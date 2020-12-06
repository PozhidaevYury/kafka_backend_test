package io.fraud.tests;

import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.consumer.KafkaMessageConsumer;
import io.fraud.kafka.producer.KafkaMessageProducer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BackendTests {

    @Test
    void testCanWriteMessageToQueueTransaction() {
        KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer("localhost:9092");
        KafkaMessageConsumer kafkaMessageConsumer = new KafkaMessageConsumer("localhost:9092");

        kafkaMessageConsumer.subscribe("test");
        kafkaMessageConsumer.consume();
        kafkaMessageProducer.send("test", "hello from java 14");

        KafkaRecord receivedRecords = kafkaMessageConsumer.waitForMessages("hello from java 14");

        assertThat(receivedRecords).isNotNull();
    }
}
