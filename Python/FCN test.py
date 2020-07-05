#import os
#
## 写之前，先检验文件是否存在，存在就删掉
#if os.path.exists("log.txt"):
#    os.remove("log.txt")
# 
## 以写的方式打开文件，如果文件不存在，就会自动创建
#log = open("log.txt", 'w')
#
#a = 10086
#s = "loggint"
#db = 8.6543
#
#form = "%06d ---- %s ---- %.6f\n"%(a,s,db)
#
#log.write(form)
#log.close()
