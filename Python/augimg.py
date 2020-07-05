import cv2 as cv
import matplotlib.pyplot as plt

img1 = cv.imread("a.jpg")
img2 = cv.imread("1a.jpg")
img3 = cv.imread("2a.jpg")
img4 = cv.imread("3a.jpg")
img5 = cv.imread("4a.jpg")

mask1 = cv.imread("1.jpg")
mask2 = cv.imread("11.jpg")
mask3 = cv.imread("21.jpg")
mask4 = cv.imread("31.jpg")
mask5 = cv.imread("41.jpg")

plt.subplot(2,5,1)
plt.imshow(img1)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,2)
plt.imshow(img2)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,3)
plt.imshow(img3)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,4)
plt.imshow(img4)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,5)
plt.imshow(img5)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,6)
plt.imshow(mask1)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,7)
plt.imshow(mask2)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,8)
plt.imshow(mask3)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,9)
plt.imshow(mask4)
plt.xticks([])
plt.yticks([])

plt.subplot(2,5,10)
plt.imshow(mask5)
plt.xticks([])
plt.yticks([])

plt.show()