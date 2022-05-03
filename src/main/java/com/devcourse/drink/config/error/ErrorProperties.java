package com.devcourse.drink.config.error;

import com.devcourse.drink.config.YamlPropertiesFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:error.yaml", factory = YamlPropertiesFactory.class)
@ConfigurationProperties(prefix = "error")
public class ErrorProperties {

    private static String priceNegativeValue;

    private static String notValidEmail;

    private static String orderItemNegativeValue;

    public static String getNotValidEmail() {
        return notValidEmail;
    }

    public static String getPriceNegativeValue() {
        return priceNegativeValue;
    }

    public void setPriceNegativeValue(String priceNegativeValue) {
        this.priceNegativeValue = priceNegativeValue;
    }

    public void setNotValidEmail(String notValidEmail) {
        this.notValidEmail = notValidEmail;
    }

    public static String getOrderItemNegativeValue() {
        return orderItemNegativeValue;
    }

    public void setOrderItemNegativeValue(String orderItemNegativeValue) {
        this.orderItemNegativeValue = orderItemNegativeValue;
    }
}
