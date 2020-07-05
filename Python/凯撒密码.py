
class Caesar:
    k = 3
    letters = [ chr(x) for x in range( ord('a'), ord('z') + 1) ]
    
    def decrypt(c):
        m = []
        
        for char in c.lower():
            m.append( Caesar.letters[(Caesar.letters.index(char) - Caesar.k) % len(Caesar.letters) ]  )
        
        return ''.join(m)

    def encrypt(m):
        c = []
        
        for char in m.lower():
            c.append( Caesar.letters[(Caesar.letters.index(char) + Caesar.k) % len(Caesar.letters) ]  )
        
        return ''.join(c)


c = 'mldrbxnhsx'
m = 'jiaoyukepu'

print(Caesar.encrypt(m))
print(Caesar.decrypt(c))