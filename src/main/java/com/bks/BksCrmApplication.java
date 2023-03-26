package com.bks;

import com.bks.config.AppConfig;
import com.bks.dto.ClientDto;
import com.bks.service.ClientService;
import com.bks.service.RabbitClient;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalDate;
import java.util.concurrent.Executor;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class BksCrmApplication implements CommandLineRunner{

	private final RabbitClient rabbitClient;

	private final ClientService clientService;

	private final AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(BksCrmApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Channel channel = rabbitClient.initChannel();

		rabbitClient.initClientExchange(channel);

		rabbitClient.initMessageListener(channel, appConfig.getClientQueue(), appConfig.getClientExchange(), "",
			rabbitMessage -> {
				clientService.registerClient(rabbitMessage);
				log.info("зарегистрирован новый клиент: {}", rabbitMessage.toString());
			});

		ClientDto newClient = new ClientDto();
		newClient.setName("Andy");
		newClient.setBirthdate(LocalDate.of(2005, 11, 7));
		newClient.setPassword("new_client_pass");
		byte[] byteMsg = SerializationUtils.serialize(newClient);

		rabbitClient.publish(channel,  appConfig.getClientExchange(), "",  byteMsg);
	}

	/* пул для @Async; + @EnableAsync in config */
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("BKS-");
		executor.initialize();
		return executor;
	}
}