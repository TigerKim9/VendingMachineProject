#!/usr/bin/env python3
"""
자판기 IoT 클라이언트 (라즈베리파이용)
- MQTT 통신으로 서버와 연동
- 판매 이벤트 전송
- 서버 명령 수신
"""

import paho.mqtt.client as mqtt
import json
import time
import random
from datetime import datetime

# 설정
MQTT_BROKER = "your-server-ip"  # Spring Boot 서버 IP
MQTT_PORT = 1883
MQTT_USERNAME = "admin"
MQTT_PASSWORD = "password"
DEVICE_KEY = "VENDING_001"  # 자판기 고유 ID

# MQTT 토픽
TOPIC_STATUS = f"vending/{DEVICE_KEY}/status"
TOPIC_SALE = f"vending/{DEVICE_KEY}/sale"
TOPIC_ERROR = f"vending/{DEVICE_KEY}/error"
TOPIC_COMMAND = f"vending/{DEVICE_KEY}/command"


class VendingMachineClient:
    def __init__(self):
        self.client = mqtt.Client(client_id=f"VM_{DEVICE_KEY}")
        self.client.username_pw_set(MQTT_USERNAME, MQTT_PASSWORD)
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        self.is_connected = False

    def on_connect(self, client, userdata, flags, rc):
        """MQTT 연결 콜백"""
        if rc == 0:
            print(f"[✓] MQTT 브로커에 연결됨: {MQTT_BROKER}")
            self.is_connected = True
            # 서버 명령 토픽 구독
            client.subscribe(TOPIC_COMMAND)
            print(f"[✓] 구독: {TOPIC_COMMAND}")
            # 연결 후 첫 heartbeat 전송
            self.send_heartbeat()
        else:
            print(f"[✗] 연결 실패, 코드: {rc}")

    def on_message(self, client, userdata, msg):
        """MQTT 메시지 수신 콜백"""
        try:
            payload = json.loads(msg.payload.decode())
            command_type = payload.get("commandType")
            print(f"\n[←] 서버 명령 수신: {command_type}")
            print(f"   데이터: {json.dumps(payload, indent=2)}")

            # 명령 처리
            if command_type == "DISPENSE":
                self.handle_dispense_command(payload)
            elif command_type == "REBOOT":
                self.handle_reboot_command()
            elif command_type == "UPDATE_PRICE":
                self.handle_update_price(payload)
            else:
                print(f"[!] 알 수 없는 명령: {command_type}")

        except Exception as e:
            print(f"[✗] 메시지 처리 오류: {e}")

    def connect(self):
        """MQTT 브로커 연결"""
        try:
            print(f"[→] MQTT 브로커 연결 시도: {MQTT_BROKER}:{MQTT_PORT}")
            self.client.connect(MQTT_BROKER, MQTT_PORT, 60)
            self.client.loop_start()
            return True
        except Exception as e:
            print(f"[✗] 연결 오류: {e}")
            return False

    def send_heartbeat(self):
        """Heartbeat 전송 (30초마다)"""
        message = {
            "deviceKey": DEVICE_KEY,
            "messageType": "HEARTBEAT",
            "payload": {
                "status": "ONLINE",
                "temperature": round(random.uniform(20, 25), 1),
                "uptime": int(time.time())
            },
            "timestamp": int(time.time() * 1000)
        }
        self.client.publish(TOPIC_STATUS, json.dumps(message), qos=1)
        print(f"[→] Heartbeat 전송")

    def send_sale_event(self, slot_number, product_id, quantity, price, payment_method="CARD"):
        """판매 이벤트 전송"""
        message = {
            "deviceKey": DEVICE_KEY,
            "messageType": "SALE",
            "payload": {
                "slotNumber": slot_number,
                "productId": product_id,
                "quantity": quantity,
                "price": price,
                "paymentMethod": payment_method,
                "timestamp": int(time.time() * 1000)
            },
            "timestamp": int(time.time() * 1000)
        }
        self.client.publish(TOPIC_SALE, json.dumps(message), qos=1)
        print(f"\n[→] 판매 이벤트 전송:")
        print(f"   슬롯: {slot_number}, 상품ID: {product_id}")
        print(f"   수량: {quantity}, 가격: {price}원")

    def send_error(self, error_code, error_message):
        """에러 전송"""
        message = {
            "deviceKey": DEVICE_KEY,
            "messageType": "ERROR",
            "payload": {
                "errorCode": error_code,
                "errorMessage": error_message,
                "timestamp": int(time.time() * 1000)
            },
            "timestamp": int(time.time() * 1000)
        }
        self.client.publish(TOPIC_ERROR, json.dumps(message), qos=1)
        print(f"\n[!] 에러 전송: {error_message}")

    def handle_dispense_command(self, payload):
        """상품 판매 명령 처리"""
        slot_number = json.loads(payload["payload"]).get("slotNumber")
        print(f"[✓] 슬롯 {slot_number} 상품 판매 실행...")

        # 실제 하드웨어 제어 코드
        # GPIO.output(slot_pins[slot_number], GPIO.HIGH)
        # time.sleep(2)
        # GPIO.output(slot_pins[slot_number], GPIO.LOW)

        # 시뮬레이션
        time.sleep(1)
        print(f"[✓] 슬롯 {slot_number} 상품 배출 완료")

        # 판매 이벤트 전송
        self.send_sale_event(
            slot_number=slot_number,
            product_id=slot_number,  # 예제: 슬롯번호 = 상품ID
            quantity=1,
            price=1000,
            payment_method="CARD"
        )

    def handle_reboot_command(self):
        """재부팅 명령 처리"""
        print("[!] 재부팅 명령 수신 - 5초 후 재시작...")
        time.sleep(5)
        # os.system("sudo reboot")  # 실제 재부팅
        print("[✓] (시뮬레이션) 재부팅 완료")

    def handle_update_price(self, payload):
        """가격 업데이트 명령 처리"""
        print(f"[✓] 가격 업데이트: {payload}")

    def run(self):
        """메인 루프"""
        if not self.connect():
            return

        print("\n" + "="*50)
        print("자판기 IoT 클라이언트 시작")
        print(f"디바이스 ID: {DEVICE_KEY}")
        print("="*50 + "\n")

        heartbeat_interval = 30  # 30초
        last_heartbeat = time.time()

        try:
            while True:
                # 주기적 heartbeat 전송
                if time.time() - last_heartbeat > heartbeat_interval:
                    self.send_heartbeat()
                    last_heartbeat = time.time()

                # 시뮬레이션: 랜덤 판매 이벤트 (테스트용)
                # if random.random() < 0.01:  # 1% 확률로 판매 발생
                #     slot = random.randint(1, 10)
                #     self.send_sale_event(
                #         slot_number=slot,
                #         product_id=slot,
                #         quantity=1,
                #         price=random.choice([500, 1000, 1500, 2000]),
                #         payment_method=random.choice(["CARD", "CASH", "MOBILE"])
                #     )

                time.sleep(1)

        except KeyboardInterrupt:
            print("\n[!] 종료 중...")
            self.client.loop_stop()
            self.client.disconnect()
            print("[✓] 종료 완료")


if __name__ == "__main__":
    # 사용 예제
    vm_client = VendingMachineClient()
    vm_client.run()
