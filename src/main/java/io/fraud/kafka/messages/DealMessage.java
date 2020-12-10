package io.fraud.kafka.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class DealMessage {

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

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("base_currency")
    private String baseCurrency;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return
                "DealMessage{" +
                        "date = '" + date + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",currency = '" + currency + '\'' +
                        ",source = '" + source + '\'' +
                        ",target = '" + target + '\'' +
                        "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealMessage that = (DealMessage) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(date, that.date) && Objects.equals(currency, that.currency) && Objects.equals(source, that.source) && Objects.equals(target, that.target) && Objects.equals(rate, that.rate) && Objects.equals(baseCurrency, that.baseCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, currency, source, target, rate, baseCurrency);
    }
}