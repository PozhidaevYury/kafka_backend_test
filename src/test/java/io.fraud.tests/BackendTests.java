package io.fraud.tests;

import io.fraud.db.model.Deal;
import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.messages.DealMessage;
import io.fraud.kafka.messages.MessageGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

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
        List<Deal> deals = dbService.findDealById(100);
        assertThat(deals.size()).isEqualTo(1);
    }

    @ParameterizedTest
    @MethodSource("generatorMessages")
    void test(String message) {
        MessageGenerator messageGenerator = kafkaService.send(message);

        List<Deal> deals = dbService.findDealById(1);

        assertThat(deals.get(0).getBaseCurrency()).isEqualTo("USD");
    }

    public static Stream<String> generatorMessages() {
        return Stream.of(
                "{\"date\": \"12/08/2020 12:27:12\", \"source\": \"java14\", \"target\": \"IUsmnlzitnZ6\", \"amount\": 300, \"currency\": \"USD\"}",
                "{\"date\": \"12/08/2020 13:27:12\", \"source\": \"java15\", \"target\": \"IUsmnlzitwZ6\", \"amount\": 500, \"currency\": \"USD\"}"
        );
    }
}
