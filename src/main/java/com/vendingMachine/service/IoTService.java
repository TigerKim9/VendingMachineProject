package com.vendingMachine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendingMachine.iot.DTO.IoTCommand;
import com.vendingMachine.iot.DTO.IoTMessage;
import com.vendingMachine.iot.DTO.SaleEvent;

@Service
public class IoTService {

	@Autowired
	private MessageChannel mqttOutputChannel;

	@Autowired
	private ProductStockService productStockService;

	private ObjectMapper objectMapper = new ObjectMapper();

	// MQTT 메시지 수신 처리
	@ServiceActivator(inputChannel = "mqttInputChannel")
	public void handleMqttMessage(Message<?> message) {
		try {
			String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
			String payload = new String((byte[]) message.getPayload());

			System.out.println("Received MQTT message from topic: " + topic);
			System.out.println("Payload: " + payload);

			IoTMessage iotMessage = objectMapper.readValue(payload, IoTMessage.class);

			// 메시지 타입별 처리
			switch (iotMessage.getMessageType()) {
				case "HEARTBEAT":
					handleHeartbeat(iotMessage);
					break;
				case "SALE":
					handleSale(iotMessage);
					break;
				case "STOCK_CHANGE":
					handleStockChange(iotMessage);
					break;
				case "ERROR":
					handleError(iotMessage);
					break;
				default:
					System.out.println("Unknown message type: " + iotMessage.getMessageType());
			}
		} catch (Exception e) {
			System.err.println("Error processing MQTT message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Heartbeat 처리
	private void handleHeartbeat(IoTMessage message) {
		System.out.println("Device heartbeat: " + message.getDeviceKey());
		// TODO: 디바이스 상태 업데이트
	}

	// 판매 이벤트 처리
	private void handleSale(IoTMessage message) {
		try {
			SaleEvent saleEvent = objectMapper.convertValue(message.getPayload(), SaleEvent.class);
			System.out.println("Sale event: Product " + saleEvent.getProductId() +
				", Quantity: " + saleEvent.getQuantity());

			// TODO: 판매 이력 저장 및 재고 감소
			// productStockService.sellProductStock(...);
		} catch (Exception e) {
			System.err.println("Error processing sale event: " + e.getMessage());
		}
	}

	// 재고 변화 처리
	private void handleStockChange(IoTMessage message) {
		System.out.println("Stock change event from device: " + message.getDeviceKey());
		// TODO: 재고 변화 처리
	}

	// 에러 처리
	private void handleError(IoTMessage message) {
		System.err.println("Device error: " + message.getDeviceKey());
		System.err.println("Error details: " + message.getPayload());
		// TODO: 에러 로깅 및 알림
	}

	// 자판기에 명령 전송
	public void sendCommand(String deviceKey, IoTCommand command) {
		try {
			String topic = "vending/" + deviceKey + "/command";
			String payload = objectMapper.writeValueAsString(command);

			Message<String> message = MessageBuilder
					.withPayload(payload)
					.setHeader(MqttHeaders.TOPIC, topic)
					.setHeader(MqttHeaders.QOS, 1)
					.build();

			mqttOutputChannel.send(message);
			System.out.println("Command sent to device " + deviceKey + ": " + command.getCommandType());
		} catch (Exception e) {
			System.err.println("Error sending command: " + e.getMessage());
		}
	}

	// 상품 판매 명령 (테스트용)
	public void dispenseProduct(String deviceKey, Long slotNumber) {
		IoTCommand command = new IoTCommand();
		command.setCommandType("DISPENSE");
		command.setPayload("{\"slotNumber\":" + slotNumber + "}");
		command.setTimestamp(System.currentTimeMillis());
		sendCommand(deviceKey, command);
	}

	// 자판기 재부팅 명령
	public void rebootDevice(String deviceKey) {
		IoTCommand command = new IoTCommand();
		command.setCommandType("REBOOT");
		command.setTimestamp(System.currentTimeMillis());
		sendCommand(deviceKey, command);
	}
}
