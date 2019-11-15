package io.github.jhipster.consumer.web.rest;

import io.github.jhipster.consumer.service.ConsumerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumer-kafka")
public class ConsumerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(ConsumerKafkaResource.class);

    private ConsumerKafkaProducer kafkaProducer;

    public ConsumerKafkaResource(ConsumerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.send(message);
    }
}
