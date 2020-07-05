from pyspark.sql import SparkSession
from pyspark.mllib.fpm import FPGrowth
ss = SparkSession.builder \
        .appName("test_fp") \
        .config("spark.executor.memory", "32G") \
        .config("spark.driver.memory", "32G") \
        .config("spark.python.worker.memory", "32G") \
        .config("spark.default.parallelism", "4") \
        .config("spark.executor.cores", "8") \
        .config("spark.sql.shuffle.partitions", "500") \
        .config("spark.sql.crossJoin.enabled", "true")\
        .config("spark.sql.broadcastTimeout","36000") \
        .enableHiveSupport() \
        .getOrCreate()
dataset=open('E:\\ALLLLLLLLLLLLLLLLLLL\\python_workplace\\py36\\1\\Breakfast.csv','r')
data = []
l = []
n = 1
lines = dataset.readlines()
for i in range(1,len(lines)):
    strs = lines[i].rstrip('\n').split(",")
    if int(strs[0]) == n:
        l.append(strs[1])
        if i == len(lines)-1:
            data.append(l)
    else:
        n = n+1
        data.append(l)
        l = []
        l.append(strs[1])  
rdd = ss.sparkContext.parallelize(data, 2)
model = FPGrowth.train(rdd, 0.05, 2)
res = model.freqItemsets().collect()
for i in res:
print(i)
