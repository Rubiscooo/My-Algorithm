#include <bits/stdc++.h>

using namespace std;

#define SIZE 10

void Merge(int arr[],int low,int mid,int high)
{
    int left = low,right = mid,p = 0;

    int *ans = new int[high - low + 1];

    while( left < mid && right <= high)
    {
        if( arr[left] < arr[right] )
        {
            ans[p++] = arr[left++];
        }
        else
        {
            ans[p++] = arr[right++];
        }
    }
    while( left < mid ){  ans[p++] = arr[left++];}
    while( right <= high ){  ans[p++] = arr[right++];}
    
    for(int i = 0 , k = low ; k <= high ; )
    {
        arr[k++] = ans[i++];
    }

    delete[] ans;

}
void MSort(int arr[],int low,int high)
{
    int mid = (low + high) / 2;
    
    if( low == high )
    {
        return;
    }
    
    MSort(arr,low,mid);
    MSort(arr,mid+1,high);
    Merge(arr,low,mid+1,high);
}
int main()
{
    int arr[SIZE] = { 5,6,7,8,10,3,31,32,33,34 };
    MSort(arr,0,SIZE-1);
    for(int i = 0 ; i <= SIZE-1 ; i++)
       cout<<arr[i]<<endl;
}
