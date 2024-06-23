package com.poc.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.poc.auser.main.entity.UserSubscription;

@Component
@Configuration
public class UserSubscriptionItemReader {

    @Autowired
    private DataSource dataSource;

    @Bean
    @StepScope
	public ItemReader<UserSubscription> reader() {
        return new JdbcCursorItemReaderBuilder<UserSubscription>()
                .dataSource(dataSource)
                .name("reader")
                .sql("SELECT * FROM USER_SUBSCRIPTIONS")
                .rowMapper(new BeanPropertyRowMapper<>(UserSubscription.class))
                .build();
    }
}
