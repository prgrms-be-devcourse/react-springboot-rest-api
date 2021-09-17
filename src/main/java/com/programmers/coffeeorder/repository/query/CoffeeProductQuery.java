package com.programmers.coffeeorder.repository.query;

import com.programmers.coffeeorder.config.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "coffee-product")
@PropertySource(value = "classpath:/coffee_repository_query.yml", factory = YamlPropertySourceFactory.class)
@Getter
@Setter
public class CoffeeProductQuery {
    private Select select;

    @Getter
    @Setter
    public static class Select {
        private String all;
        private String byId;
    }

}
