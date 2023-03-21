package com.bks;

import com.bks.service.RabbitClient;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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


	@Value("${app.input_exchange}")
	private String inputExchange;

	@Value("${app.input_queue}")
	private String inputQueue;


	public static void main(String[] args) {
		SpringApplication.run(BksCrmApplication.class, args);
	}


	/*ТОЧНО ЛИ ВСЮ ЭТУ КОНСТРУКЦИЮ СЛУШАТЕЛЯ СЮДА? */
	@Override
	public void run(String... args) throws IOException, URISyntaxException {

		Channel syncChannel = rabbitClient.initSyncChannel();

		/* НУЖНО (ЛИ) СОЗДАТЬ inputExchange*/

		/* последний аргумент - consumer */
		rabbitClient.initSyncListener(syncChannel, inputQueue, inputExchange, "",
		rabbitMessage -> {

		});
	}
}