//库函数头文件包含
#include<stdio.h>
#include<malloc.h>
#include<stdlib.h>

#include <bits/stdc++.h>

using namespace std;

//函数状态码定义
#define TRUE        1
#define FALSE       0
#define OK          1
#define ERROR       0
#define INFEASIBLE -1
#define OVERFLOW   -2
#define MAXSIZE    100
#define Maxnum 101
#define INF 10086
#define visited TRUE

typedef int  Status;
typedef int  ElemType;

typedef char VexType;
//数组都是从1开始
typedef struct AdjMatrix
{
    int degree = 0;
    int weight = INF;
} AdjMatrix;

typedef struct MatrixGraph  //有向图
{
    int numOfVertex;
    int numOfArcs;
    VexType vertexs[25] = { 'a','a', 'b', 'c', 'd', 'e','f', 'g','h','i','j','k','l','m','n'};
    AdjMatrix M[Maxnum][Maxnum];
} Graph;

void CreateGraph( Graph &G )
{
    int x,y,w;
    cout<<"输入图的顶点数和边数:"<<endl;
    cin>>G.numOfVertex>>G.numOfArcs; //输入点数和边数
    cout<<"输入每条边的起点、终点、权值:"<<endl;
    for(int i = 1 ; i <= G.numOfArcs ; i++ )
    {
        cin>>x>>y>>w;
        if( x > G.numOfVertex || y > G.numOfVertex )
        {
            cout<<"顶点输入不合法"<<endl;
            exit(OVERFLOW);
        }
        G.M[x][y].degree++;
        G.M[x][y].weight = w;
    }

    for(int x = 1 ;  x <= G.numOfVertex ; x++ )
    {
        for(int y = 1 ; y <= G.numOfVertex ; y++ )
        {
            if( x == y )
            {
                G.M[x][y].weight = 0;
            }
        }
    }

}

void PrintGraph( Graph G )
{
    cout<<"This Graph has "<<G.numOfVertex<<" vertexes and "<<G.numOfArcs<<" arcs"<<endl;
    cout<<"This is AdjMatrix"<<endl;
    for(int x = 1 ;  x <= G.numOfVertex ; x++ )
    {
        for(int y = 1 ; y <= G.numOfVertex ; y++ )
        {
            if( y == 1 )
                cout<<G.M[x][y].degree;
            else
                cout<<" "<<G.M[x][y].degree;
        }
        cout<<endl;
    }

    cout<<"This is WeightMatrix"<<endl;
    for(int x = 1 ;  x <= G.numOfVertex ; x++ )
    {
        for(int y = 1 ; y <= G.numOfVertex ; y++ )
        {
            if(G.M[x][y].weight != INF)
            {
                if( y == 1 )
                    cout<<G.M[x][y].weight;
                else
                    cout<<" "<<G.M[x][y].weight;
            }
            else if(G.M[x][y].weight == INF)
            {
                if( y == 1 )
                    cout<<"∞";
                else
                    cout<<" ∞";
            }
        }
        cout<<endl;
    }

}

typedef struct Stack
{
    int *base;
    int *top;
    int stacksize;
} Stack;

bool isEmpty(Stack S)
{
    if( S.top == S.base )
        return true;
    else
        return false;
}

bool isFull(Stack S)
{
    if( S.top - S.base == S.stacksize )
        return true;
    else
        return false;
}

bool FindInStack(Stack S,ElemType e)
{
    for( int i = 0 ; i <= S.top - S.base - 1 ; i++ )
    {
        if(S.base[i] == e )
            return true;
    }
    return false;
}

Status InitStack(Stack &S)
{
    S.base = (ElemType *) malloc( sizeof(ElemType) * MAXSIZE);
    if(!S.base)
        exit(OVERFLOW);
    S.top = S.base;
    S.stacksize = MAXSIZE;
    return OK;
}

Status Push(Stack &S,ElemType datum)
{
    if( isFull(S) )
        return ERROR;
    else
    {
        *S.top = datum;
        S.top++;
        return OK;
    }
}

ElemType Pop(Stack &S)
{
    if( !isEmpty(S) )
    {
        S.top--;
        return *S.top;
    }
    else
    {
        return ERROR;
    }
}

void PrintStack(Stack S)
{
    cout<<"Stack = "<<endl;
    for( int i = 0 ; i <= S.top - S.base - 1 ; i++ )
    {
        if( i == 0 )
            cout<<S.base[i];
        else
            cout<<" "<<S.base[i];
    }
    cout<<endl;
}

void PrintIndegreeArray(int indegreeArray[],Graph G )
{
    cout<<"this is indegreeArray after init :"<<endl;
    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        if( i == 1 )
            cout<<indegreeArray[i];
        else
            cout<<"  "<<indegreeArray[i];
    }
    cout<<endl<<" ok indegreearray end"<<endl;
}

void InitIndegreeArray(int indegreeArray[],Graph G)
{
    for(int i = 1 ; i <= G.numOfVertex ; i++ )  //第i个顶点
    {
        for(int x = 1 ; x <= G.numOfVertex ; x++ )  //扫描邻接矩阵每一列更新入度
        {
            indegreeArray[i] += G.M[x][i].degree;
        }
    }
    //PrintIndegreeArray(indegreeArray,G);
}

