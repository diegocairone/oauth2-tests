package com.cairone.example.webapp.cfg;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource databaseDataSource() {
		return DataSourceBuilder.create().build();
	}
}
