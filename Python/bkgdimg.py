import cv2 as cv
import matplotlib.pyplot as plt

img1 = cv.imread("1.jpg")
img2 = cv.imread("2.jpg")
img3 = cv.imread("3.jpg")
img4 = cv.imread("4.jpg")

plt.subplot(2,2,1)
plt.imshow(img1)
plt.xticks([])
plt.yticks([])

plt.subplot(2,2,2)
plt.imshow(img2)
plt.xticks([])
plt.yticks([])

plt.subplot(2,2,3)
plt.imshow(img3)
plt.xticks([])
plt.yticks([])

plt.subplot(2,2,4)
plt.imshow(img4)
plt.xticks([])
plt.yticks([])

plt.show()