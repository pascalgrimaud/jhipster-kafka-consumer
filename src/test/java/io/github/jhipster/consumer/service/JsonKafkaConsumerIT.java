package io.github.jhipster.consumer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.github.jhipster.consumer.ConsumerApp;
import io.github.jhipster.consumer.config.KafkaProperties;
import io.github.jhipster.consumer.domain.JsonMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.KafkaContainer;

import java.util.Map;


@SpringBootTest(classes = ConsumerApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class JsonKafkaConsumerIT {

    private static boolean started = false;

    private static KafkaContainer kafkaContainer;

    private  KafkaProducer<String, JsonNode> jsonMessageProducer;

    @Autowired
    private  KafkaProperties kafkaProperties;

    @Autowired
    private  JsonKafkaConsumer jsonMessageConsumer;

    @Autowired
    private JsonMessageService jsonMessageService;

    private static final int MAX_ATTEMPT = 5;

    public static final String JSON_MESSAGE_TOPIC = "json_message_topic";

    @BeforeAll
    public static void startServer() {
        if (!started) {
            startTestcontainer();
            started = true;
        }
    }

    private static void startTestcontainer() {
        kafkaContainer = new KafkaContainer("5.3.1");
        kafkaContainer.start();
        System.setProperty("kafkaBootstrapServers", kafkaContainer.getBootstrapServers());

    }

    @BeforeEach
    public void setup() {

        //Config Json topic
        jsonMessageConsumer = new JsonKafkaConsumer(jsonMessageService);
        jsonMessageConsumer.setBOOTSTRAP_SERVERS(kafkaContainer.getBootstrapServers());
        jsonMessageConsumer.start();

        jsonMessageProducer = new KafkaProducer<>(
            ImmutableMap.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers()
            ),
            new StringSerializer(),
            new JsonSerializer()
        );
    }

    @AfterEach
    public void teardown() {

        jsonMessageConsumer.shutdown();

    }

    @Test
    public void producedJsonMessageHasBeenConsumed() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonMessage message = new JsonMessage().field1("Test").field2("Test").number(1);
        jsonMessageProducer.send(new ProducerRecord<>(JSON_MESSAGE_TOPIC, objectMapper.valueToTree(message)));

        Map<MetricName, ? extends Metric> metrics = jsonMessageConsumer.getJsonKafkaConsumer().metrics();

        Metric recordsConsumedTotalMetric = metrics.entrySet().stream()
            .filter(entry -> "records-consumed-total".equals(entry.getKey().name()))
            .findFirst()
            .get()
            .getValue();

        Double expectedTotalConsumedMessage = 1.0;
        Double totalConsumedMessage;
        int attempt = 0;

        do {
            totalConsumedMessage = (Double) recordsConsumedTotalMetric.metricValue();
            Thread.sleep(200);
        } while (!totalConsumedMessage.equals(expectedTotalConsumedMessage) && attempt++ < MAX_ATTEMPT);

        Assertions.assertThat(attempt).isLessThan(MAX_ATTEMPT);
        Assertions.assertThat(totalConsumedMessage).isEqualTo(expectedTotalConsumedMessage);
    }

}
