package com.disco.shoppingcart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class ShoppingUserConfiguration {
    @Bean
    public DataSource dataSource() {
        log.info("creating in-memory database");
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb")
                .addScript("createShoppingCartTables.sql")
                .build();
    }

}
