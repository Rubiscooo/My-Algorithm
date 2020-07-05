
#include <bits/stdc++.h>

using namespace std;


long exp_mod(long a,long n,long b)
{
    long t;
    if(n==0) return 1%b;
    if(n==1) return a%b;
    t=exp_mod(a,n/2,b);
    t=t*t%b;
    if((n&1)==1) t=t*a%b;
    return t;
}


int main()
{
   int a,n,m;
   while(cin>>a>>n>>m)
   cout<<exp_mod(a,n,m)<<endl;
}


