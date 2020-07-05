import requests
from bs4 import BeautifulSoup as BS

# https://ent.sina.com.cn/
# 抓取这个页面中的所有的新闻标题，时间

#根据新闻的URL获取新闻时间
def get_time(url):
    response = requests.get(url)

    #针对 https://ent.sina.com.cn/
    #该网页下的新闻时间包含在标签<span class="date">下
    if url.find("https://ent.sina.com.cn/")>=0 :
        response.encoding = "utf-8"
        soup = BS(response.text, 'html.parser')
        #用Class选择器进行选择
        d = soup.select(".date")
        if len(d)>0:
            d = d[0].string

        return d

    #针对 http://slide.ent.sina.com.cn/
    #该网页下的新闻时间包含在标签<div id="eData" style="display:none;">下
    #第一个<dl>标签的第四个<dd>标签内
    elif url.find("http://slide.ent.sina.com.cn/") >= 0:
        response.encoding = "gb2312"
        soup = BS(response.text, 'html.parser')
        d = soup.select("#eData dl dd")[3]
        d = d.string

        return d
    
    #针对 https://video.sina.com.cn/
    #该网页下的新闻时间包含在标签<p class="from">下第一个<span>标签的<em>标签内
    elif url.find("https://video.sina.com.cn/")>=0:
        response.encoding = "utf-8"
        soup = BS(response.text, 'html.parser')
        d = soup.select(".from span em")[0].string

        return d

    return None

url = "https://ent.sina.com.cn/"

response = requests.get(url)

# 设置返回数据的编码格式
response.encoding = "utf-8"

soup = BS(response.text, 'html.parser')

#在Network下第一个收到的文件ent.sina.com.cn内可以看到新闻标题都由标签
#<ul class="seo_data_list">包裹
#使用方法选择器选择该标签
ul = soup.find(name="ul", attrs={"class": "seo_data_list"})

# 找到该ul下的所有的<a>标签
a_list = ul.find_all(name="a")

for i, a in enumerate(a_list):
    print(i+1)
    #新闻标题就是<a>标签的内容
    print("新闻标题：", a.get_text())
    news_url = a.attrs.get("href")
    print("新闻时间：", get_time(news_url))








