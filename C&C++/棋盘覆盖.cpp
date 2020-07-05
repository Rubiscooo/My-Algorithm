#include <bits/stdc++.h>
using namespace std;
//用一个二维数组当棋盘
int ** Board;
//记录填充过程
int tile = 1;
void ChessBoard(int fr,int fc,int gr,int gc,int size)
{
    if( size == 1 )
    {
        return;
    }
    int t = tile++;
    int s = size / 2;
    //特殊方格位于左上角的棋盘
    if( gr >= fr && gr <= fr + s - 1 && gc >= fc && gc <= fc + s - 1)
    {
        ChessBoard(fr,fc,gr,gc,s);
    }
    else
    {
        Board[fr + s - 1][fc + s - 1] = t;
        ChessBoard(fr,fc,fr + s - 1,fc + s - 1,s);
    }
    //特殊方格位于右上角的棋盘
    if( gr >= fr && gr <= fr + s - 1 && gc >= fc + s && gc <= fc + size - 1 )
    {
        ChessBoard(fr,fc + s,gr,gc,s);
    }
    else
    {
        Board[fr + s - 1][fc + s] = t;
        ChessBoard(fr,fc + s,fr + s - 1,fc + s,s);
    }
    //特殊方格位于左下角的棋盘
    if( gr >= fr + s && gr <= fr + size - 1 && gc >= fc && gc <= fc + s - 1 )
    {
        ChessBoard(fr + s,fc,gr,gc,s);
    }
    else
    {
        Board[fr + s][fc + s - 1] = t;
        ChessBoard(fr + s,fc,fr + s,fc + s - 1,s);
    }
    //特殊方格位于右下角的棋盘
    if( gr >= fr + s && gr <= fr + size - 1 && gc >= fc + s && gc <= fc + size - 1 )
    {
        ChessBoard(fr + s,fc + s,gr,gc,s);
    }
    else
    {
        Board[fr + s][fc + s] = t;
        ChessBoard(fr + s,fc + s,fr + s,fc + s,s);
    }
}
int main()
{
int size,k;
int gr,gc;
    //棋盘大小2^k*2^k
    cout<<"enter the order of board"<<endl;
    cin>>k;
    size = pow(2,k);
    cout<<"enter the row and column of grid"<<endl;
    cin>>gr>>gc;
    //初始化棋盘
    Board = (int **)malloc( sizeof(int *) * size );
    for(int x = 0 ; x <= size - 1 ; x++)
    {
        Board[x] = (int *)malloc( sizeof(int) * size );
        memset(Board[x],0,sizeof(int) * size);
    }
    //标记特殊方格
    Board[gr][gc] = 0;
    ChessBoard(0,0,gr,gc,size);
    //输出填充棋盘的结果
    for(int x = 0 ; x <= size - 1 ; x++)
    {
        for( int y = 0 ; y <= size - 1 ; y++)
        {
            if( Board[x][y] < 10 )
                cout<<" "<<Board[x][y]<<" ";
            else if( Board[x][y] < 100 )
                cout<<Board[x][y]<<" ";
        }
        cout<<endl;
    }
}
