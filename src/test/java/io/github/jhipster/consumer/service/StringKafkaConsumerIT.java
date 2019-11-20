package io.github.jhipster.consumer.service;

import com.google.common.collect.ImmutableMap;
import io.github.jhipster.consumer.ConsumerApp;
import io.github.jhipster.consumer.config.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.serialization.StringSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.KafkaContainer;

import java.util.Map;


@SpringBootTest(classes = ConsumerApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class StringKafkaConsumerIT {

    private static boolean started = false;

    private static KafkaContainer kafkaContainer;

    @Autowired
    private  ConsumerKafkaConsumer stringMessageConsumer;

    @Autowired
    private  KafkaProperties kafkaProperties;

    @Autowired
    private  StringMessageService stringMessageService;

    private  KafkaProducer<String, String> stringMessageProducer;

    private static final int MAX_ATTEMPT = 5;

    public static final String STRING_MESSAGE_TOPIC = "string_message_topic";

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

        //Config String topic
        stringMessageConsumer = new ConsumerKafkaConsumer(kafkaProperties, stringMessageService);
        stringMessageConsumer.start();

        stringMessageProducer = new KafkaProducer<>(
            ImmutableMap.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers()
            ),
            new StringSerializer(),
            new StringSerializer()
        );
    }

    @AfterEach
    public void teardown() {
        stringMessageConsumer.shutdown();
    }

    @Test
    public void producedStringMessageHasBeenConsumed() throws Exception {

        stringMessageProducer.send(new ProducerRecord<>(STRING_MESSAGE_TOPIC, "test"));

        Map<MetricName, ? extends Metric> metrics = stringMessageConsumer.getKafkaConsumer().metrics();

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
