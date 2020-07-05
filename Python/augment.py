import cv2 as cv
import os
from glob import glob

#图像增强 转置、水平翻转、垂直翻转、水平垂直翻转
#获取.py文件所在路径
cur_path = os.path.abspath('.')

#创建输出到的文件夹
outpath = os.path.join(cur_path,"Argument")
if not os.path.exists(outpath):
    os.mkdir(outpath)

image_list = glob(os.path.join(cur_path, "*.jpg"))

for imgpath in image_list:
    print("handleing : " + imgpath)
    img=cv.imread(imgpath)
    #图像转置
    img_trans=cv.transpose(img)
    #垂直翻转
    img_flip0=cv.flip(img,0)
    #水平翻转
    img_flip1=cv.flip(img,1)
    #水平垂直翻转
    img_flip_1=cv.flip(img,-1)
    
    cv.imwrite(outpath + "\\trans_" +  os.path.basename(imgpath),img_trans)
    cv.imwrite(outpath + "\\flip0_" +  os.path.basename(imgpath),img_flip0)
    cv.imwrite(outpath + "\\flip1_" +  os.path.basename(imgpath),img_flip1)
    cv.imwrite(outpath + "\\flip_1_" +  os.path.basename(imgpath),img_flip_1)
    print("ok")

print("augmentover.")





