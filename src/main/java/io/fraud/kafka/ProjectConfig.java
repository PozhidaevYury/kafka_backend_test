package io.fraud.kafka;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {
    String env();
    @Key("${env}.dbHost")
    String dbHost();
    @Key("${env}.dbPort")
    String dbPort();
    @Key("${env}.dbName")
    String dbName();
    @Key("${env}.dbUser")
    String dbUser();
    @Key("${env}.dbPassword")
    String dbPassword();
    @Key("${env}.kafkaBrokers")
    String kafkaBrokers();
    @Key("legitTopic")
    String legitTopic();
    @Key("fraudTopic")
    String fraudTopic();
    @Key("queueTopic")
    String queueTopic();
}
