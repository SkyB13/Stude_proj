import cv2
import numpy as np
import matplotlib.pyplot as plt

#Задание 1. Построение матрицы Гаусса, просмотр итоговых матриц для размерностей 3, 5, 7
def create_gaussian_matrix(size, sigma):
    # Создаем одномерную ядро Гаусса
    kernel1d = cv2.getGaussianKernel(size, sigma)

    # Создаем двумерное ядро путем внешнего произведения
    kernel2d = np.outer(kernel1d, kernel1d)

    return kernel2d

# Создаем матрицы для размерностей 3, 5, 7
matrices = {
    3: create_gaussian_matrix(3, 0),
    5: create_gaussian_matrix(5, 0),
    7: create_gaussian_matrix(7, 0)
}
# np.set_printoptions(precision=2, suppress=True)
#
# for size, matrix in matrices.items():
#     print(f"Матрица размером {size}x{size}:")
#     print(matrix)
#     print("\n")


#Задание 2. Нормирование матрицы Гаусса
def check_normalization(matrix):
    return np.isclose(matrix.sum(), 1)

for size, matrix in matrices.items():
    print(f"Сумма элементов матрицы {size}x{size}: {matrix.sum():.4f}")
    print(f"Матрица нормирована: {check_normalization(matrix)}")
    print("\n")


#Задание 3-4. Реализация фильтра Гаусса, применение созданного фильтра
def create_gaussian_kernel(size, sigma):
    if sigma == 0:
        kernel = np.zeros((size, size))
        kernel[size // 2, size // 2] = 1
    else:
        x = np.arange(-(size // 2), size // 2 + 1)
        kernel_1d = np.exp(-x ** 2 / (2 * sigma ** 2))
        kernel_1d /= kernel_1d.sum()
        kernel = np.outer(kernel_1d, kernel_1d)
    return kernel


def gaussian_filter(image, size, sigma):
    # Создаем ядро Гаусса
    kernel = create_gaussian_kernel(size, sigma)

    # Применяем свертку для каждого канала изображения
    channels = []
    for channel in cv2.split(image):
        filtered_channel = np.zeros_like(channel)
        pad_size = size // 2

        # Добавляем padding к изображению
        padded_image = np.pad(channel, ((pad_size, pad_size), (pad_size, pad_size)), mode='constant')

        # Применяем свертку
        for i in range(pad_size, image.shape[0] + pad_size):
            for j in range(pad_size, image.shape[1] + pad_size):
                patch = padded_image[i - pad_size:i + pad_size + 1, j - pad_size:j + pad_size + 1]
                filtered_channel[i - pad_size, j - pad_size] = np.sum(patch * kernel)

        channels.append(filtered_channel.astype(np.uint8))

    # Объединяем обработанные каналы обратно в одно изображение
    return cv2.merge(channels)

# Загрузка изображения
image = cv2.imread('Media_lab/sleepy_cat.jpg')

# Применение фильтра с разными параметрами
filtered_images = {
    "size=3, sigma=0": gaussian_filter(image, 3, 0),
    "size=5, sigma=1": gaussian_filter(image, 5, 1),
    "size=7, sigma=2": gaussian_filter(image, 7, 2),
}

# Вывод результатов
fig, axes = plt.subplots(2, 2, figsize=(10, 5))
axes[0, 0].imshow(cv2.cvtColor(image, cv2.COLOR_BGR2RGB))
axes[0, 0].set_title("Оригинальное изображение")

for ax, (key, img) in zip(axes.flat[1:], filtered_images.items()):
    ax.imshow(cv2.cvtColor(img, cv2.COLOR_BGR2RGB))
    ax.set_title(key)

plt.tight_layout()
plt.show()


#Задание 5. Применение существующего в cv2 фильтра Гаусса
def gaussian_filter(image, size, sigma):
    return cv2.GaussianBlur(image, (size, size), sigma)

while True:
    # Пример использования
    image = cv2.imread('Media_lab/sleepy_cat.jpg')
    filtered_image = gaussian_filter(image, 5, 1)

    cv2.imshow('Original', image)
    cv2.imshow('Filter', filtered_image)

    key = cv2.waitKey(1) & 0xFF
    if key == 27:
        break

cv2.destroyAllWindows()