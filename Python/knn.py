import requests

from PIL import Image
from PIL import ImageDraw

import joblib

import numpy as np
import os

'''
下载 http://121.42.236.205/images/code.jpg 上的验证码图片
对图片进行二值化、降噪、切割，用训练好的模型进行识别
'''

down_path = "down_img/"
two_path = "down_img_two/"
cut_path = "down_img_cut/"
resize_path = "down_img_resize/"
path_list = [down_path,two_path,cut_path,resize_path]

#创建所需目录
def mkdirs():
    for path in path_list:
        if not os.path.exists(path):
            os.mkdir(path)

#下载验证码图片 默认下载100张
def download_codes(num=100):
    
    for i in range(num):
        response = requests.get("http://121.42.236.205/images/code.jpg")
        with open(down_path + str(i+1) + ".jpg","wb") as f:
            f.write(response.content)

# 统计像素数量
def get_counts(image):

    counts = {}
    for x in range(image.width):
        for y in range(image.height):
            pixel = image.getpixel((x, y))
            count = counts.get(pixel,0)
            count += 1
            counts[pixel] = count

    # 灰度值，对应的出现的次数
    return counts

# 二值化
def two_value(image, counts):
    img2val = {}

    for x in range(image.width):
        for y in range(image.height):
            # 获取， x, y 这一点的像素值， 这个值应该是一个0-255的灰度值
            pixel = image.getpixel((x, y))
            
            #背景设置为白色
            if counts.get(pixel) >= 45:
                img2val[(x, y)] = 1
            #前景设置为黑色
            else:
                img2val[(x, y)] = 0

    return img2val

# 降噪
def clear_noise(image, img2val):
    
    for x in range(image.width):
        for y in range(image.height):
            # 把当前像素点对应的二值化后的值取出
            value = img2val[(x, y)]
            count = 0
            # 左上角
            if img2val.get((x-1, y-1)) == value:
                count += 1
            # 正上
            if img2val.get((x, y-1)) == value:
                count += 1
            # 右上角
            if img2val.get((x+1, y-1)) == value:
                count += 1
            # 左边
            if img2val.get((x-1, y)) == value:
                count += 1
            # 右边
            if img2val.get((x+1, y)) == value:
                count += 1
            # 左下
            if img2val.get((x-1, y+1)) == value:
                count += 1
            # 正下
            if img2val.get((x, y+1)) == value:
                count += 1
            # 右下
            if img2val.get((x+1, y+1)) == value:
                count += 1

            if count <= 3:
                img2val[(x, y)] = 1

# 保存二值化后的图片
def save_img(size, img2val, img_name):
    # 创建一张二值化的图片
    image = Image.new("1", size)
    # 画笔
    draw = ImageDraw.Draw(image)

    for x in range(image.width):
        for y in range(image.height):
            draw.point((x, y), img2val[(x, y)])

    image.save(img_name)
    
# 分割图片
def cut_and_save_img(image, img2val, img_name):
    
    char_w = image.width // 4
    char_h = image.height

    # 解析当前文件名
    file_name = os.path.splitext(img_name)[0]  #文件名

    for i in range(4):
        # 创建一张图片
        char_img = Image.new("1", (char_w, char_h))

        # 创建画笔
        draw = ImageDraw.Draw(char_img)

        for x in range(char_w):
            for y in range(char_h):
                draw.point((x, y), img2val[(x + char_w*i, y)])

        # 保存文件
        char_img.save(cut_path + file_name + "_" + str(i+1) + ".jpg")


if __name__ == "__main__":

    #创建各种目录用于保存相关数据
    mkdirs()
    # 下载验证码图片
    download_codes()
    
    #对图片进行处理
    for img_name in os.listdir(down_path):
        # 打开一张图片
        image = Image.open(down_path + img_name)
        
        # 将图片转换成灰度值
        image = image.convert("L")

        # 统计图片中所有的像素数量
        counts = get_counts(image)
        
        # 将图片二值化
        img2val = two_value(image, counts)
        
        # 降噪
        clear_noise(image,img2val)
        clear_noise(image,img2val)
        clear_noise(image,img2val)
        
        # 保存降噪后的图片
        save_img((65,25),img2val,two_path + img_name)
        
        # 保存切割后的图片
        cut_and_save_img(image,img2val,img_name)
        
    # resize 把图片大小变成 20 * 25
    for img_name in os.listdir(cut_path):
        img = Image.open(cut_path + img_name)
        img_resize = img.resize((20, 25))
        img_resize.save(resize_path + img_name)
    
    # 加载模型
    model = joblib.load("code.model")
    
    # 使用训练好的模型进行测试
    x_list = []
    img_name = []
    for test_img in os.listdir(resize_path):
        img = Image.open(resize_path + test_img).convert("L")
        pixel = np.asarray(img).reshape(20 * 25)
        x_list.append(pixel)
        img_name.append(test_img)
        
    x_list = np.asarray(x_list)
    result = model.predict(x_list)
    
    # 输出每张切割图片的预测结果
    print("预测结果")
    for name,res in zip(img_name,result):
        print(name,":",res)
    
    
        


