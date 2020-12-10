package io.fraud.kafka;

import io.fraud.kafka.consumer.KafkaMessageConsumer;
import io.fraud.kafka.producer.KafkaMessageProducer;

public class KafkaService {

    private final KafkaMessageProducer kafkaMessageProducer;
    private final KafkaMessageConsumer kafkaMessageConsumer;

    public KafkaService(String server) {
        kafkaMessageProducer = new KafkaMessageProducer(server);
        kafkaMessageConsumer = new KafkaMessageConsumer(server);
    }

    public void send(String topic, String message) {
        kafkaMessageProducer.send(topic, message);
    }

    public void subscribe(String topic) {
        kafkaMessageConsumer.subscribe(topic);
        kafkaMessageConsumer.consume();
    }

    public KafkaRecord waitForMessages(String message) {
        return kafkaMessageConsumer.waitForMessages(message);
    }
}
