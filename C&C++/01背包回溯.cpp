#include <bits/stdc++.h>

using namespace std;

int n = 4;//物品个数
int bestp = -1;//最优背包价值
int bestx[4]; //保存最优解
int no[] = {1,2,3,4};   //商品编号
double c = 9;//背包容量
double p[] = {9,10,7,4};//物品价值
double w[] = {3,5,2,1}; //物品重量
double pw[4];           //单位重量价值
//按单位价值从大到小排序
void sortItem(int no[],double p[],double w[],double pw[])
{
    for(int t = 1 ; t <= n-1 ; t++)
    {
        for(int i = 0 ; i <= n-2 ; i++)
        {
            if( pw[i] < pw[i+1] )
            {
                swap( no[i],no[i+1] );
                swap( p[i] ,p[i+1] );
                swap( w[i] ,w[i+1] );
                swap( pw[i],pw[i+1] );
            }
        }
    }
}
//计算上界
double bound(int i,double cp,double cw)
{
    double bp = cp;
    while( i <= n-1 )
    {
        if( cw + w[i] <= c)//判断能不能把整个物品全部装入
        {
            bp += p[i];
            cw += w[i];
        }
        else//不能把整个物品全部装入只能装一部分
        {
            bp += ((c-cw)/w[i])*p[i];
            break;
        }
        i++;
    }
    return bp;
}
//深度优先搜索解空间树
//i:当前要考虑的物品
//cp:当前背包价值
//cw:当前背包重量
//x:当前可行解
void dfs(int i,double cp,double cw,int x[])
{
    if( i >= n) //到达了叶子结点就得到了一个可行解
    {
        if( cp > bestp ) //当前背包价值大于最优价值
        {                //保存最优值和最优解
            bestp = cp;
            for(int i = 0 ; i <= n-1 ; i++)
            {
                bestx[i] = x[i];
            }
        }
        return;
    }
    //只要不超过背包容量就搜索左子树
    if( cw + w[i] <= c )
    {
        x[i] = 1;
        cp += p[i];
        cw += w[i];
        dfs(i+1,cp,cw,x);
        cp -= p[i];//回溯的时候把装入的商品在拿出来
        cw -= w[i];//相应的价值和重量要减去
    }
    //上界大于最优值就搜索右子树
    if( bound(i+1,cp,cw) > bestp )
    {
        x[i] = 0;
        dfs(i+1,cp,cw,x);
    }
}

int main()
{
    //计算单位重量价值
    for(int i = 0 ; i <= n-1 ; i++)
    {
        pw[i] = p[i]/w[i];
    }
    sortItem(no,p,w,pw);
    int x[4] = {0};
    dfs(0,0.0,0.0,x);
    cout<<"best value : "<<bestp<<endl;
    cout<<"best solve : ";
    for(int i = 0 ; i <= n-1 ; i++)
    {
        cout<<bestx[i]<<" ";
    }
    //排序过后商品的顺序和原来不一样输出的是一开始的编号
    cout<<endl<<"best item selection : ";
    for(int i = 0 ; i <= n-1 ; i++)
    {
        if( bestx[i] == 1 )
            cout<<no[i]<<" ";
    }
}
