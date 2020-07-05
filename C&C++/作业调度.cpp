#include <bits/stdc++.h>
#include <algorithm>
using namespace std;


int n = 4;
int a[] = {3,4,8,10}; //所有作业在机器M1上加工所需时间
int b[] = {6,2,9,15}; //所有作业在机器M2上加工所需时间
int *c = (int *)malloc( sizeof(int) * n );

class Jobtype
{
public:
    int key;    // key保存a[i]和b[i]二者较小的值
    int index;  // index保存作业号i
    bool job;   // 将满足条件a[i]<b[i]的放入N1集合的作业标记为true
};

void Sort(Jobtype *d,int p,int q)
{
    int n = q - p + 1;
    for(int i = 1 ; i <= n-1 ; i++ )
    {
        for(int j = 0 ; j <= n-2 ; j++)
        {
            if( d[j].key > d[j+1].key )
            {
                swap(d[j].key,d[j+1].key);
                swap(d[j].index,d[j+1].index);
                swap(d[j].job,d[j+1].job);
            }
        }
    }
}

int FlowShop(int n, int a[], int b[], int c[])
{
    Jobtype *d = new Jobtype[n];

    for(int i=0; i<n; i++)
    {
        d[i].key = a[i]>b[i]? b[i]:a[i]; //分别取b[i]和a[i]值较小的作为关键字
        d[i].job = a[i]<=b[i];           //将满足a[i]<b[i]的放入N1集合的作业i标记为true
        d[i].index = i;                  //将当前作业号i赋值给index
    }

    Sort(d,0,n);//对数组d按关键字key升序进行排序

    int j = 0,  k = n-1; //指向数组c的两个指针,j指向最前面，k指向最后面

    for(int i=0; i<n; i++)
    {
        if(d[i].job)
          c[j++] = d[i].index; //将排过序的数组d，取N1中作业号,放到数组c的前面
        else
          c[k--] = d[i].index;//将d中属于N2的作业号, 放到数组c的后面，从而实现N1的非减序排序，N2的非增序排序
    }

    j = a[c[0]];  // p
    k = j+b[c[0]]; // q

    for(int i=1; i<n; i++)
    {
        j += a[c[i]]; //M1在执行c[i]作业的同时，M2在执行c[i-1]号作业，最短执行时间取决于M1与M2谁后执行完
        k = j<k? k+b[c[i]] : j+b[c[i]]; //计算最优加工时间
    }

    //输出最优调度顺序
    cout<<"Sequence of work : ";
    for(int i = 0 ; i < n ; i++ )
    {
        cout<<1+c[i]<<" ";
    }
    cout<<endl;

    delete d;

    return k;

}
int main()
{
    cout<<"Minimal time : "<<FlowShop(4,a,b,c);
}
