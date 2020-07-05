import cv2 as cv
import matplotlib.pyplot as plt

img1 = cv.imread("011.jpg")
img2 = cv.imread("012.jpg")
img3 = cv.imread("013.jpg")
img4 = cv.imread("014.jpg")

mask1 = cv.imread("mask011.jpg")
mask2 = cv.imread("mask012.jpg")
mask3 = cv.imread("mask013.jpg")
mask4 = cv.imread("mask014.jpg")

plt.subplot(2,4,1)
plt.imshow(img1)
plt.xticks([])
plt.yticks([])

plt.subplot(2,4,2)
plt.imshow(mask1)
plt.xticks([])
plt.yticks([])

plt.subplot(2,4,3)
plt.imshow(img2)
plt.xticks([])
plt.yticks([])

plt.subplot(2,4,4)
plt.imshow(mask2)
plt.xticks([])
plt.yticks([])

plt.subplot(2,4,5)
plt.imshow(img3)
plt.xticks([])
plt.yticks([])

plt.subplot(2,4,6)
plt.imshow(mask3)
plt.xticks([])
plt.yticks([])

plt.subplot(2,4,7)
plt.imshow(img4)
plt.xticks([])
plt.yticks([])

plt.subplot(2,4,8)
plt.imshow(mask4)
plt.xticks([])
plt.yticks([])

plt.show()