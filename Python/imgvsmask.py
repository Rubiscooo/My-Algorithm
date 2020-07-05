import cv2 as cv
import matplotlib.pyplot as plt
import os
from glob import glob

cur_path = os.path.abspath('.')

#获取该目录下所有.jpg文件
image_list = glob(os.path.join(cur_path,"testImages", "*.jpg"))
mask_list = glob(os.path.join(cur_path,"testPreds", "*.jpg"))

for i,imgpath,maskpath in zip(range(0,4,2),image_list,mask_list):
    plt.subplot(2,2,i+1)
    plt.imshow(cv.imread(imgpath))
    plt.xticks([])
    plt.yticks([])
    
    plt.subplot(2,2,i+2)
    plt.imshow(cv.imread(maskpath))
    plt.xticks([])
    plt.yticks([])
    
plt.show()
#img1 = cv.imread("001.JPG")
#img2 = cv.imread("002.JPG")
#
#mask1 = cv.imread("mask001.JPG")
#mask2 = cv.imread("mask002.JPG")
#
#
#
#for i in range(9):
#    plt.subplot(2,5,i+1)
#    plt.imshow(mask1)
#    plt.xticks([])
#    plt.yticks([])
#
#plt.subplot(2,5,10)
#plt.imshow(img1)
#plt.xticks([])
#plt.yticks([])
#
#plt.show()

#plt.subplot(1,4,1)
##plt.imshow(cv.resize(img1,(256,256)))
#plt.imshow(img1)
#plt.xticks([])
#plt.yticks([])
#
#plt.subplot(1,4,2)
#plt.imshow(mask1)
#plt.xticks([])
#plt.yticks([])
#
#plt.subplot(2,2,3)
##plt.imshow(cv.resize(img2,(256,256)))
#plt.imshow(img2)
#plt.xticks([])
#plt.yticks([])
#
#plt.subplot(2,2,4)
##3936 2624
##plt.imshow(mask2)
#plt.imshow(cv.resize(mask2,(3936,2624)))
#plt.xticks([])
#plt.yticks([])
#
#plt.show()

#cv.imwrite("resize.jpg",cv.resize(mask2,(3936,2624)))