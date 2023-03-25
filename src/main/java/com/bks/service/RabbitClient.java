package com.bks.service;

import com.bks.config.AppConfig;
import com.bks.dto.ClientDto;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

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

	private static Connection rmqConnection;
	private final AppConfig appConfig;

	/**
	 * инициализация channel (канала) входящих сообщений с информаией о создаваемых клиентах
	 */
	public Channel initChannel() {
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
			log.error("Ошибка создания подключения либо канала RabbitMQ {} ",  e.getMessage());
		}
		return channel;
	}

	/**
	 * инициализация иксченджа входящих сообщений
	 */
	public void initClientExchange(Channel channel) {
		try {
			channel.exchangeDeclare(appConfig.getClientExchange(),  BuiltinExchangeType.DIRECT, true);
		} catch (IOException e) {
			log.error("Ошибка создания иксченджа  {} ",  e.getMessage());
		}
	}


	/**
	*  реализация 'слухача' сообщений
	 */
	public void initMessageListener(Channel channel, String queueName, String exchange, String bindKey, Consumer<ClientDto> consumer) {

		/**
		 * Called when a <code><b>basic.deliver</b></code> is received for this consumer.
		 * @param consumerTag the <i>consumer tag</i> associated with the consumer
		 * @param message the delivered message
		 * @throws IOException if the consumer encounters an I/O error while processing the message
		 *
		 * void handle(String consumerTag, Delivery message) throws IOException;
		 */

		DeliverCallback deliverCallback = (str, delivery) -> {

			ClientDto rmqMessage = null;
			//String rmqMessage = null;
			try {
				log.info("delivery.getProperties() {}", delivery.getProperties());
				log.info("delivery.getBody(): {}", delivery.getBody());
				//rmqMessage = new String(delivery.getBody(), "UTF-8");
				rmqMessage =  SerializationUtils.deserialize(delivery.getBody());
				log.info("RabbitMQ сообщение: {}", rmqMessage.toString());
			} catch (Exception e) {
				log.error("Ошибка  чтения сообщения RabbitMQ: {}", e.getMessage());
			}

			consumer.accept(rmqMessage);
		};
		try {
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(queueName, exchange, bindKey);
			channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {	});

		} catch (IOException e) {
			log.error("Ошибка  чтения сообщения RabbitMQ: {}", e.getMessage());
		}
	}


	public  void publish(Channel channel,  String exchange, String routingKey, byte[] clientMessage) {
		try {
			channel.basicPublish(exchange, routingKey, null, clientMessage);
		} catch (IOException e) {
			log.error("Ошибка публикации:{}", e.getMessage());
		}
	}
}