package com.ilya.service.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilya.service.service.PaymentProcessorService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.learn.dropwizard.model.CreateUpdatePaymentProcessorDTO;

import java.time.Duration;
import java.util.Collections;

import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;

@Component("kafkaconsumer")
public class KafkaConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;

    @Autowired
    private PaymentProcessorService paymentProcessorService;

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run() {
        try (Consumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(kafkaConsumerConfig.getProperties());) {
            consumer.subscribe(Collections.singletonList(topic));
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(Long.parseLong(kafkaConsumerConfig.getProperties().getProperty(MAX_POLL_INTERVAL_MS_CONFIG))));
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    try {
                        CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO = objectMapper.readValue(consumerRecord.value(), CreateUpdatePaymentProcessorDTO.class);
                        paymentProcessorService.createPaymentProcessor(createUpdatePaymentProcessorDTO);

                        LOGGER.info("Payment processor {} successfully created", consumerRecord.value());
                    } catch (Exception e) {
                        LOGGER.warn("Incorrect message format message = {}", consumerRecord.value());
                    }
                }
                consumer.commitSync();
            }
        }
    }
}

