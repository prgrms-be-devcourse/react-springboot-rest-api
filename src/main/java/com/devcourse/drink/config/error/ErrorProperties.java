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

    private static String productDBInsertFail;

    private static String productNotMatched;

    private static String orderNotMatched;

    public static String getOrderNotMatched() {
        return orderNotMatched;
    }

    public void setOrderNotMatched(String orderNotMatched) {
        this.orderNotMatched = orderNotMatched;
    }

    public static String getNotValidEmail() {
        return notValidEmail;
    }

    public static String getPriceNegativeValue() {
        return priceNegativeValue;
    }

    public static String getOrderItemNegativeValue() {
        return orderItemNegativeValue;
    }

    public static String getProductDBInsertFail() {
        return productDBInsertFail;
    }

    public void setPriceNegativeValue(String priceNegativeValue) {
        this.priceNegativeValue = priceNegativeValue;
    }

    public void setNotValidEmail(String notValidEmail) {
        this.notValidEmail = notValidEmail;
    }

    public void setProductDBInsertFail(String productDBInsertFail) {
        this.productDBInsertFail = productDBInsertFail;
    }

    public void setOrderItemNegativeValue(String orderItemNegativeValue) {
        this.orderItemNegativeValue = orderItemNegativeValue;
    }

    public static String getProductNotMatched() {
        return productNotMatched;
    }

    public void setProductNotMatched(String productNotMatched) {
        this.productNotMatched = productNotMatched;
    }
}
