package io.fraud.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class KafkaRecord {

    private final ConsumerRecord<String, String> record;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaRecord(ConsumerRecord<String, String> record) {
        this.record = record;
    }

    public boolean hasSourceId(String message) {
        return record.value().contains(message);
    }

    public <T> T valueAs(Class<T> tClass) {
        try {
            return objectMapper.readValue(record.value(), tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
