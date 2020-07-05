from efficient_apriori import apriori

dataset = open('E:\\ALLLLLLLLLLLLLLLLLLL\\python_workplace\\py36\\1\\Breakfast.csv','r')

data = []
tup = ()
n = 1
#数据处理
lines = dataset.readlines()
for i in range(1,len(lines)):
    strs = lines[i].rstrip('\n').split(",")
    if int(strs[0]) == n:
        tup = tup + (strs[1],)
        if i == len(lines)-1:
            data.append(tup)
    else:
        n = n+1
        data.append(tup)
        tup = ()
        tup = tup + (strs[1],) 
# 挖掘频繁项集和频繁规则
itemsets, rules = apriori(data, min_support=0.05, min_confidence=0.5)
print("itemsets:\n")
for key in itemsets.keys():
    for item in itemsets[key]:
        print(item,"freq : ",itemsets[key][item])
print("\nrules:\n")
for i in rules:
    print(i)
