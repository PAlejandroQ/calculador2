package com.alejandroqo.calculador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hazelcast.client.config.ClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
@EnableCaching
public class CalculadorApplication {
    //private static final String constant = "constant";

	public static void main(String[] args) {
		SpringApplication.run(CalculadorApplication.class, args);
	}

	@Bean
	public ClientConfig hazelcastClientConfig() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress("hazelcast");
		return clientConfig;
	}
}


