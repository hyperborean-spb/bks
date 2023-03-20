package com.bks.service;

import com.bks.config.AppConfig;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@EnableRabbit
@RequiredArgsConstructor
@Slf4j
public class RabbitClient {

	@Value("${spring.rabbitmq.host}")
	private String rabbitmqHost;

	@Value("${spring.rabbitmq.username}")
	private String rabbitmqUser;

	@Value("${spring.rabbitmq.password}")
	private String rabbitmqPassword;

	@Value("${spring.rabbitmq.port}")
	private int rabbitmqPort;

	private static Channel notificationChannel;
	private static Connection rmqConnection;
	private final AppConfig appConfig;
	//private final EventProcessor eventProcessor;

	public Channel initSyncChannel() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(rabbitmqHost);
		factory.setUsername(rabbitmqUser);
		factory.setPassword(rabbitmqPassword);
		factory.setPort(rabbitmqPort);
		Channel channel = null ;
		try {
			rmqConnection = factory.newConnection();
			channel = rmqConnection.createChannel();
		} catch (Exception e) {
			log.error("Ошибка подключения к RabbitMQ {} ",  e.getMessage());
		}
		return channel;
	}

	/**
	 * инициализация икченджа RabbitMQ, через очереди которого осуществляется оповещение
	 */
	public void initOutExchange() {
		try {
			notificationChannel = rmqConnection.createChannel();
			notificationChannel.exchangeDeclare(appConfig.getOutExchange(),  BuiltinExchangeType.DIRECT, true);
		} catch (IOException e) {
			log.error("Ошибка создания иксенджа  {} ",  e.getMessage());
		}
	}

	/**
	 * объявление очередей рассылки
	 * @param queueName  имя очереди
	 * @param exchange	иксчендж, за которым закреплена очередь
	 * @param bindKey	ключ закрепления очереди к иксченджу
	 */
	private void declareAndBindQueue(String queueName, String exchange, String bindKey) {
		try {
			notificationChannel.queueDeclare(queueName, true, false, false, null);
			notificationChannel.queueBind(queueName, exchange, bindKey);
		} catch (IOException e) {
			log.error("Ошибка создания либо байндинга очереди :{}", e.getMessage());
		}
	}

	/**
	 * базовый механизм публикации сообщений в очередь
	 * @param exchange  икчендж, куда будет опубликовано сообщение
	 * @param routingKey ключ адресации. не важен в случае FANOUT искченджа
	 * @param notification собственно публикуемое сообщение
	 */
	private  void publish(String exchange, String routingKey, String notification) {
		try {
			notificationChannel.basicPublish(exchange, routingKey, null, notification.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			log.error("Error while  publishing notification:{}", e.getMessage());
		}
	}
}