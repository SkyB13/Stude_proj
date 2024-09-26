import cv2
import numpy as np

#Захват кадра с камеры
cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    if not ret:
        break

    #Задание 1. Преобразование в HSV
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    #Задание 2. Задаем цвет (красный) для HSV
    lower_red = np.array([0, 70, 50])
    upper_red = np.array([10, 255, 255])

    #Создание и применение маски для указанного цвета
    mask = cv2.inRange(hsv, lower_red, upper_red)
    result = cv2.bitwise_and(frame, frame, mask=mask)

    #Задание 3. Морфологические преобразования
    kernel = np.ones((5, 5), np.uint8)
    opening = cv2.morphologyEx(mask, cv2.MORPH_OPEN, kernel, iterations=2)
    closing = cv2.morphologyEx(opening, cv2.MORPH_CLOSE, kernel, iterations=2)

    #Задание 4. Вычисление моментов и поиск контуров
    conts, _ = cv2.findContours(closing, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # Создание копии исходного кадра
    drawFrame = frame.copy()

    for cont in conts:
        moments = cv2.moments(cont)
        area = moments['m00']

        if area > 1000:  # Фильтрация по площади
            x, y, w, h = cv2.boundingRect(cont)

            # Построение контура (прямоугольника)
            cv2.rectangle(drawFrame, (x, y), (x + w, y + h), (0, 0, 0), 2)

            # Вывод получаемой площади
            cv2.putText(drawFrame, f'Area: {int(area)}', (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.9,
                        (0, 0, 0), 2)

    #Вывод результатов (несколько окон)
    cv2.imshow('Original', frame)
    cv2.imshow('HSV', hsv)
    cv2.imshow('Mask', mask)
    cv2.imshow('Result', result)
    cv2.imshow('Opening', opening)
    cv2.imshow('Closing', closing)
    cv2.imshow('Final Result', drawFrame)

    #Ожидание закрытия (если нажата клавиша esc, программа завершает выполнение)
    key = cv2.waitKey(1) & 0xFF
    if key == 27:
        break

cap.release()
cv2.destroyAllWindows()


