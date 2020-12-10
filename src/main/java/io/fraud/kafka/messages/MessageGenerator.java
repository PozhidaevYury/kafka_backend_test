package io.fraud.kafka.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class MessageGenerator {

    @JsonProperty("date")
    private String date;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("source")
    private String source;

    @JsonProperty("target")
    private String target;

    @Override
    public String toString() {
        return "MessageGenerator{" +
                "date='" + date + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public MessageGenerator setDate(String date) {
        this.date = date;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public MessageGenerator setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public MessageGenerator setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getSource() {
        return source;
    }

    public MessageGenerator setSource(String source) {
        this.source = source;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public MessageGenerator setTarget(String target) {
        this.target = target;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageGenerator that = (MessageGenerator) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(date, that.date) && Objects.equals(currency, that.currency) && Objects.equals(source, that.source) && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, currency, source, target);
    }
}
