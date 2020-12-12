package io.fraud.tests;

import io.fraud.db.model.Deal;
import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.messages.DealMessage;
import io.fraud.kafka.messages.MessageGenerator;
import org.junit.jupiter.api.Test;

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
        // MessageGenerator messageGenerator = new MessageGenerator();
        // messageGenerator
        //         .setDate(new Date().toString())
        //         .setAmount(200)
        //         .setCurrency("USD")
        //         .setTarget(RandomStringUtils.randomAlphabetic(10))
        //         .setSource(RandomStringUtils.randomAlphabetic(3));

        kafkaService.subscribeToLegitTopic();
        MessageGenerator messageGenerator = kafkaService.send();

        DealMessage dealMessage =
                kafkaService.waitForMessages(messageGenerator.getTarget()).valueAs(DealMessage.class);

        assertThat(dealMessage.getAmount()).isEqualTo(messageGenerator.getAmount());
        assertThat(dealMessage.getCurrency()).isEqualTo(messageGenerator.getCurrency());
    }

    @Test
    void testAppCanProcessFraudMessage() {
        //  MessageGenerator messageGenerator = new MessageGenerator();
        //  messageGenerator
        //          .setDate(new Date().toString())
        //          .setAmount(2000)
        //          .setCurrency("USD")
        //          .setTarget(RandomStringUtils.randomAlphabetic(10))
        //          .setSource(RandomStringUtils.randomAlphabetic(3));

        kafkaService.subscribeToFraudTopic();
        MessageGenerator messageGenerator = kafkaService.send();

        DealMessage dealMessage =
                kafkaService.waitForMessages(messageGenerator.getTarget()).valueAs(DealMessage.class);

        assertThat(dealMessage.getAmount()).isEqualTo(messageGenerator.getAmount());
        assertThat(dealMessage.getCurrency()).isEqualTo(messageGenerator.getCurrency());
    }

    @Test
    void testAppCanSaveFraudMessageToDb() {
        Deal deal = dbService.findDealById(100);
        System.out.println(deal.getAmount());
        //assertThat(deal.getAmount()).isEqualTo(200);
    }
}
