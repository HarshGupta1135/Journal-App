package com.example.pracJournalApp;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PracJournalAppApplication {

	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	public static void main(String[] args) {
		SpringApplication.run(PracJournalAppApplication.class, args);
	}

	@Bean
	public MongoDatabaseFactory mongoDatabaseFactory() {
		return new SimpleMongoClientDatabaseFactory(
				MongoClients.create(mongoUri), databaseName
		);
	}
	@Bean
	public PlatformTransactionManager falana(MongoDatabaseFactory dbfactory){
		return new MongoTransactionManager(dbfactory);
	}

}
