package com.bks.service;

import com.bks.config.AppConfig;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
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

	private static Channel notificationChannel;
	private static Connection rmqConnection;
	private final AppConfig appConfig;

	/**
	 * инициализация channel (канала) входящих сообщений
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
			log.error("Ошибка подключения к RabbitMQ {} ",  e.getMessage());
		}
		return channel;
	}

	/**
	 * реализация слушателя иксченджа событий какого-либо из сервисов
	 * @param queueName имя вновь создаваемой очереди, через которую реализуется чтение событий
	 * @param exchangeToReadFrom имя иксченджа-поставщика событий
	 * @param bindKey ключ привязки очереди к иксченджу
	 */
	public  void initEventListener(String queueName, String exchangeToReadFrom, String bindKey) {

		DeliverCallback deliverCallback = new DeliverCallback() {
			public void handle(String s, Delivery delivery) {

				Optional<String> eventMessageBody = Optional.empty();
				Envelope envelope = delivery.getEnvelope();

				String routingKey = envelope.getRoutingKey();	 // нужен ли

				try {
					eventMessageBody = Optional.ofNullable(new String(delivery.getBody(), "UTF-8"));
					log.info("RabbitMQ message: {}", eventMessageBody);
				} catch (UnsupportedEncodingException e) {
					log.error("Error while extracting RabbitMQ message: {}", e.getMessage());
				}

				if(eventMessageBody.isPresent()){
					/*try {
						eventProcessor
						.readEvent(eventMessageBody.get())
						.ifPresent(eventMessage -> {
							log.info("EventDto: {}", eventMessage);
							Notification notification = eventProcessor.writeNotificationToDb(eventMessage);
							log.info("Notification recorded to db: {}", notification);

							Map<EventPriority, String> notificationsToSend = eventProcessor.mapEventDtoToNotification(eventMessage, notification);
							appConfig.getRecipients().entrySet()
							.stream()
							.filter(entry -> entry.getKey().equals(eventMessage.getEventType()))
							.findFirst()
							.map(mapEntry -> mapEntry.getValue())
							.get()
							// recipient  -  grave.operational_support_service и т.д.; eventMessage - EventDto
							//.forEach(recipient -> publishNotificationsPerClient(eventMessage, recipient, notificationsToSend, appConfig.getNotificationExchange()));
							.forEach(recipient -> publishNotificationsPerClient(eventMessage, recipient, notificationsToSend, appConfig.getNotificationExchange()));
						}) ;
					} catch (Exception e) {
						log.error("Error while converting event message to notification: {}", e.getMessage());
					}*/
				}
			}
		};
		//  слушаем очереди событий
		try {
			log.info("Queue declared: queue - {}, exchange - {}", queueName, exchangeToReadFrom);
			notificationChannel.queueDeclare(queueName, true, false, false, null);
			log.info("1 Queue declared: queue {}", queueName);
			notificationChannel.queueBind(queueName, exchangeToReadFrom, bindKey);
			log.info("2 Queue binded: queue {}", queueName);
			// 2-nd arg - acknowledgement flag; 'true' removes the message from queue,  4-th arg - CancelCallback
			notificationChannel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
			log.info("3 Consumer started: queue {}", queueName);
		} catch (IOException e) {
			log.error("Error while reading event  message: {}", e.getMessage());
		}
	}

	public void initMessageListener(Channel channel, String queueName, String exchange, String bindKey, Consumer<Object> consumer) {

		/**
		 * Called when a <code><b>basic.deliver</b></code> is received for this consumer.
		 * @param consumerTag the <i>consumer tag</i> associated with the consumer
		 * @param message the delivered message
		 * @throws IOException if the consumer encounters an I/O error while processing the message
		 *
		 * void handle(String consumerTag, Delivery message) throws IOException;
		 */

		DeliverCallback deliverCallback = (str, delivery) -> {

			Object rmqMessage = null;
			try {
				log.info("delivery.getBody(): {}", delivery.getBody());
				rmqMessage =  SerializationUtils.deserialize(delivery.getBody());
				log.info("RabbitMQ сообщение: {}", rmqMessage);
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
}