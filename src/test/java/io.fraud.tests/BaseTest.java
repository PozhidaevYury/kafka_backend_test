package io.fraud.tests;

import io.fraud.db.DbService;
import io.fraud.kafka.KafkaService;

public class BaseTest {

    protected final KafkaService kafkaService = new KafkaService();
    protected final DbService dbService = new DbService();

}
