#include <bits/stdc++.h>

using namespace std;

int Partition(int arr[],int low,int high)
{
   arr[0] = arr[low];
   while(low < high )
   {
     while( arr[high] >= arr[0] && high > low )
        high--;
    arr[low] = arr[high];
    while( arr[low] <= arr[0] && low < high )
        low++;
    arr[high] = arr[low];
   }
   arr[low] = arr[0];
   return low;
}

void QSort(int arr[],int low,int high)

{
    if(low < high)
    {
        int pivotal = Partition(arr,low,high);
        QSort(arr,low,pivotal-1);
        QSort(arr,pivotal+1,high);
    }
    else
        return;
}