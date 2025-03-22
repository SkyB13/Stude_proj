import hashlib
import socket
import time
import logging
from collections import defaultdict

attempt_count = defaultdict(int)
# Заданный хэш для проверки
#TARGET_HASH = "5d41402abc4b2a76b9719d911017c592" #hello
TARGET_HASH = "5f4dcc3b5aa765d61d8327deb882cf99" #password
logging.basicConfig(filename='server.log', level=logging.INFO)


def start_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('localhost', 12345))
    server_socket.listen(1)

    print("Сервер запущен и ожидает подключения...")

    while True:
        client_socket, addr = server_socket.accept()
        print(f"Подключен клиент: {addr}")
        logging.info(f"Подключен клиент: {addr}")

        data = client_socket.recv(1024).decode()
        login, password_hash = data.split(':')

        time.sleep(1)  # Задержка для эмуляции обработки

        if attempt_count[addr[0]] >= 3:
            response = "Превышено количество попыток. Доступ заблокирован."
        elif password_hash == TARGET_HASH:
            response = "Аутентификация успешна!"
        else:
            response = "Ошибка аутентификации!"
            attempt_count[addr[0]] += 1

        logging.info(f"Попытка аутентификации: {login}, результат: {response}")

        client_socket.send(response.encode())
        client_socket.close()


if __name__ == "__main__":
    start_server()
