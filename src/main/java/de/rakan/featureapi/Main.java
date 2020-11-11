package de.rakan.featureapi;

import de.rakan.featureapi.repositories.FeatureCache;
import de.rakan.featureapi.repositories.StaticFeatureCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public FeatureCache featureCache(@Value("${SOURCE_DATA_FILE_NAME}") String sourceDataFileName) {
		return new StaticFeatureCache(sourceDataFileName);
	}

}
