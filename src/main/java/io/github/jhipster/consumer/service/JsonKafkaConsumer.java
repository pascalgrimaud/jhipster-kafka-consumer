package io.github.jhipster.consumer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.consumer.domain.JsonMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class JsonKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public static final String TOPIC = "json_message_topic";

    private KafkaConsumer<String, JsonNode> jsonKafkaConsumer;

    private String BOOTSTRAP_SERVERS = "localhost:9092";

    private final JsonMessageService jsonMessageService;

    public JsonKafkaConsumer(JsonMessageService jsonMessageService) {
        this.jsonMessageService = jsonMessageService;
    }

    public void start() {
        log.info("Kafka consumer starting...");

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        this.jsonKafkaConsumer = new KafkaConsumer<>(props);
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        Thread consumerThread = new Thread(() -> {

            ObjectMapper mapper = new ObjectMapper();

            try {
                jsonKafkaConsumer.subscribe(Collections.singletonList(TOPIC));
                log.info("Kafka consumer started");
                while (!closed.get()) {
                    ConsumerRecords<String, JsonNode> records = jsonKafkaConsumer.poll(Duration.ofSeconds(3));
                    for (ConsumerRecord<String, JsonNode> record : records) {
                        log.info("Consumed message in {} : {}", TOPIC, record.value());
                        JsonNode jsonNode = record.value();
                        JsonMessage jsonMessage = mapper.treeToValue(jsonNode, JsonMessage.class);
                        this.jsonMessageService.save(jsonMessage.receivedAt(Instant.now()));
                    }
                }
                jsonKafkaConsumer.commitSync();
            } catch (WakeupException e) {
                // Ignore exception if closing
                if (!closed.get()) throw e;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                jsonKafkaConsumer.close();
            }
        });
        consumerThread.start();
    }

    public KafkaConsumer<String, JsonNode> getJsonKafkaConsumer() {
        return jsonKafkaConsumer;
    }

    public void shutdown() {
        log.info("Shutdown Kafka json consumer");
        closed.set(true);
        jsonKafkaConsumer.wakeup();
    }

    public void setBOOTSTRAP_SERVERS(String BOOTSTRAP_SERVERS) {
        this.BOOTSTRAP_SERVERS = BOOTSTRAP_SERVERS;
    }
}
