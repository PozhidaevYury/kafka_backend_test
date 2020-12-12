package io.fraud.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fraud.kafka.consumer.KafkaMessageConsumer;
import io.fraud.kafka.messages.MessageGenerator;
import io.fraud.kafka.producer.KafkaMessageProducer;
import org.aeonbits.owner.ConfigFactory;

public class KafkaService {

    private final KafkaMessageProducer kafkaMessageProducer;
    private final KafkaMessageConsumer kafkaMessageConsumer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProjectConfig projectConfig = ConfigFactory.create(ProjectConfig.class);
    private final TestDataGenerator testDataGenerator = new TestDataGenerator();

    public KafkaService() {
        kafkaMessageProducer = new KafkaMessageProducer(projectConfig.kafkaBrokers());
        kafkaMessageConsumer = new KafkaMessageConsumer(projectConfig.kafkaBrokers());
    }

    public MessageGenerator send() {
        try {
            String message = testDataGenerator.generate("data/message.twig");
            send(projectConfig.queueTopic(), message);
            return objectMapper.readValue(message, MessageGenerator.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String topic, String message) {
        kafkaMessageProducer.send(topic, message);
    }

    public void send(Object message) {
        try {
            kafkaMessageProducer.send(projectConfig.queueTopic(), objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void subscribeToLegitTopic() {
        subscribe(projectConfig.legitTopic());
    }

    public void subscribeToFraudTopic() {
        subscribe(projectConfig.fraudTopic());
    }

    public void subscribe(String topic) {
        kafkaMessageConsumer.subscribe(topic);
        kafkaMessageConsumer.consume();
    }

    public KafkaRecord waitForMessages(String message) {
        return kafkaMessageConsumer.waitForMessages(message);
    }
}
