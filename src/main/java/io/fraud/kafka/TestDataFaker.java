package io.fraud.kafka;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDataFaker {

    public String date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public String amount() {
        return "200.0";
    }

    public String currency() {
        return "EUR";
    }

    public String source() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String target() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
