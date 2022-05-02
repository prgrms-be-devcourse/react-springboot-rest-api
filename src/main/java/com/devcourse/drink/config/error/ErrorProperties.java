package com.devcourse.drink.config.error;

import com.devcourse.drink.config.YamlPropertiesFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:error.yaml", factory = YamlPropertiesFactory.class)
@ConfigurationProperties("error")
public class ErrorProperties {
    private static String priceNegativeValue;

    public static String getPriceNegativeValue() {
        return priceNegativeValue;
    }

    public void setPriceNegativeValue(String priceNegativeValue) {
        ErrorProperties.priceNegativeValue = priceNegativeValue;
    }

}
