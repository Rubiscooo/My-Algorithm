import requests

from lxml import etree

# https://ent.sina.com.cn/
# 使用xpath抓取这个页面中的所有的新闻标题，时间

def get_time(url):
    response = requests.get(url)
    
    #针对 https://ent.sina.com.cn/
    #该网页下的新闻时间包含在标签<span class="date">下
    if url.find("https://ent.sina.com.cn/")>=0 :
        response.encoding = "utf-8"
        html = etree.HTML(response.text)
        news_date = html.xpath("//span[@class='date']/text()")
        return news_date[0]
    
    #针对http://ent.sina.com.cn/
    #该网页下的新闻时间包含在标签<div class="pub-time">下 
    elif url.find("http://ent.sina.com.cn/")>=0 :
        response.encoding = "utf-8"
        html = etree.HTML(response.text)
        news_date = html.xpath("//div[@class='pub-time']/text()")
        return news_date[0]

    #针对 http://slide.ent.sina.com.cn/
    #该网页下的新闻时间包含在标签<div id="eData" style="display:none;">下
    #第一个<dl>标签的第四个<dd>标签内
    elif url.find("http://slide.ent.sina.com.cn/") >= 0:
        response.encoding = "gb2312"
        html = etree.HTML(response.text)
        news_date = html.xpath("//div[@id='eData']/dl[1]/dd[4]/text()")
        return news_date[0]
    
    #针对 https://video.sina.com.cn/
    #该网页下的新闻时间包含在标签<p class="from">下第一个<span>标签的<em>标签内
    elif url.find("https://video.sina.com.cn/")>=0:
        response.encoding = "utf-8"
        html = etree.HTML(response.text)
        news_date = html.xpath("//p[@class='from']/span[1]/em/text()")
        return news_date[0]
    
    return None

if __name__ == "__main__":

    url = "https://ent.sina.com.cn/"
    
    response = requests.get(url)

#    print(response.encoding)
#    encoding:ISO-8859-1
    
    #设置编码格式为UTF-8
    response.encoding = "UTF-8"
    
    html = etree.HTML(response.text)
    
    #在Network下第一个收到的文件ent.sina.com.cn内可以看到新闻都由标签<ul class="seo_data_list">包裹
    #该标签下所有的<a>标签包裹了新闻标题,其中的href属性是新闻的URL
    #构建路径表达式获取新闻标题及其URL
    titles = html.xpath("//ul[@class='seo_data_list']//a/text()")
    news_urls = html.xpath("//ul[@class='seo_data_list']//a/attribute::href")

    for i,title in enumerate(titles):
        print(i+1)
        print("新闻标题：" + title)
        print("新闻时间：" + get_time(news_urls[i]))
