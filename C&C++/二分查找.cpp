#include <bits/stdc++.h>

using namespace std;

int size;

int BinarySearch(int array[],int item)
{
    int left = 0;
    int right = size-1;
    while( left <= right )
    {
        int mid = (left+right)/2;
        if( array[mid] == item )
        {
            return mid;
        }
        else if( array[mid] > item )
        {
            right = mid - 1;
        }
        else if( array[mid] < item )
        {
            left = mid + 1;
        }
    }
    return -1;
}
int main()
{
    cout<<"enter the size of array"<<endl;
    cin>>size;

    int* array = (int *)malloc( sizeof(int) * size );

    cout<<"enter "<<size<<" items of array"<<endl;
    for(int i = 0 ; i <= size - 1 ; i++)
    {
        cin>>array[i];
    }

    sort(array,array+size);

    cout<<"display sorted array"<<endl;
    for(int i = 0 ; i <= size - 1 ; i++)
    {
        cout<<array[i]<<" ";
    }
    cout<<endl;

    int item;
    int index;
    cout<<"find a item "<<endl;
    cin>>item;

    index = BinarySearch(array,item);

    if( index == -1 ) //查找的元素不存在
    {
        cout<<"item not exist"<<endl;
    }
    else
    {
        cout<<"the index of this item is "<<index<<endl;
    }

}
