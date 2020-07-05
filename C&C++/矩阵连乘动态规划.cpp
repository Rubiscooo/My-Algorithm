#include <bits/stdc++.h>

using namespace std;

int *p; //用于存储矩阵的维度 第i个矩阵的行是 pi-1 列是 pi
int **m; //用于存储最少连乘次数 m[i][j]存储的是A[i:j]的最小连乘次数
int **s; //用于存储最优断开位置 s[i][j]存储的是A[i:j]的最优断开位置

int size = 7; //下标从1开始 样例一共6个矩阵数组大小就是7

void MatrixChain(int *p,int **m,int **s,int size) //动态规划
{
    for(int k = 2 ; k <= size-1 ; k++ )//k是子问题的长度先算小的子问题自底向上算大的子问题
    {
        for(int i = 1 ; i <= size - 1 - k + 1  ; i++ ) //每个子问题的起点i
        {
           int j = i + k - 1; //计算起点为i长度为k的子问题的终点j
           m[i][j] = m[i][i] + m[i+1][j] + p[i-1]*p[i]*p[j];
           s[i][j] = i; //先计算i为断点的连乘次数方便之后遍历所有断点找最小值
           for(int k = i+1 ; k <= j-1 ; k++) //遍历所有断点寻找最优值
           {
               int u = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
               if( u < m[i][j] ) { m[i][j] = u ; s[i][j] = k; }
           }
        }
    }
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
    MatrixChain(p,m,s,size);
    cout<<"m[i][j] : "<<endl;
    for(int i = 1 ; i <= 6 ; i++)
    {
        for(int j = 1 ; j <= 6 ; j++)
        {
            printf("%-6d ",m[i][j]);
        }
        cout<<endl;
    }
    cout<<endl<<"s[i][j] : "<<endl;
    for(int i = 1 ; i <= 6 ; i++)
    {
        for(int j = 1 ; j <= 6 ; j++)
        {
            printf("%-6d ",s[i][j]);
        }
        cout<<endl;
    }
    cout<<endl<<"the best calculate order is : ";
    Print(1,size-1);
    cout<<endl;
}
