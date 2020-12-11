package io.fraud.tests;

import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.messages.DealMessage;
import io.fraud.kafka.messages.MessageGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BackendTests extends BaseTest {

    @Test
    void testCanWriteMessageToQueueTransaction() {
        kafkaService.subscribe("test");
        kafkaService.send("test", "hello from java 14");

        KafkaRecord receivedRecord = kafkaService.waitForMessages("hello from java 14");

        assertThat(receivedRecord).isNotNull();
    }

    @Test
    void testAppCanProcessValidMessage() {
        MessageGenerator messageGenerator = new MessageGenerator();
        messageGenerator
                .setDate(new Date().toString())
                .setAmount(200)
                .setCurrency("USD")
                .setTarget(RandomStringUtils.randomAlphabetic(10))
                .setSource(RandomStringUtils.randomAlphabetic(3));


        kafkaService.subscribeToLegitTopic();
        kafkaService.send(messageGenerator);

        DealMessage dealMessage =
                kafkaService.waitForMessages(messageGenerator.getTarget()).valueAs(DealMessage.class);

        assertThat(dealMessage.getAmount()).isEqualTo(200.0);
        assertThat(dealMessage.getCurrency()).isEqualTo("USD");
    }

    @Test
    void testAppCanProcessFraudMessage() {
        MessageGenerator messageGenerator = new MessageGenerator();
        messageGenerator
                .setDate(new Date().toString())
                .setAmount(2000)
                .setCurrency("USD")
                .setTarget(RandomStringUtils.randomAlphabetic(10))
                .setSource(RandomStringUtils.randomAlphabetic(3));


        kafkaService.subscribeToFraudTopic();
        kafkaService.send(messageGenerator);

        DealMessage dealMessage =
                kafkaService.waitForMessages(messageGenerator.getTarget()).valueAs(DealMessage.class);

        assertThat(dealMessage.getAmount()).isEqualTo(2000.0);
        assertThat(dealMessage.getCurrency()).isEqualTo("USD");
    }
}
