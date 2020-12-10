package io.fraud.tests;

import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.KafkaService;
import io.fraud.kafka.messages.DealMessage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BackendTests {

    private final KafkaService kafkaService = new KafkaService("localhost:9092");

    @Test
    void testCanWriteMessageToQueueTransaction() {
        kafkaService.subscribe("test");
        kafkaService.send("test", "hello from java 14");

        KafkaRecord receivedRecord = kafkaService.waitForMessages("hello from java 14");

        assertThat(receivedRecord).isNotNull();
    }

    @Test
    void testAppCanProcessValidMessage() {
        kafkaService.subscribe("streaming.transactions.legit");
        kafkaService.send("queuing.transactions", "{\"date\": \"12/08/2020 12:27:12\", \"source\": \"java14\", \"target\": \"IUsmnlzitnZ6\", \"amount\": 300, \"currency\": \"USD\"}");

        DealMessage dealMessage = kafkaService.waitForMessages("java14").valueAs(DealMessage.class);

        assertThat(dealMessage.getAmount()).isEqualTo(300.0);
        assertThat(dealMessage.getCurrency()).isEqualTo("USD");
    }
}
