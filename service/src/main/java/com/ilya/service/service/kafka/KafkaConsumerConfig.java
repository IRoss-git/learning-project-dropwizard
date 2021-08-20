package com.ilya.service.service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

@Component
@PropertySource("classpath:vault.properties")
public class KafkaConsumerConfig {

    private Properties properties;

    KafkaConsumerConfig(Properties properties){
        this.properties=properties;
    }

    @Value("${kafka.broker}")
    private void setBroker(String broker){
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG,broker);
    }

    @Value("${kafka.group.id}")
    private void setGroupId(String groupId){
        properties.setProperty(GROUP_ID_CONFIG, groupId);
    }

    @Value("${kafka.max.poll.results}")
    private void setMaxPollRecord(Integer maxPollRecord){
        properties.setProperty(MAX_POLL_RECORDS_CONFIG, String.valueOf(maxPollRecord));
    }

    @Value("${kafka.key.deserializer}")
    private void setKeyDeserializer(String keyDeserializer){
        properties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG,keyDeserializer);
    }

    @Value("${kafka.value.deserializer}")
    private void setValueDeserializer(String valueDeserializer){
        properties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
    }

    @Value("${kafka.poll.interval}")
    private void setPollInterval(String pollInterval){
        properties.setProperty(MAX_POLL_INTERVAL_MS_CONFIG, pollInterval);
    }

    public Properties getProperties(){
        return properties;
    }
}
