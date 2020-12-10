package io.fraud.kafka.consumer;

import io.fraud.kafka.KafkaRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KafkaMessageConsumer {

    private static KafkaConsumer<String, String> consumer;
    private final Collection<KafkaRecord> receivedRecords = new ArrayList<>();

    public KafkaMessageConsumer(String bootstrapServer) {
        consumer =
                new KafkaConsumer<String, String>(createConsumerProperties(bootstrapServer));
    }

    public Collection<KafkaRecord> consume() {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(0));

        records.forEach(record -> {
            System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            receivedRecords.add(new KafkaRecord(record));
        });
        return receivedRecords;
    }

    public KafkaRecord waitForMessages(String message) {
        Awaitility
                .await()
                .atMost(30, TimeUnit.SECONDS)
                .until(() -> consume().stream().anyMatch(it -> it.hasSourceId(message)));

        return receivedRecords
                .stream()
                .filter(it -> it.hasSourceId(message))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no such record with sourceId" + message));
    }

    public void subscribe(String topic) {
        consumer.subscribe(Collections.singletonList(topic));
    }

    public Properties createConsumerProperties(String bootstrapServer) {
        return new Properties() {{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
            put(ConsumerConfig.GROUP_ID_CONFIG, "testgroup");
            put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        }};
    }
}
