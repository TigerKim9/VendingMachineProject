package com.vendingMachine.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

	// MQTT 브로커 주소 (로컬 테스트용 - 실제로는 외부 브로커 사용)
	private static final String MQTT_BROKER_URL = "tcp://localhost:1883";
	private static final String MQTT_CLIENT_ID = "VendingMachineServer";
	private static final String MQTT_USERNAME = "admin"; // 실제 환경에서는 환경변수로
	private static final String MQTT_PASSWORD = "password"; // 실제 환경에서는 환경변수로

	// MQTT 토픽
	public static final String TOPIC_DEVICE_STATUS = "vending/+/status"; // vending/{deviceId}/status
	public static final String TOPIC_DEVICE_SALE = "vending/+/sale";
	public static final String TOPIC_DEVICE_ERROR = "vending/+/error";
	public static final String TOPIC_SERVER_COMMAND = "vending/+/command";

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { MQTT_BROKER_URL });
		options.setUserName(MQTT_USERNAME);
		options.setPassword(MQTT_PASSWORD.toCharArray());
		options.setCleanSession(true);
		options.setAutomaticReconnect(true);
		options.setConnectionTimeout(10);
		factory.setConnectionOptions(options);
		return factory;
	}

	// 메시지 수신 채널
	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	// MQTT 메시지 수신 어댑터
	@Bean
	public MqttPahoMessageDrivenChannelAdapter inbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
				MQTT_CLIENT_ID + "_inbound", mqttClientFactory(),
				TOPIC_DEVICE_STATUS, TOPIC_DEVICE_SALE, TOPIC_DEVICE_ERROR);
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	// 메시지 발신 채널
	@Bean
	public MessageChannel mqttOutputChannel() {
		return new DirectChannel();
	}

	// MQTT 메시지 발신 핸들러
	@Bean
	@ServiceActivator(inputChannel = "mqttOutputChannel")
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
				MQTT_CLIENT_ID + "_outbound", mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic(TOPIC_SERVER_COMMAND);
		messageHandler.setDefaultQos(1);
		return messageHandler;
	}
}
