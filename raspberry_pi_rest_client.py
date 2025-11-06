#!/usr/bin/env python3
"""
자판기 IoT 클라이언트 (REST API 버전)
- MQTT가 없는 환경에서 HTTP REST API로 통신
- 라즈베리파이, 아두이노 + WiFi 모듈 등에서 사용 가능
"""

import requests
import json
import time
from datetime import datetime

# 설정
SERVER_URL = "http://your-server-ip:8082"  # Spring Boot 서버 주소
DEVICE_KEY = "VENDING_001"
API_ENDPOINT = f"{SERVER_URL}/api/iot/message"


class VendingMachineRestClient:
    def __init__(self):
        self.device_key = DEVICE_KEY
        self.server_url = SERVER_URL

    def send_message(self, message_type, payload):
        """서버에 메시지 전송"""
        message = {
            "deviceKey": self.device_key,
            "messageType": message_type,
            "payload": payload,
            "timestamp": int(time.time() * 1000)
        }

        try:
            response = requests.post(
                API_ENDPOINT,
                json=message,
                timeout=10
            )

            if response.status_code == 200:
                print(f"[✓] {message_type} 전송 성공")
                return response.json()
            else:
                print(f"[✗] 전송 실패: {response.status_code}")
                return None

        except requests.exceptions.RequestException as e:
            print(f"[✗] 연결 오류: {e}")
            return None

    def send_heartbeat(self):
        """Heartbeat 전송"""
        payload = {
            "status": "ONLINE",
            "temperature": 23.5,
            "uptime": int(time.time())
        }
        return self.send_message("HEARTBEAT", payload)

    def send_sale_event(self, slot_number, product_id, quantity, price, payment_method="CARD"):
        """판매 이벤트 전송"""
        payload = {
            "slotNumber": slot_number,
            "productId": product_id,
            "quantity": quantity,
            "price": price,
            "paymentMethod": payment_method,
            "timestamp": int(time.time() * 1000)
        }
        print(f"\n[→] 판매: 슬롯 {slot_number}, 상품 {product_id}, {price}원")
        return self.send_message("SALE", payload)

    def send_error(self, error_code, error_message):
        """에러 전송"""
        payload = {
            "errorCode": error_code,
            "errorMessage": error_message
        }
        print(f"\n[!] 에러: {error_message}")
        return self.send_message("ERROR", payload)

    def check_server_status(self):
        """서버 상태 확인"""
        try:
            response = requests.get(f"{self.server_url}/api/iot/status", timeout=5)
            if response.status_code == 200:
                status = response.json()
                print(f"[✓] 서버 연결 성공")
                print(f"   서버 상태: {status.get('serverStatus')}")
                return True
            return False
        except:
            print(f"[✗] 서버 연결 실패")
            return False

    def run_demo(self):
        """데모 실행"""
        print("\n" + "="*50)
        print("자판기 IoT REST 클라이언트 (데모)")
        print(f"디바이스 ID: {self.device_key}")
        print(f"서버: {self.server_url}")
        print("="*50 + "\n")

        # 1. 서버 상태 확인
        if not self.check_server_status():
            print("서버에 연결할 수 없습니다. 서버 주소를 확인하세요.")
            return

        # 2. Heartbeat 전송
        time.sleep(1)
        self.send_heartbeat()

        # 3. 판매 이벤트 시뮬레이션
        time.sleep(2)
        print("\n--- 판매 이벤트 시뮬레이션 ---")
        self.send_sale_event(
            slot_number=1,
            product_id=101,
            quantity=1,
            price=1500,
            payment_method="CARD"
        )

        time.sleep(2)
        self.send_sale_event(
            slot_number=3,
            product_id=103,
            quantity=2,
            price=3000,
            payment_method="MOBILE"
        )

        # 4. 에러 전송 시뮬레이션
        time.sleep(2)
        print("\n--- 에러 이벤트 시뮬레이션 ---")
        self.send_error(
            error_code=100,
            error_message="슬롯 5 상품 배출 실패"
        )

        print("\n[✓] 데모 완료\n")


if __name__ == "__main__":
    client = VendingMachineRestClient()

    # 데모 실행
    client.run_demo()

    # 실제 사용 예제:
    # while True:
    #     # 주기적 heartbeat
    #     client.send_heartbeat()
    #     time.sleep(30)
