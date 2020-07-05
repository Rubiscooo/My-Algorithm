from sklearn.cluster import KMeans
from sklearn.manifold import TSNE
import matplotlib.pyplot as plt
import numpy as np

plt.rcParams['font.sans-serif']=['SimHei']  # 用来正常显示中文标签  
plt.rcParams['axes.unicode_minus']=False  # 用来正常显示负号

dataset = open('E:\ALLLLLLLLLLLLLLLLLLL\python_workplace\py36\exp6 k-means\credit_card.csv','r')

X = []
data = []
#数据处理
dataset.readline()
for i in dataset.readlines():
    line = i.split("\n")
    line = line[0].split(",")
    dline = line[1:]
    for item in dline:
        data.append(int(item))
    X.append(data)
    data = []

X = np.array(X)
#设置类别数量为5进行聚类
kmeans = KMeans(n_clusters=5).fit(X)
#聚类中心
print(kmeans.cluster_centers_)
#统计每个类别的客户数
count = [0,0,0,0,0]
for i in kmeans.labels_:
    count[i] = count[i] + 1

xlabel = [0,1,2,3,4]
plt.bar(xlabel,count)
plt.xlabel('类别')
plt.ylabel('数量')
plt.title('每个类别客户数')
for a, b in zip(xlabel, count):
    plt.text(a, b + 0.05, '%.0f' % b, ha='center', va='bottom', fontsize=10)
plt.show()
#聚类结果图绘图点的个数
nump = 150
#原始数据和聚类中心一起降维至2维
ts = TSNE(n_components=2)
xx = np.concatenate((X[0:nump,],kmeans.cluster_centers_))

ts.fit_transform(xx)
x2d = ts.embedding_
#绘制聚类结果图
plt.scatter(x2d[0:nump,0],x2d[0:nump,1],c=kmeans.labels_[0:nump])
plt.scatter(x2d[nump:,0],x2d[nump:,1],marker='*',s=500,c=[0,1,2,3,4])
plt.show()
