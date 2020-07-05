#include <bits/stdc++.h>

using namespace std;

#define SIZE 10

template<class T>
void merge(T a[], int left, int mid, int right)
{
    T* tmp = new T[right - left + 1];

    int i = left, j = mid, k = 0;

    while(i < mid && j <= right)
    {
        if(a[i] < a[j])
            tmp[k++] = a[i++];
        else tmp[k++] = a[j++];
    }

    while(i < mid) tmp[k++] = a[i++];
    while(j<=right) tmp[k++] = a[j++];

    for(i = 0, k = left; k <= right;)
        a[k++] = tmp[i++];

    delete[] tmp;
}

template<class T>
void mergeSort(T a[], int left, int right)
{
    int mid = (left + right) / 2;

    if(left == right) return;

    mergeSort(a, left, mid);
    mergeSort(a, mid + 1, right);

    merge(a, left, mid + 1, right);

}

int main()
{
    int arr[SIZE] = { 5,6,7,8,10,        3,31,32,33,34 };
    mergeSort(arr,0,SIZE-1);
    for(int i = 0 ; i <= SIZE-1 ; i++)
       cout<<arr[i]<<endl;
}