void ScanIndegreeArray(int indegreeArray[],bool visit[],Stack &S,Graph G)
{
    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        if( indegreeArray[i] == 0 && !visit[i] && !FindInStack(S,i) )
        {
            Push(S,i);
        }
    }
    //PrintStack(S);
}

void UpdateIndegree( Graph G,int indegreeArray[],int indexOfPoint )
{
    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        indegreeArray[i] -= G.M[indexOfPoint][i].degree;
    }
    //PrintIndegreeArray(indegreeArray,G);
}

void PrintTopologicalSortSequence( int sequence[],Graph G)
{
    cout<<endl<<"The TopologicalSortSequence is : "<<endl;
    for(int i = 1; i <= G.numOfVertex ; i++ )
    {
        if( i == 1)
            cout<<G.vertexs[sequence[i]];
        else
            cout<<"->"<<G.vertexs[sequence[i]];
    }
}

void TopologicalSort(Graph G,int sequence[] )
{
    Stack S;
    InitStack(S);
    const int maxlength = G.numOfVertex + 1;
    int count = 1;
    bool visit[maxlength] = {false};
    //int sequence[maxlength] = {0};
    int indegreeArray[maxlength] = {0};

    InitIndegreeArray(indegreeArray,G);

    ScanIndegreeArray(indegreeArray,visit,S,G);

    while( !isEmpty(S) )
    {
        int indexOfPoint = Pop(S);
        //cout<<"point : "<<indexOfPoint<<endl;
        visit[indexOfPoint] = visited;
        sequence[count] = indexOfPoint;
        count++;
        //cout<<"count = "<<count<<endl;
        UpdateIndegree(G,indegreeArray,indexOfPoint);
        ScanIndegreeArray(indegreeArray,visit,S,G);
    }
    //cout<<"count = "<<count<<endl;
    if( count - 1 == G.numOfVertex )
        PrintTopologicalSortSequence(sequence,G);
    else
        cout<<"TopologicalSort Failed"<<endl;


}

void PrintEarliestActiveTime(Graph G,int sequence[],int time[] )
{
    cout<<endl<<endl<<"This is earliest active time : "<<endl;
    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        cout<<G.vertexs[sequence[i]]<<" : "<<time[i]<<endl;
    }
}

void EarliestActiveTime( Graph G,int sequence[],int time[] )
{
    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        int currentPoint = sequence[i];
        for(int y = 1 ; y <= G.numOfVertex ; y++ )
        {
            if( G.M[currentPoint][y].degree != 0 )
            {
                time[y] = max( time[y], time[currentPoint] + G.M[currentPoint][y].weight );
            }
        }
    }
    PrintEarliestActiveTime(G,sequence,time);
}

void PrintLatestArriveTime(Graph G,int sequence[], int time[])
{
    cout<<endl<<"This is latest arrive time : "<<endl;
    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        cout<<G.vertexs[sequence[i]]<<" : "<<time[i]<<endl;
    }
}

void LatestArriveTime(Graph G,int sequence[],int time[],int t)
{
    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        time[i] = t;
    }
    for(int i = G.numOfVertex ; i >= 1 ; i-- )
    {
        int currentPoint = sequence[i];
        for(int y = 1 ; y <= G.numOfVertex ; y++ )
        {
            if( G.M[y][currentPoint].degree != 0 )
            {
                time[y] = min( time[y], time[currentPoint] - G.M[y][currentPoint].weight );
            }
        }
    }
    PrintLatestArriveTime(G,sequence,time);
}

void PrintPivotalRoute(int earliesttime[] , int latesttime[] ,int sequence[], Graph G )
{
    cout<<"This is pivotal route"<<endl;
    PrintEarliestActiveTime(G,sequence,earliesttime);
    PrintLatestArriveTime(G,sequence,latesttime);
    for( int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        if( earliesttime[i] == latesttime[i] )
        {
            if( i == 1 )
                cout<<G.vertexs[sequence[i]];
            else
                cout<<"->"<<G.vertexs[sequence[i]];
        }
    }
    cout<<endl;
}

int main()
{
    Graph G;
    CreateGraph(G);
    //PrintGraph(G);
    int sequence[G.numOfVertex + 1] = {0};
    int earliesttime[G.numOfVertex + 1] = {0};
    TopologicalSort(G,sequence);
    EarliestActiveTime(G,sequence,earliesttime);
    //cout<<"ealiest time = "<<earliesttime[G.numOfVertex]<<"  ended"<<endl;
    int ealiest = earliesttime[G.numOfVertex];
    int latesttime[G.numOfVertex + 1] = {0};
    LatestArriveTime(G,sequence,latesttime,ealiest);
    PrintPivotalRoute(earliesttime,latesttime,sequence,G);
    /*InitStack(S);
    Push(S,1);
    cout<<Pop(S);*/

}

/* test
8 10     9 11
1 3 1    1 2 6
1 7 1    1 3 4
2 7 1    1 4 5
2 8 1    2 5 1
3 4 1    3 5 1
4 5 1    4 6 2
4 7 1    5 7 8
7 6 1    5 8 7
8 6 1    6 8 4
6 5 1    8 9 4
         7 9 2
         a - d - f - c - b - e - h - g - i
         0   6   4   5   7   7   15  14  18
         0   6   6   8   7   10  16  14  18
result: 2 8 1 3 4 7 6 5
*/
