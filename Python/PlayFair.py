import sys

#添加的字符
add = 'x'
#需要去掉的字符
rem = 'j'
#字母a-z的列表 去掉一个最不常用的字母rem
chars = [ chr(x) for x in range( ord('a'), ord('z') + 1) ]
chars.remove(rem)

#输出密码表
def print_code(code):
    for i in range(5):
        print(code[i],"\n")
        
#获取密码表中字符的下标
def locate_index(c):
    for x in range(5):
        for y in range(5):
            if code[x][y] == c:
                return [x,y]
            
#密钥、明文、密文合法性判断 要求密钥、明文、密文的所有字符都在密码表中存在
def judge(msg):
    msg = msg.lower()
    for c in msg:
        if chars.count(c) == 0 :
            return False
    return True

#用密钥生成密码表
def create_code(key):
    #用空字符初始化密码表
    code = [['' for i in range(5)] for j in range(5) ]
    
    l = len(key)
    #先用密钥填充密码表 横向替换
    for i in range(0,l):
        x = i // 5
        y = i % 5
        code[x][y] = key[i].lower()
     
    inds = l    
    #再用剩余字母填充密码表    
    for c in chars:
        if not c in key.lower() :
            x = inds // 5
            y = inds % 5
            code[x][y] = c
            inds += 1
        
    return code

#加密
def encrypt(msg):
    
    msg = msg.lower()
    
    #保存明文
    m = []
    
    #明文长度不为2的倍数就在最后添加一个字符
    if len(msg) % 2 == 1:
        msg = msg + add
    
    for i in range(0,len(msg)-1,2):
        m.append(msg[i])
        #明文两两分组 若字母都相同就在其间添加一个字符
        if  msg[i] == msg[i+1]:
            m.append(add)
            
        m.append(msg[i+1])
                
    #明文长度不为2的倍数就在最后添加一个字符
    if len(m) % 2 == 1:
        m.append(add)

    print("处理后的明文 : ",''.join(m))

    #密文
    c = []
    
    for i in range(0,len(m)-1,2):
        
        inds1 = locate_index(m[i])
        inds2 = locate_index(m[i+1])
        
        #同行
        if inds1[0] == inds2[0]:
            c.append( code[ inds1[0] ][ (inds1[1] + 1 ) % 5] )
            c.append( code[ inds2[0] ][ (inds2[1] + 1 ) % 5] )
        #同列
        elif inds1[1] == inds2[1]:
            c.append( code[ (inds1[0] + 1) % 5 ][ inds1[1] ] )
            c.append( code[ (inds2[0] + 1) % 5 ][ inds2[1] ] )
        #不同行不同列
        else:
            c.append( code[ inds1[0] ][ inds2[1] ] )
            c.append( code[ inds2[0] ][ inds1[1] ] )
    
    print("加密后的密文 : ",''.join(c))
    
    return ''.join(c)

#解密
def decrypt(c):
    
    c = c.lower()
    
    m = []
    
    for i in range(0,len(c)-1,2):
    
        inds1 = locate_index(c[i])
        inds2 = locate_index(c[i+1])
        
        #同行
        if inds1[0] == inds2[0]:
            m.append( code[ inds1[0] ][ (inds1[1] - 1 ) % 5] )
            m.append( code[ inds2[0] ][ (inds2[1] - 1 ) % 5] )
        #同列
        elif inds1[1] == inds2[1]:
            m.append( code[ (inds1[0] - 1) % 5 ][ inds1[1] ] )
            m.append( code[ (inds2[0] - 1) % 5 ][ inds2[1] ] )
        #不同行不同列
        else:
            m.append( code[ inds1[0] ][ inds2[1] ] )
            m.append( code[ inds2[0] ][ inds1[1] ] )

    print("解密后的明文 : ",''.join(m))
    
    return ''.join(m)
    
if __name__ == "__main__":
    #key是密钥    
    #key = input("输入密钥:")
    key = 'crazydog'
    
    if not judge(key):
        print("密钥不合法！")
        sys.exit(0)
    else:
         code = create_code(key)
         print("密码表 : ")
         print_code(code)
     
    #msg是明文
    #msg = input("输入明文:")
    msg = "wherethereislifethereishope"
    
    print("明文 : ",msg)
    
    if not judge(msg):
        print("明文不合法！")
        sys.exit(0)
    else :
        c = encrypt(msg)
    
    #chi是密文
    #chi = "GNTLTONHOEAFCP"
    #chi = input("输入密文:")
    chi = c
    
    if not judge(chi):
        print("密文不合法！")
        sys.exit(0)
    else:
        m = decrypt(chi)


