import os
from glob import glob
import cv2 as cv
#from PIL import Image 
#把掩模图恢复成原图的大小
#获取.py文件所在路径
cur_path = os.path.abspath('.')

#创建输出到的文件夹
outpath = os.path.join(cur_path,"Remasks")
if not os.path.exists(outpath):
    os.mkdir(outpath)

#获取该目录下所有.jpg文件
image_list = glob(os.path.join(cur_path,"testImages", "*.jpg"))
mask_list = glob(os.path.join(cur_path,"testPreds", "*.jpg"))


for imgpath,maskpath in zip(image_list,mask_list):
    print("handeling : " + imgpath)
    img = cv.imread(imgpath)
    mask = cv.imread(maskpath)
    x,y = img.shape[0:2]
    mask = cv.resize(mask,(int(y),int(x)))
    cv.imwrite(outpath + "\\" +  os.path.basename(imgpath),mask)
    print("ok")

print("resizeover.")
# 待处理图片存储路径	
#im = Image.open('038.jpg')
# Resize图片大小，入口参数为一个tuple，新的图片大小
#imBackground = im.resize((3936,2624))
#处理后的图片的存储路径，以及存储格式
#imBackground.save('resize038.jpg','JPEG')