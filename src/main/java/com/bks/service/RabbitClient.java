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
	/*public void initOutExchange() {
		try {
			notificationChannel = rmqConnection.createChannel();
			notificationChannel.exchangeDeclare(appConfig.getOutExchange(),  BuiltinExchangeType.DIRECT, true);
		} catch (IOException e) {
			log.error("Ошибка создания иксенджа  {} ",  e.getMessage());
		}
	}*/

	/**
	 * объявление и привязка очередей
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
	/*private  void publish(String exchange, String routingKey, String notification) {
		try {
			notificationChannel.basicPublish(exchange, routingKey, null, notification.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			log.error("Error while  publishing notification:{}", e.getMessage());
		}
	}*/

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

	/* убрать лишние аргументы */
	public void initSyncListener(Channel channel, String queueName, String exchange, String bindKey, Consumer<Object> consumer) {

		/*что за аргумент str ?*/
		DeliverCallback deliverCallback = (str, delivery) -> {

			Object rabbitMessage = null;
			try {
				rabbitMessage =  SerializationUtils.deserialize(delivery.getBody());
				log.info("RabbitMQ message: {}", rabbitMessage);
			} catch (Exception e) {
				log.error("Error while extracting RabbitMQ message: {}", e.getMessage());
			}

			consumer.accept(rabbitMessage);
		};
		try {
			/*кажется, не нужен , поскольку декларировал и привязал очередь к иксченджу на выходе
			declareAndBindQueue(queueName, exchange, bindKey);*/
			channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {	});

		} catch (IOException e) {
			log.error("Error while consuming message: " + e.getMessage());
		}
	}
}