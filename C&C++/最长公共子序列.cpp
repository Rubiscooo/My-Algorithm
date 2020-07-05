#include <bits/stdc++.h>
using namespace std;

char x[] = {' ','A','B','C','B','D','A','B'}; //第一个序列 下标从1开始用
char y[] = {' ','B','D','C','A','B','A'};     //第二个序列 所以第一个位置的元素用空字符代替
int m = 7; //第一个序列的长度
int n = 6; //第二个序列的长度
int  **c; //c[i][j]用于存储序列x[i],y[j]的最长公共子序列长度
int  **b; //b[i][j]记录c[i][j]的值是由哪个子问题得到的

void LCSLength(int m,int n,char *x,char *y,int **b,int **c )
{
    //自下向上先算小的子问题的解最终由子问题的解构造出整个问题的解
    for(int i = 1 ; i <= m ; i++)
    {
        for(int j = 1 ; j <= n ; j++ )
        {
            if( x[i] == y[j] ) //第一种情况即zk = xi = yj
            {
                c[i][j] = c[i-1][j-1] + 1;
                b[i][j] = 1;
            }
            else if( c[i-1][j] >= c[i][j-1] )
            {
                c[i][j] = c[i-1][j];
                b[i][j] = 2;
            }
            else
            {
                c[i][j] = c[i][j-1];
                b[i][j] = 3;
            }
        }
    }
}
//输出最长公共子序列
void PrintLCS(int i,int j,char *x,int **b)
{
    if( i == 0 || j == 0 )
    {
        return;
    }
    else if( b[i][j] == 1 )
    {
        PrintLCS(i-1,j-1,x,b);
        cout<<x[i]<<" ";
    }
    else if( b[i][j] == 2 )
    {
        PrintLCS(i-1,j,x,b);
    }
        else if( b[i][j] == 3 )
    {
        PrintLCS(i,j-1,x,b);
    }

}
//输出最长公共子序列中每个元素在x中的下标
void PrintLCSindex(int i,int j,char *x,int **b)
{
    if( i == 0 || j == 0 )
    {
        return;
    }
    else if( b[i][j] == 1 )
    {
        PrintLCSindex(i-1,j-1,x,b);
        cout<<i<<" ";
    }
    else if( b[i][j] == 2 )
    {
        PrintLCSindex(i-1,j,x,b);
    }
        else if( b[i][j] == 3 )
    {
        PrintLCSindex(i,j-1,x,b);
    }

}

int main()
{
    c = (int **)malloc( sizeof(int *) * (m+1) );
    b = (int **)malloc( sizeof(int *) * (m+1) );
    for(int i = 0 ; i <= m ; i++)
    {
        c[i] = (int *)malloc( sizeof(int) * (n+1) );
        b[i] = (int *)malloc( sizeof(int) * (n+1) );
    }
    //初始化数组c[i][j],b[i][j]
    for(int i = 0 ; i <= m ; i++)
    {
        for(int j = 0 ; j <= n ; j++)
        {
            c[i][j] = 0;
            b[i][j] = 0;
        }
    }
    LCSLength(m,n,x,y,b,c);
    cout<<"the length of longest subsequence is : "<<c[m][n]<<endl;
    //输出c[i][j]
    cout<<endl<<"c[i][j] : "<<endl;
    for(int i = 0 ; i <= m ; i++)
    {
        for(int j = 0 ; j <= n ; j++)
        {
            cout<<c[i][j]<<" ";
        }
        cout<<endl;
    }
    //输出b[i][j]
    cout<<endl<<"b[i][j] : "<<endl;
    for(int i = 0 ; i <= m ; i++)
    {
        for(int j = 0 ; j <= n ; j++)
        {
            cout<<b[i][j]<<" ";
        }
        cout<<endl;
    }
    cout<<endl<<"The longest subsequence is ";
    PrintLCS(m,n,x,b);
    cout<<endl<<endl<<"The index of subsequence in x is ";
    PrintLCSindex(m,n,x,b);
    cout<<endl;
}

