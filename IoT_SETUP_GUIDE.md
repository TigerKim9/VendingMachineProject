# 🤖 자판기 IoT 연동 가이드

실제 자판기 하드웨어와 Spring Boot 서버를 연결하는 방법입니다.

## 📋 목차
1. [아키텍처 개요](#아키텍처-개요)
2. [필요한 하드웨어](#필요한-하드웨어)
3. [MQTT 브로커 설치](#mqtt-브로커-설치)
4. [서버 설정](#서버-설정)
5. [라즈베리파이 클라이언트 설치](#라즈베리파이-클라이언트-설치)
6. [통신 프로토콜](#통신-프로토콜)
7. [하드웨어 연결](#하드웨어-연결)

---

## 🏗️ 아키텍처 개요

```
┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
│  자판기 장치    │  MQTT   │  MQTT Broker    │  MQTT   │  Spring Boot    │
│ (라즈베리파이)  │ ←─────→ │   (Mosquitto)   │ ←─────→ │     서버        │
│                 │         │                 │         │                 │
│  - 센서         │         │  - Port: 1883   │         │  - Port: 8082   │
│  - 모터 제어    │         │  - WebSocket    │         │  - MySQL DB     │
│  - 결제 단말    │         │                 │         │  - 웹 관리      │
└─────────────────┘         └─────────────────┘         └─────────────────┘
        ↓                           ↓                           ↓
   GPIO 제어                  메시지 중계                  데이터 관리
```

---

## 🔧 필요한 하드웨어

### 최소 구성
- **라즈베리파이 4B** (2GB 이상)
- **릴레이 모듈** (8채널 - 상품 배출용)
- **온도 센서** (DHT22 또는 DS18B20)
- **카드 리더기** (NFC/RFID)
- **WiFi 연결** 또는 **이더넷**

### 권장 구성
- **PLC** (산업용 자판기)
- **LCD 디스플레이** (사용자 인터페이스)
- **동전/지폐 인식기**
- **UPS** (무정전 전원 장치)

---

## 📡 MQTT 브로커 설치

### 1. Mosquitto 설치 (Ubuntu/Debian)

```bash
# 패키지 업데이트
sudo apt update

# Mosquitto 설치
sudo apt install mosquitto mosquitto-clients -y

# 서비스 시작
sudo systemctl start mosquitto
sudo systemctl enable mosquitto

# 상태 확인
sudo systemctl status mosquitto
```

### 2. 설정 파일 수정

```bash
sudo nano /etc/mosquitto/mosquitto.conf
```

다음 내용 추가:
```
listener 1883
allow_anonymous false
password_file /etc/mosquitto/passwd
```

### 3. 사용자 계정 생성

```bash
# 비밀번호 파일 생성
sudo mosquitto_passwd -c /etc/mosquitto/passwd admin

# 재시작
sudo systemctl restart mosquitto
```

### 4. 방화벽 설정

```bash
sudo ufw allow 1883/tcp
```

---

## ⚙️ 서버 설정

### 1. application.properties 수정

```properties
# MQTT 설정
mqtt.broker.url=tcp://localhost:1883
mqtt.broker.username=admin
mqtt.broker.password=your_password
mqtt.client.id=VendingMachineServer
```

### 2. MqttConfig.java 수정

`src/main/java/com/vendingMachine/config/MqttConfig.java` 파일에서:

```java
private static final String MQTT_BROKER_URL = "tcp://your-server-ip:1883";
private static final String MQTT_USERNAME = "admin";
private static final String MQTT_PASSWORD = "your_password";
```

---

## 🥧 라즈베리파이 클라이언트 설치

### 1. Python 패키지 설치

```bash
# Python3 및 pip 설치
sudo apt update
sudo apt install python3 python3-pip -y

# MQTT 라이브러리 설치
pip3 install paho-mqtt

# REST API용 (선택)
pip3 install requests
```

### 2. GPIO 라이브러리 설치

```bash
# RPi.GPIO 설치
pip3 install RPi.GPIO

# 또는 gpiozero (더 쉬운 사용법)
pip3 install gpiozero
```

### 3. 클라이언트 코드 복사

```bash
# 파일 복사
scp raspberry_pi_client.py pi@your-pi-ip:~/

# 또는 직접 생성
nano ~/vending_machine_client.py
```

### 4. 설정 수정

`raspberry_pi_client.py` 파일에서:

```python
MQTT_BROKER = "your-server-ip"  # 서버 IP 주소
MQTT_USERNAME = "admin"
MQTT_PASSWORD = "your_password"
DEVICE_KEY = "VENDING_001"  # 자판기 고유 ID
```

### 5. 실행

```bash
# 실행
python3 ~/vending_machine_client.py

# 백그라운드 실행
nohup python3 ~/vending_machine_client.py &

# 부팅 시 자동 실행 (systemd)
sudo nano /etc/systemd/system/vending-machine.service
```

systemd 서비스 파일:
```ini
[Unit]
Description=Vending Machine IoT Client
After=network.target

[Service]
ExecStart=/usr/bin/python3 /home/pi/vending_machine_client.py
WorkingDirectory=/home/pi
StandardOutput=inherit
StandardError=inherit
Restart=always
User=pi

[Install]
WantedBy=multi-user.target
```

```bash
sudo systemctl enable vending-machine
sudo systemctl start vending-machine
```

---

## 📨 통신 프로토콜

### MQTT 토픽 구조

```
vending/{deviceId}/status    # 자판기 → 서버 (상태)
vending/{deviceId}/sale      # 자판기 → 서버 (판매)
vending/{deviceId}/error     # 자판기 → 서버 (에러)
vending/{deviceId}/command   # 서버 → 자판기 (명령)
```

### 메시지 포맷 (JSON)

#### 1. Heartbeat (자판기 → 서버)
```json
{
  "deviceKey": "VENDING_001",
  "messageType": "HEARTBEAT",
  "payload": {
    "status": "ONLINE",
    "temperature": 23.5,
    "uptime": 123456
  },
  "timestamp": 1699999999000
}
```

#### 2. 판매 이벤트 (자판기 → 서버)
```json
{
  "deviceKey": "VENDING_001",
  "messageType": "SALE",
  "payload": {
    "slotNumber": 1,
    "productId": 101,
    "quantity": 1,
    "price": 1500,
    "paymentMethod": "CARD",
    "timestamp": 1699999999000
  },
  "timestamp": 1699999999000
}
```

#### 3. 에러 (자판기 → 서버)
```json
{
  "deviceKey": "VENDING_001",
  "messageType": "ERROR",
  "payload": {
    "errorCode": 100,
    "errorMessage": "슬롯 5 상품 배출 실패"
  },
  "timestamp": 1699999999000
}
```

#### 4. 명령 (서버 → 자판기)
```json
{
  "commandType": "DISPENSE",
  "payload": {
    "slotNumber": 3
  },
  "timestamp": 1699999999000
}
```

---

## 🔌 하드웨어 연결

### 라즈베리파이 GPIO 핀맵

```python
# 상품 배출 릴레이 (8개 슬롯)
SLOT_PINS = {
    1: 17,  # GPIO 17
    2: 18,  # GPIO 18
    3: 27,  # GPIO 27
    4: 22,  # GPIO 22
    5: 23,  # GPIO 23
    6: 24,  # GPIO 24
    7: 25,  # GPIO 25
    8: 4    # GPIO 4
}

# 온도 센서
TEMP_SENSOR_PIN = 5  # GPIO 5

# 카드 리더기 (SPI)
# MOSI: GPIO 10
# MISO: GPIO 9
# SCK: GPIO 11
# CS: GPIO 8
```

### 연결 예제 코드

```python
import RPi.GPIO as GPIO
import time

# GPIO 설정
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# 릴레이 핀 설정 (출력)
for pin in SLOT_PINS.values():
    GPIO.setup(pin, GPIO.OUT)
    GPIO.output(pin, GPIO.LOW)

def dispense_product(slot_number):
    """상품 배출"""
    pin = SLOT_PINS.get(slot_number)
    if pin:
        print(f"슬롯 {slot_number} 배출 시작")
        GPIO.output(pin, GPIO.HIGH)  # 릴레이 ON
        time.sleep(2)                # 2초 대기
        GPIO.output(pin, GPIO.LOW)   # 릴레이 OFF
        print(f"슬롯 {slot_number} 배출 완료")
        return True
    return False
```

---

## 🔐 보안 고려사항

1. **디바이스 인증**: `deviceKey`를 DB에 등록하고 검증
2. **MQTT TLS**: 프로덕션 환경에서는 TLS 암호화 필수
3. **방화벽**: 필요한 포트만 열기 (1883, 8082)
4. **API 토큰**: REST API에 JWT 토큰 인증 추가

---

## 🧪 테스트

### 1. MQTT 브로커 테스트

```bash
# 구독 테스트
mosquitto_sub -h localhost -t "vending/+/status" -u admin -P your_password

# 발행 테스트
mosquitto_pub -h localhost -t "vending/VENDING_001/status" -m '{"test":"message"}' -u admin -P your_password
```

### 2. REST API 테스트

```bash
# REST 클라이언트 실행
python3 raspberry_pi_rest_client.py

# 또는 curl
curl -X POST http://localhost:8082/api/iot/message \
  -H "Content-Type: application/json" \
  -d '{
    "deviceKey": "VENDING_001",
    "messageType": "HEARTBEAT",
    "payload": {"status": "ONLINE"},
    "timestamp": 1699999999000
  }'
```

---

## 📞 문제 해결

### MQTT 연결 안 됨
```bash
# 브로커 상태 확인
sudo systemctl status mosquitto

# 로그 확인
sudo tail -f /var/log/mosquitto/mosquitto.log

# 포트 확인
sudo netstat -tulpn | grep 1883
```

### GPIO 권한 오류
```bash
# pi 사용자에게 GPIO 권한 추가
sudo usermod -a -G gpio pi
sudo usermod -a -G spi pi
```

---

## 🎯 다음 단계

1. ✅ MQTT 브로커 설치 및 설정
2. ✅ 서버 MQTT 설정
3. ✅ 라즈베리파이 클라이언트 설치
4. ✅ 테스트 실행
5. ⬜ 실제 하드웨어 연결
6. ⬜ 프로덕션 배포

---

**문의사항이 있으시면 이슈를 등록해주세요!**
