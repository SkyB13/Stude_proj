import cv2
import numpy as np

#Захват кадра с камеры
cap = cv2.VideoCapture(0)

while True:
    ret_frame, frame = cap.read()
    if not ret_frame:
        break

    #Задание 1. Преобразование в HSV
    hsv_img = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    #Задание 2. Задаем цвет (красный) для HSV
    left_red = np.array([0, 70, 50])
    right_red = np.array([10, 255, 255])

    #Создание и применение маски для указанного цвета
    mask = cv2.inRange(hsv_img, left_red, right_red)
    result = cv2.bitwise_and(frame, frame, mask=mask)

    #Задание 3. Морфологические преобразования
    kernel = np.ones((5, 5), np.uint8)
    opening = cv2.morphologyEx(mask, cv2.MORPH_OPEN, kernel, iterations=2)
    closing = cv2.morphologyEx(opening, cv2.MORPH_CLOSE, kernel, iterations=2)

    #Задание 4, задание 5. Вычисление моментов и поиск контуров
    # Создание копии исходного кадра
    drawFrame = frame.copy()
    moment = cv2.moments(opening)
    area = moment['m00']

    if (moment["m00"] != 0):
        x_val = int(moment['m10'] / moment['m00'])
        y_val = int(moment['m01'] / moment['m00'])
        weight = int(np.sqrt(moment['m00']))
        high = int(moment['m00']//weight)
        cv2.rectangle(drawFrame, (x_val - weight//20, y_val - high//20), (x_val + weight//20, y_val + high//20), (0, 0, 255), 4)
        cv2.putText(drawFrame, f'Area: {area}', (x_val, y_val-10), cv2.FONT_HERSHEY_COMPLEX, 0.9, (0, 0, 0), 2)

    #Вывод результатов (оригинал, перевод в HSV, маска, результат маски, открытие и закрытие (морфологическое преобразование))
    cv2.imshow('Original img', frame)
    cv2.imshow('HSV img', hsv_img)
    cv2.imshow('Mask img', mask)
    cv2.imshow('Result img', result)
    cv2.imshow('Opening img', opening)
    cv2.imshow('Closing img', closing)
    cv2.imshow('Final img', drawFrame)

    #Ожидание закрытия (если нажата клавиша esc, программа завершает выполнение)
    key = cv2.waitKey(1) & 0xFF
    if key == 27:
        break

cap.release()
cv2.destroyAllWindows()


