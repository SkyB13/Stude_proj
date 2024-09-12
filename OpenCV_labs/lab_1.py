import cv2
import numpy as np


def main():
    # Задание 1: Вывод изображения
    print("\nЗадание 1: Вывод изображения")
    img = cv2.imread("sleepy_cat.jpg", cv2.IMREAD_COLOR)

    cv2.namedWindow('Image', cv2.WINDOW_NORMAL)
    cv2.resizeWindow('Image', 800, 600)
    cv2.imshow('Image', img)
    cv2.waitKey(2000)  # Пауза на 2 секунды

    # Тестирование разных расширений и флагов
    print("\nТестирование разных расширений и флагов")
    img_bgr = cv2.imread('sleepy_cat.jpg', cv2.IMREAD_COLOR)
    img_gray = cv2.imread('sleepy_cat.jpg', cv2.IMREAD_GRAYSCALE)
    img_unchanged = cv2.imread('sleepy_cat.jpg', cv2.IMREAD_UNCHANGED)

    cv2.namedWindow('Normal Window', cv2.WINDOW_NORMAL)
    cv2.namedWindow('Auto Resize Window', cv2.WINDOW_AUTOSIZE)
    cv2.namedWindow('Fullscreen Window', cv2.WND_PROP_FULLSCREEN)

    cv2.imshow('Normal Window', img_bgr)
    cv2.imshow('Auto Resize Window', img_gray)
    cv2.imshow('Fullscreen Window', img_unchanged)

    cv2.waitKey(3000)  # Пауза на 3 секунды

    cv2.destroyAllWindows()

    # # Задание 2: Отображение видео
    # print("\nЗадание 2: Отображение видео")
    # cap = cv2.VideoCapture('large.mp4')

    # while True:
    #     ret, frame = cap.read()
    #     if not ret:
    #         break

    #     resized_frame = cv2.resize(frame, (640, 480))
    #     gray_frame = cv2.cvtColor(resized_frame, cv2.COLOR_BGR2GRAY)

    #     cv2.imshow('Original Video', resized_frame)
    #     cv2.imshow('Grayscale Video', gray_frame)

    #     if cv2.waitKey(1) & 0xFF == ord('q'):
    #         break

    # cap.release()
    # cv2.destroyAllWindows()

    # # Задание 3: Запись видео из файла в другой файл
    # print("\nЗадание 3: Запись видео из файла в другой файл")
    # cap = cv2.VideoCapture('large.mp4')
    # width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    # height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
    # fps = cap.get(cv2.CAP_PROP_FPS)

    # fourcc = cv2.VideoWriter_fourcc(*'mp4v')
    # out = cv2.VideoWriter('output_video.mp4', fourcc, fps, (width, height))

    # while True:
    #     ret, frame = cap.read()
    #     if not ret:
    #         break

    #     processed_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    #     processed_frame = cv2.cvtColor(processed_frame, cv2.COLOR_GRAY2BGR)

    #     out.write(processed_frame)

    # cap.release()
    # out.release()
    # cv2.destroyAllWindows()

    # # Задание 4: Преобразование изображения в HSV и отображение
    # print("\nЗадание 4: Преобразование изображения в HSV и отображение")
    # img = cv2.imread('photo_2024-09-06_15-58-59.jpg')
    # hsv_img = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    # cv2.namedWindow('Original Image', cv2.WINDOW_NORMAL)
    # cv2.namedWindow('HSV Image', cv2.WINDOW_NORMAL)

    # cv2.imshow('Original Image', img)
    # cv2.imshow('HSV Image', hsv_img)

    # cv2.waitKey(3000)  # Пауза на 3 секунды

    # cv2.destroyAllWindows()
