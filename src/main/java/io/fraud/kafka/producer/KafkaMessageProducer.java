package io.fraud.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaMessageProducer {

    private static KafkaProducer<String, String> kafkaProducer;
    private final String bootstrapServer;

    public KafkaMessageProducer(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
        createProducer();
    }

    private void createProducer() {
        Properties properties = createProducerProperties();
        if (kafkaProducer == null) {
            kafkaProducer = new KafkaProducer<String, String>(properties);
        }
    }

    private Properties createProducerProperties() {
        return new Properties() {{
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
            put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducer");
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        }};
    }

    public RecordMetadata send(String topic, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        try {
            return kafkaProducer.send(record).get();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
