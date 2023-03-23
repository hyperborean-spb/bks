package com.bks;

import com.bks.config.AppConfig;
import com.bks.service.RabbitClient;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
@Slf4j
public class BksCrmApplication implements CommandLineRunner{

	private final RabbitClient rabbitClient;

	private final AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(BksCrmApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Channel сhannel = rabbitClient.initChannel();

		/* НУЖНО (ЛИ) СОЗДАТЬ inputExchange*/

		/* последний аргумент - consumer */
		rabbitClient.initMessageListener(сhannel, appConfig.getClientQueue(), appConfig.getClientExchange(), "", rabbitMessage -> log.info("Процессинг сообщения: {}", rabbitMessage));
	}
}