#include <bits/stdc++.h>

using namespace std;

int n = 4; //物品个数
int c = 8; //背包容量
int w[] = {-1,1,4,2,3}; //每个物品的重量 下标从1开始用
int v[] = {-1,2,1,4,3}; //每个物品的价值 下标从1开始用
int **m;
//表格m[i][j]储存的是考虑(n,n-1,..,i+1,i)这些物品,背包容量为j时的最优值
//最优值是m[1][c]
//从第n个物品开始依次向前装，装的顺序为:(n,n-1,…,i+1,i,i-1, …,2,1)
void Knapsack(int n,int c,int *w,int *v,int **m)
{
    for(int j = 0 ; j <= c ; j++)//         {  0   , j < w[n]
    {                            // m[n][j]=|
        if( j < w[n] )           //         { v[n] , j >= w[n]
            m[n][j] = 0;
        else
            m[n][j] = v[n];
    }

    for(int i = n-1 ; i >= 1 ; i--)
    {
        for(int j = 0 ; j <= c ; j++ )
        {
            if( j < w[i] )
            {                       //        { m[i+1][j]                                  j < w[i]
                m[i][j] = m[i+1][j];//m[i][j]=|
            }                       //        { max{ m[i+1][j] , v[i] + m[i+1][j-w[i]] }   j >= w[i]
            else
            {
                m[i][j] = max(m[i+1][j] , v[i] + m[i+1][j-w[i]] );
            }
        }
    }

}
//根据表格回溯构造最优解
void PrintBest(int n,int c,int *w,int *v,int **m)
{
    int i = 1;     //回溯时的行下标
    int j = c;     //回溯时的列下标
    int best[n+1]; //存储最优解

    while( i <= n-1 )
    {
        if( m[i][j] == m[i+1][j] ) //第i个物品没有装进来xi = 0
        {
            best[i++] = 0;
        }
        else  //第i个物品装进来了xi = 1
        {
            j -= w[i]; //注意回溯时要修改i,j的值
            best[i++] = 1;
        }
    }
    //回溯到 i = n 时的判断
    if( m[i][j] )
        best[i] = 1;
    else
        best[i] = 0;
    //输出最优解
    cout<<endl<<"optimal solve : "<<endl<<endl;
    for(int i = 1 ; i <= n ; i++)
    {
        cout<<"x"<<i<<"="<<best[i]<<endl;
    }

}

int main()
{
    //初始化表格
    int **m = (int **)( malloc(sizeof(int *) * n+1) );
    for(int i = 0 ; i <= n ; i++)
    {
        m[i] = (int *)(malloc(sizeof(int) * c+1) );
    }
    //清零表格
    for( int i = 0 ; i <= n ; i++ )
    {
        for(int j = 0 ; j <= c ; j++ )
        {
            m[i][j] = 0;
        }
    }
    Knapsack(n,c,w,v,m);
    //输出表格
    cout<<"m[i][j] : "<<endl;
    for( int i = 4 ; i >= 1  ; i-- )
    {
        for(int j = 0 ; j <= c ; j++ )
        {
            cout<<m[i][j]<<" ";
        }
        cout<<endl;
    }
    //输出最优值和最优解
    cout<<endl<<"optimal value = "<<m[1][c]<<endl;
    PrintBest(n,c,w,v,m);
}
