from sklearn import tree
import numpy as np
import graphviz
#数据和标签
data=[[1,0,0,1],
      [1,0,0,2],
      [1,1,0,2],
      [1,1,1,1],
      [1,0,0,1],
      [2,0,0,1],
      [2,0,0,2],
      [2,1,1,2],
      [2,0,1,3],
      [2,0,1,3],
      [3,0,1,3],
      [3,0,1,2],
      [3,1,0,2],
      [3,1,0,3],
      [3,0,0,1]]

target = [0,0,1,1,0,0,0,1,1,1,1,1,1,1,0]

data = np.array(data)
target = np.array(target)
#训练决策树
clf = tree.DecisionTreeClassifier(criterion='entropy')
mod = clf.fit(data,target)

fname = ['Age','Has-job','Own_house','Credit_rating']
cname = ['No','Yes']
#决策树可视化
dot_data=tree.export_graphviz(mod,out_file=None,
                              feature_names=fname,
                              class_names=cname,
                              filled=True,rounded=True,
                              special_characters=True,
                              precision=4)

graph=graphviz.Source(dot_data,encoding='utf-8')
graph.view(filename='loan') #在这里可为输出文件命名 会输出 .gv 和 .pdf 两个文件
