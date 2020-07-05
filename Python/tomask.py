import os
from glob import glob
import cv2 as cv
#把tomask.py所在目录下的所有.png图片转换成掩模图 存放在Masks文件夹内 名称和原图一样
#获取.py文件所在路径
cur_path = os.path.abspath('.')
#获取该目录下所有.png文件
image_list = glob(os.path.join(cur_path, "*.png"))
#print(image_list)
#创建输出到的文件夹
outpath = cur_path + "\\Masks"
if not os.path.exists(outpath):
    os.mkdir(outpath)

for path in image_list:
    img = cv.imread(path,cv.IMREAD_COLOR)
#    print(img.shape)
    print("handling : " + path)
    for i in range(img.shape[0]):
        for j in range(img.shape[1]):
                #三个通道都是0才是黑色 反之就不是黑色
                if ~( img[i][j][0] == 0 and img[i][j][1] == 0 and img[i][j][2] == 0 ):
                    img[i][j][0] = 255
                    img[i][j][1] = 255
                    img[i][j][2] = 255
    
    maskpath = outpath + "\\" + os.path.basename(path)
    cv.imwrite(maskpath,img)
    print("ok")
    
print("tomask over.")

