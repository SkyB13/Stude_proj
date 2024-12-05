import cv2
import numpy as np
import matplotlib.pyplot as plt

def gauss_ker(x, y, a, b, sigma):
    f_val = 1 / (2 * np.pi * (sigma ** 2))
    sec_val = (((x - a) ** 2) + ((y - b) ** 2)) / (2 * (sigma ** 2))
    return f_val * sec_val

def gaussian_blur(image, kern_size, sigma):
    kernel = np.ones((kern_size, kern_size))
    a = (kern_size + 1) // 2  # Координаты центрального элемента матрицы

    for i in range(kern_size):
        for j in range(kern_size):
            kernel[i, j] = gauss_ker(i, j, a, a, sigma)  #Вычисление функции Гаусса для каждого элемента матрицы

    # print("Матрица ядра свертки размером ", kern_size)
    # print(kernel)
    #
    # summ = kernel.sum()
    # kernel /= summ
    # print("Нормализованная матрица ядра свертки размером ", kern_size, " :", "\n", kernel, "\n")

    img_BLur = image.copy()
    x_y_start = kern_size // 2  # Начальные координаты для итераций по пикселям

    # Проходимся по каждому пикселю, кроме краев
    for i in range(x_y_start, img_BLur.shape[0] - x_y_start):
        for j in range(x_y_start, img_BLur.shape[1] - x_y_start):
            val = 0
            # Проходимся по каждому элементу ядра свертки
            for k in range(-x_y_start, x_y_start + 1):
                for l in range(-x_y_start, x_y_start + 1):
                    val += image[i + k, j + l] * kernel[k + x_y_start, l + x_y_start]
            img_BLur[i, j] = val  # Значение val становится новым значением пикселя в получаемом изображении

    print("Матрица ядра свертки размером ", kern_size)
    print(kernel)

    summ = kernel.sum()
    kernel /= summ
    print("Нормализованная матрица ядра свертки размером ", kern_size, " :", "\n", kernel, "\n")

    return img_BLur

image = cv2.imread("Media_lab/sleepy_cat.jpg", cv2.IMREAD_GRAYSCALE)

blur1 = gaussian_blur(image, 3, 100)
blur2 = gaussian_blur(image, 3, 10)
blur3 = gaussian_blur(image, 5, 100)
blur4 = gaussian_blur(image, 5, 10)
blur5 = gaussian_blur(image, 7, 100)
blur6 = gaussian_blur(image, 7, 10)

cv2.imshow("Original", image)
cv2.imshow("Kern_size 3 Standart_dev 100", blur1)
cv2.imshow("Kern_size 3 Standart_dev 10", blur2)
cv2.imshow("Kern_size 5 Standart_dev 100", blur3)
cv2.imshow("Kern_size 5 Standart_dev 10", blur4)
cv2.imshow("Kern_size 7 Standart_dev 100", blur5)
cv2.imshow("Kern_size 7 Standart_dev 10", blur6)

cv2.waitKey(0)
cv2.destroyAllWindows()

imageBlurInCv2 = cv2.GaussianBlur(image, (7, 7), 100)
cv2.imshow("CV2 Gaussian Blur", imageBlurInCv2)
cv2.imshow("gaussian_blur Realization", blur5)
cv2.waitKey(0)
cv2.destroyAllWindows()