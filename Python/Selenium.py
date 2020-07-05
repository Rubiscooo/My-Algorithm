import time
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
'''
练习：
使用Selenium 调用谷歌浏览器
在百度下搜索内容，并将结果，获取打印出来
分页打印

'''

browser = webdriver.Chrome()

browser.get("https://www.baidu.com")

# 找到输入框并进行搜索
kw = browser.find_element_by_id("kw")

kw.send_keys("中享思途")

kw.send_keys(Keys.ENTER)

time.sleep(3)

#打印每页所有搜索结果
t = 0
while True:
    t += 1
    print("------------第",t,"页的搜索结果------------")
    # 搜索结果被标签<div class="result c-container ">标签包裹
    contents = browser.find_elements_by_class_name("result")
    
    # 打印搜索结果
    for i,content in enumerate(contents):
        print("第",i+1,"条:")
        print(content.text)
        
    # 当前页打印完毕后打印下一页的搜索结果
    # 上一页和下一页按钮被包裹在标签<a class="n">标签
    # 页面代码中存在两个class="n"的<a>标签，按照顺序分别是上一页和下一页
    # 选取下一页按钮并点击
    nextbtn = browser.find_elements_by_class_name("n")
    
    #判断是不是到了最后一页 最后一页只有一个上一页按钮
    if t > 1 and len(nextbtn) == 1:
        break

    nextbtn[-1].click()
    
    time.sleep(1)
    

time.sleep(3)
# 关闭浏览器
browser.close()

print("爬取完毕，共爬取",t,"页的搜索结果.")
