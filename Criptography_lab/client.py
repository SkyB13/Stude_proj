# import hashlib
# import itertools
# import random
# import string
# import socket
# import threading
# import time
#
# # Глобальный флаг для остановки потоков
# collision_found = threading.Event()
#
#
# def find_collision(target_hash, start_char, end_char):
#     chars = string.ascii_letters + string.digits + string.punctuation
#     length = 4  # Начинаем с более коротких строк
#     while not collision_found.is_set():  # Проверяем, не найдена ли уже коллизия
#         for combination in itertools.product(chars[start_char:end_char], repeat=length):
#             if collision_found.is_set():  # Проверяем флаг перед каждой итерацией
#                 return
#             candidate = ''.join(combination)
#             candidate_hash = hashlib.md5(candidate.encode()).hexdigest()
#             if candidate_hash == target_hash:
#                 print(f"Найдена коллизия: {candidate} -> {candidate_hash}")
#                 collision_found.set()  # Устанавливаем флаг
#                 send_to_server("admin", candidate)  # Отправляем данные на сервер
#                 return
#         length += 1
#     print(f"Увеличиваем длину строки до {length}...")
#
#
# def send_to_server(login, password):
#     client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#     client_socket.connect(('localhost', 12345))
#
#     password_hash = hashlib.md5(password.encode()).hexdigest()
#     data = f"{login}:{password_hash}"
#
#     client_socket.send(data.encode())
#
#     response = client_socket.recv(1024).decode()
#     print("Ответ сервера:", response)
#
#     client_socket.close()
#
#     time.sleep(random.uniform(1, 5))
#
#
# if __name__ == "__main__":
#     #target_hash = "5d41402abc4b2a76b9719d911017c592" #случай, когда таргет у клиента и сервера один - подбор с попаданием
#     #ВАЖНА СТАРТОВАЯ СТРОКА С КОЛИЧЕСТВОМ СИМВОЛОВ, ПОТОМУ НАЧИНАЙ С МИНИМУМА
#     target_hash = "5f4dcc3b5aa765d61d8327deb882cf99" #случай, когда таргет у клиента один, у сервера другой - вечный поиск
#     threads = []
#     num_threads = 2
#     chunk_size = len(string.ascii_letters + string.digits + string.punctuation) // num_threads
#
#     for i in range(num_threads):
#         start_char = i * chunk_size
#         end_char = (i + 1) * chunk_size if i != num_threads - 1 else None
#         thread = threading.Thread(target=find_collision, args=(target_hash, start_char, end_char))
#         threads.append(thread)
#         thread.start()
#
#     for thread in threads:
#         thread.join()
#
#     print("Все потоки завершены.")


import hashlib
import itertools
import random
import string
import socket
import threading
import time

collision_found = threading.Event()

def find_collision(target_hash, start_char, end_char, max_length=5):
    chars = string.ascii_letters + string.digits + string.punctuation

    for length in range(4, max_length + 1):
        for combination in itertools.product(chars[start_char:end_char], repeat=length):
            if collision_found.is_set():
                return
            candidate = ''.join(combination)
            candidate_hash = hashlib.md5(candidate.encode()).hexdigest()
            print(f"Проверка: {candidate} -> {candidate_hash}")  # Логирование проверки
            if candidate_hash == target_hash:
                print(f"Найдена коллизия: {candidate} -> {candidate_hash}")
                collision_found.set()
                send_to_server("admin", candidate)
                return
    print(f"Увеличиваем длину строки до {length + 1}...")


def send_to_server(login, password):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect(('localhost', 12345))

    password_hash = hashlib.md5(password.encode()).hexdigest()
    data = f"{login}:{password_hash}"
    client_socket.send(data.encode())

    response = client_socket.recv(1024).decode()
    print("Ответ сервера:", response)
    client_socket.close()
    time.sleep(random.uniform(1, 5))

if __name__ == "__main__":
    target_hash = "5f4dcc3b5aa765d61d8327deb882cf99" #password; не успевает дойти до коллизии
    threads = []
    num_threads = 3
    chunk_size = len(string.ascii_letters + string.digits + string.punctuation) // num_threads

    for i in range(num_threads):
        start_char = i * chunk_size
        end_char = (i + 1) * chunk_size if i != num_threads - 1 else None
        thread = threading.Thread(target=find_collision, args=(target_hash, start_char, end_char))
        threads.append(thread)
        thread.start()

    for thread in threads:
        thread.join()

    print("Все потоки завершены.")