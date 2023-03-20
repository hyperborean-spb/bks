package com.bks;

import com.bks.service.RabbitClient;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class BksCrmApplication implements CommandLineRunner{

	private final RabbitClient rabbitClient;


	public static void main(String[] args) {
		SpringApplication.run(BksCrmApplication.class, args);
	}


	@Override
	public void run(String... args) throws IOException, URISyntaxException {

		Channel syncChannel = rabbitClient.initSyncChannel();
		rabbitClient.initOutExchange();
	}
}