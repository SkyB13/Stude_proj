import cv2 as cv
import numpy as np


def kanny(path,kernelSize,sigma,lowScale,highScale):
    #Переводим в черно-белое (серое)
    img = cv.imread(path, cv.IMREAD_GRAYSCALE)
    cv.imshow("Grayscale_img", img)
    gauss = cv.GaussianBlur(img, (kernelSize, kernelSize), sigma)
    cv.imshow("Gaussian_img", gauss)

    cv.waitKey(0)
    cv.destroyAllWindows()

    length = np.zeros(gauss.shape)
    angle = np.zeros(gauss.shape)

    for x in range(1, (len(gauss) - 1)):
        for y in range(1, len(gauss[0]) - 1):
            Gx = gauss[x + 1][y + 1] - gauss[x - 1][y - 1] + gauss[x + 1][y - 1] - gauss[x - 1][y + 1] + 2 * (
                    gauss[x + 1][y] - gauss[x - 1][y])
            Gy = gauss[x + 1][y + 1] - gauss[x - 1][y - 1] + gauss[x - 1][y + 1] - gauss[x + 1][y - 1] + 2 * (
                    gauss[x][y + 1] - gauss[x][y - 1])
            length[x][y] = np.sqrt(Gx**2 + Gy**2)
            tg = np.arctan(Gy / Gx)

            print(Gx, Gy, length[x][y])

            if 0 < Gx and Gy < 0 and tg < -2.414 or Gx < 0 and Gy < 0 and tg > 2.414:
                angle[x][y] = 0
            elif Gx > 0 and Gy < 0 and tg < -0.414:
                angle[x][y] = 1
            elif (Gx > 0 and Gy < 0 and tg > -0.414) or (Gx > 0 and Gy > 0 and tg < 0.414):
                angle[x][y] = 2
            elif Gx > 0 and Gy > 0 and tg < 2.414:
                angle[x][y] = 3
            elif (Gx > 0 and Gy > 0 and tg > 2.414) or (Gx < 0 and Gy > 0 and tg < -2.414):
                angle[x][y] = 4
            elif Gx < 0 and Gy > 0 and tg < -0.414:
                angle[x][y] = 5
            elif (Gx < 0 and Gy > 0 and tg > -0.414) or (Gx < 0 and Gy < 0 and tg < 0.414):
                angle[x][y] = 6
            elif Gx < 0 and Gy < 0 and tg < 2.414:
                angle[x][y] = 7


    cv.imshow("lengths", length)
    cv.imshow("angles", angle)
    cv.waitKey(0)
    cv.destroyAllWindows()

    maxLength = np.max(length)
    borders=np.zeros(gauss.shape)

    for x in range(1, (len(gauss) - 1)):
        for y in range(1, len(gauss[0]) - 1):
            ix = 0
            iy = 0
            if (angle[x][y] == 0):
                iy = -1
            if (angle[x][y] == 1):
                iy = -1
                ix = 1
            if (angle[x][y] == 2):
                ix = 1
            if (angle[x][y] == 3):
                iy = 1
                ix = 1
            if (angle[x][y] == 4):
                iy = 1
            if (angle[x][y] == 5):
                iy = 1
                ix = -1
            if (angle[x][y] == 6):
                ix = -1
            if (angle[x][y] == 7):
                iy = -1
                ix = -1
            border = length[x][y] > length[x + ix][y + iy] and length[x][y] > length[x - ix][y - iy]
            borders[x][y] = 255 if border else 0

    cv.imshow("borders", borders)
    cv.waitKey(0)
    cv.destroyAllWindows()

    lowLevel = maxLength//lowScale
    highLevel = maxLength//highScale

    for x in range(1, (len(gauss) - 1)):
        for y in range(1, len(gauss[0]) - 1):
            if(borders[x][y] == 255):
                if(length[x][y] < lowLevel):
                    borders[x][y] = 0


    for x in range(1, (len(gauss) - 1)):
        for y in range(1, len(gauss[0]) - 1):
            if (borders[x][y] == 255):
                if (length[x][y] <= highLevel):
                    if (borders[x-1][y-1] == 255 or borders[x-1][y] == 255 or borders[x-1][y+1] == 255 or borders[x][y+1] == 255 or borders[x+1][y+1] == 255 or borders[x+1][y] == 255 or borders[x+1][y-1] == 255 or borders[x][y-1] == 255):
                        borders[x][y] = 255
                    else:
                        borders[x][y] = 0

    cv.imshow("Media_lab/umaru_mood.jpg", borders)
    cv.waitKey(0)
    cv.destroyAllWindows()

#kanny("Media_lab/umaru_mood.jpg", 5, 100, 1.0250, 1.0200)
#kanny("Media_lab/umaru_mood.jpg", 11, 100, 1.0500, 1.0400)
kanny("Media_lab/umaru_mood.jpg", 7, 100, 1.0700, 1.0500)