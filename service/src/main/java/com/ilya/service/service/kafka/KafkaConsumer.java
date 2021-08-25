package com.ilya.service.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;

import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;

@Component("kafkaconsumer")
public class KafkaConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;

//    @Autowired
//    private PersonService personService;

    @Value("${kafka.topic}")
    private String topic;

    @Override
    public void run() {
        try (Consumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(kafkaConsumerConfig.getProperties());) {
            consumer.subscribe(Collections.singletonList(topic));
            ObjectMapper objectMapper = new ObjectMapper();
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(Long.parseLong(kafkaConsumerConfig.getProperties().getProperty(MAX_POLL_INTERVAL_MS_CONFIG))));
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    try {
//                        CreateUpdatePersonDTO createUpdatePersonDTO = objectMapper.readValue(consumerRecord.value(), CreateUpdatePersonDTO.class);
//                        personService.createPerson(createUpdatePersonDTO);

                        LOGGER.info("Person {} successfully created", consumerRecord.value());
                    } catch (Exception e) {
                        LOGGER.warn("Incorrect message format message = {}", consumerRecord.value());
                    }
                }
//            consumer.commitSync();
                consumer.commitAsync();
            }
        }
    }
}

