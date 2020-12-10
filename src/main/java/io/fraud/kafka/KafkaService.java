package io.fraud.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fraud.kafka.consumer.KafkaMessageConsumer;
import io.fraud.kafka.producer.KafkaMessageProducer;
import org.aeonbits.owner.ConfigFactory;

public class KafkaService {

    private final KafkaMessageProducer kafkaMessageProducer;
    private final KafkaMessageConsumer kafkaMessageConsumer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaService() {
        ProjectConfig projectConfig = ConfigFactory.create(ProjectConfig.class);
        kafkaMessageProducer = new KafkaMessageProducer(projectConfig.kafkaBrokers());
        kafkaMessageConsumer = new KafkaMessageConsumer(projectConfig.kafkaBrokers());
    }

    public void send(String topic, String message) {
        kafkaMessageProducer.send(topic, message);
    }

    public void send(String topic, Object message) {
        try {
            kafkaMessageProducer.send(topic, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        kafkaMessageConsumer.subscribe(topic);
        kafkaMessageConsumer.consume();
    }

    public KafkaRecord waitForMessages(String message) {
        return kafkaMessageConsumer.waitForMessages(message);
    }
}
