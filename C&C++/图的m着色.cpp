#include <bits/stdc++.h>

using namespace std;

int n = 4;//图的顶点个数
int m = 3;//颜色个数
int sum = 0;//着色方案个数
int adj[4][4] = { {0,1,1,1},{1,0,1,1},{1,1,0,0},{1,1,0,0} }; //邻接矩阵
int x[4] = {0}; //着色方案
//输出一个可行的涂色方案
void print(int x[])
{
    for(int i = 0 ; i <= n-1 ; i++)
    {
        cout<<x[i]<<" ";
    }
    cout<<endl;
}
//判断当前结点的颜色是否和与之相邻的且已涂色的结点颜色相同
bool OK(int x[],int i)
{   //这里给出的u的范围是i-1 也就是仅和已涂色的结点颜色进行比较
    for(int u = 0 ; u <= i-1 ; u++)
    {
        if( adj[i][u] == 1 && x[i] == x[u] )
            return false;
    }
    return true;
}

void dfs(int i) //i是当前要涂色的结点
{
    if( i >= n ) //输出结果
    {
        cout<<"solve "<<++sum<<" : "<<endl;
        print(x);
    }
    else //深度优先搜索所有结点
    {
        for(int t = 1 ; t <= m ; t++)
        {
            x[i] = t;
            if( OK(x,i) ) //满足约束条件才向下继续搜索
            {
                dfs(i+1);
            }
            //由于是仅和已涂色的结点颜色进行比较下一语句可以去掉
            //否则需要将结点回复到未涂色状态才能保证算法的正确性
            //x[i] = 0;
        }
    }
}

int main()
{
    dfs(0);
}
