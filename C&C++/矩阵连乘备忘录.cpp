#include <bits/stdc++.h>

using namespace std;

int *p; //用于存储矩阵的维度 第i个矩阵的行是 pi-1 列是 pi
int **m; //用于存储最少连乘次数 m[i][j]存储的是A[i:j]的最小连乘次数
int **s; //用于存储最优断开位置 s[i][j]存储的是A[i:j]的最优断开位置

int size = 7; //下标从1开始 样例一共6个矩阵数组大小就是7

int MatrixChain(int i,int j) //备忘录方法
{
    //m[i][j]>0说明A[i:j]这个子问题的最优值已经算出来了直接调用就行
    if( m[i][j] > 0 )
        return m[i][j];
    //长度是1的子问题的连乘次数是0
    if( i == j )
        return 0;
    //记录第一个断点的连乘次数及断点位置
    int u = MatrixChain(i,i) + MatrixChain(i+1,j) + p[i-1]*p[i]*p[j];
    s[i][j] = i;
    //遍历所有断点找当前子问题的最优值
    for(int k = i+1 ; k <= j-1 ; k++)
    {
        int t = MatrixChain(i,k) + MatrixChain(k+1,j) + p[i-1]*p[k]*p[j];
        if( t < u ) { u = t ; s[i][j] = k; }
    }
    //记录当前子问题的最优值并保存
    m[i][j] = u;
    //返回当前子问题的最优值
    return u;
}

void Print(int i,int j)
{
    if( i == j ) { cout<<"A["<<j<<"]"; return; }
    cout<<"(";
    Print(i,s[i][j]); Print(s[i][j]+1,j);
    cout<<")";
}

int main()
{
    //使用课本的样例
    p = (int *) (malloc(sizeof(int) * size) );
    p[0] = 30, p[1] = 35 , p[2] = 15; p[3] = 5;
    p[4] = 10, p[5] = 20 , p[6] = 25;
    //初始化数组
    m = (int **) (malloc(sizeof(int *) * size));
    s = (int **) (malloc(sizeof(int *) * size));
    for(int i = 0 ; i <= size - 1 ; i++)
    {
        m[i] = (int *)(malloc(sizeof(int) * size));
        s[i] = (int *)(malloc(sizeof(int) * size));
    }
    for(int i = 1 ; i <= 6 ; i++)
    {
        for(int j = 1 ; j <= 6 ; j++)
        {
            m[i][j] = 0;
            s[i][j] = 0;
        }
    }

    MatrixChain(1,size-1);

    cout<<"m[i][j] : "<<endl;
    for(int i = 1 ; i <= 6 ; i++)
    {
        for(int j = 1 ; j <= 6 ; j++)
        {
            printf("%-6d ",m[i][j]);
        }
        cout<<endl;
    }
    cout<<"s[i][j] : "<<endl;
    for(int i = 1 ; i <= 6 ; i++)
    {
        for(int j = 1 ; j <= 6 ; j++)
        {
            printf("%-6d ",s[i][j]);
        }
        cout<<endl;
    }
    cout<<"the best calculate order is : ";
    Print(1,size-1);
}
