#include <bits/stdc++.h>

using namespace std;

int main()
{
    int n; // 汽车加满油后可行驶n km
    int k; // 中间有k个加油站
    cin>>n>>k;
    int arr[k+1]; // 一共k+2个加油站,k+1个距离
    for(int i = 0 ; i <= k ; i++)
    {
        cin>>arr[i]; //输入相邻两个加油站的距离
    }
    int left = n; // 当前可行驶距离
    int ans = 0;  // 最少加油次数
    int pos = 0;  // 记录当前位置
    while( pos < k+1 )
    {
        if( arr[pos] > n )//加油站间距离大于可行使的最大距离
        {                 //此时无解
            cout<<"No Solution"<<endl;
            return 0;
        }
        else if( left > arr[pos]) //当前可行使距离大于加油站间距离
        {
            left -= arr[pos];     //当前可行使距离减少
            pos++;                //向前行驶一个位置
        }
        else if( left < arr[pos] )//当前可行使距离小于加油站间距离
        {                         //需要停车加油
            ans++;                //加油次数增加1
            left = n;             //当前可行使距离变为n
            //cout<<pos<<" ";     //输出需要加油的位置
        }
    }
    cout<<ans<<endl;
}
/*
test case 1
7 7
1 2 3 4 5 1 6 6
4
test case 2
1 2
9 9 9
No Solution
*/
