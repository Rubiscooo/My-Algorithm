
import re

from urllib.request import urlopen

html = urlopen("http://www.chinacodes.com.cn/exercises/gotoExercises.do").read().decode("utf-8")

line = html

pattern = re.compile("<div class=\"des\">[\\s]+<p>(.+)</p>[\\s]+<ul>[\\s]+<li>.+(A\..+)</li>[\\s]+<li>.+(B\..+)</li>[\\s]+<li>.+(C\..+)</li>[\\s]+<li>.+(D\..+)</li>[\\s]+</ul>[\\s]+.+(value=\".+\")[/][>][\\s]+</div>")

res = pattern.findall(line)

print("------—°‘ÒÃ‚------")
for i in res:
    print(i)

pattern = re.compile("<div class=\"des_2\">[\\s]+<p>(.+)</p><br/>[\\s]+.+[\\s]+.+[\\s]+.+(value=\".+\")[/][>]")

res = pattern.findall(line)

print("-----≈–∂œÃ‚-------")
for i in res:
    print(i)

