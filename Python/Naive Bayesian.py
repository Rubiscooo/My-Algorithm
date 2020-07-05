import pandas as pd
import re
import jieba
from wordcloud import WordCloud
from matplotlib import pyplot as plt
from sklearn.feature_extraction.text import CountVectorizer,TfidfTransformer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB

#注意编码问题 可以用记事本打开看右下角是什么编码 这里是ANSI即扩展的ASCII
#https://zhuanlan.zhihu.com/p/103310024
#默认header=0即第一行为标题行 本实验数据第一行是标题行 header在这里可省略用默认的
#https://www.cnblogs.com/HongjianChen/p/8872914.html
#数据中第一列作为索引列而不是数据列 索引从1开始 即第一行数据的下标是1
data = pd.read_csv('messages.csv',header=0,index_col=0,encoding='ANSI')
#print(type(data))
#<class 'pandas.core.frame.DataFrame'>
#print(data.shape)
#(20000, 2)
#展示前五行数据
# result    txt
#print(data[0:5])
#观察数据分布
#print(data['result'].value_counts())
#0    18073
#1     1927
data_new = data
#去除重复短信 对txt那一列进行操作 操作之后的返回结果只有一列
data_dup = data_new['txt'].drop_duplicates()
#print(data_dup.shape)
#(19984,) 说明去除了16条重复短信
#去除xxx序列
data_qumin = data_dup.apply(lambda x:re.sub('x','',x))
#验证
#print(data_qumin[0:5])
#print(data_qumin[23])

#对文本分词
#data_cut=data_qumin.apply(lambda x:','.join(jieba.cut(x)))
data_cut=data_qumin.apply(lambda x:jieba.cut(x))
#返回结果是一个迭代器 具体内容在下面  使用join可以显示内容
#print(data_cut[0:5])

#读取停用词字典
#编码显示的是带 BOM 的 UTF-8
stopWords=pd.read_csv('stopwords.txt',encoding='utf-8', engine='python',header=None)
#print(stopWords.shape)
#(1130, 1)
#停用词字典和自定义停用词拼接 日在字典里没有 添加一个
stopWords=list(stopWords.iloc[:,0])+['日']
#print(len(stopWords))
#1131
#去除停用词
data_after_stop=data_cut.apply(lambda x:[i for i in x if i not in stopWords] )
# 从data_new 选中index和resul两列 其他的data仅保存了txt那一列的内容因为['txt']
labels=data_new.loc[data_after_stop.index,'result']
adata=data_after_stop.apply(lambda x:' '.join(x))
#print(adata[0:5])
#print(labels[0:5])
#print(data_after_stop[0:5])

#绘制词汇云图
labels==0 绘制正常短信词云图  labels==1 绘制垃圾短信词云图
labels是刚才那个labels 满足条件才会被选中 就可以区分正常短信和垃圾短信
word_fre={}
for i in data_after_stop[labels==0]:
    for j in i:
        if j not in word_fre.keys():
            word_fre[j]=1
        else:
            word_fre[j]+=1
#背景图片
mask=plt.imread('background.jpg')
wc=WordCloud(mask=mask,background_color='white',font_path=r'C:\Windows\Fonts\simhei.ttf')
wc.fit_words(word_fre)
plt.imshow(wc)

#将分词之后的数据拆分为训练集和测试集 80%为训练集 20%为测试集
data_tr,data_te,labels_tr,labels_te=train_test_split(adata,labels,test_size=0.2)

#sklearn中的CountVectorizer 可将词语转化为词频矩阵 
#矩阵元素a[i][j] 表示j词在i类文本下的词频
countVectorizer = CountVectorizer()
#sklearn中的TfidfTransformer 会统计每个词语的tf-idf权值
#元素a[i][j]表示j词在i类文本中的tf-idf权重
tf_idf_transformer = TfidfTransformer()
#计算训练集的词频矩阵
data_tr = countVectorizer.fit_transform(data_tr)
#计算训练集的tf-idf
X_tr = tf_idf_transformer.fit_transform(data_tr.toarray())
#获取训练集的tf-idf矩阵
X_tr = X_tr.toarray()

#获取测试集的词频矩阵 仅计算测试集中出现的词的词频
data_te=CountVectorizer(vocabulary=countVectorizer.vocabulary_).fit_transform(data_te)
#获取测试集的tf-idf矩阵 toarray()是转成数组
X_te=TfidfTransformer().fit_transform(data_te.toarray()).toarray()
#data_te,data_tr

model=GaussianNB()
#训练模型
model.fit(X_tr,labels_tr)
#使用测试集测试 获取得分
print(model.score(X_te,labels_te))
