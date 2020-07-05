#include <bits/stdc++.h>

using namespace std;

#define MAXSIZE 101
#define INF 65535

typedef int Point;

typedef struct
{
    int count = 0;
    int numOfPoint=0;
    int group[MAXSIZE]; //存下标为i所对应的顶点属于哪一个组
}UnionFound;

void InitUnionFound(UnionFound &UF,int size)
{
    UF.numOfPoint = size;
    for(int i = 1 ; i <= UF.numOfPoint ; i++ )
    {
        UF.group[i] = i;
    }
    UF.count = UF.numOfPoint;
}

void Union(Point a, Point b,UnionFound &UF)
{
    int ga = UF.group[a];
    int gb = UF.group[b];

    for(int i = 1 ; i <= UF.numOfPoint ; i++ )
    {
        if( UF.group[i] == gb )
        {
            UF.group[i] = ga;
        }
    }
    UF.count--;
}

bool BelongToSameGroup(Point a,Point b,UnionFound UF)
{
    if( UF.group[a] == UF.group[b] )
        return true;
    else
        return false;
}

int getNumberOfGroups(UnionFound UF)
{
    return UF.count;
}

int find(Point p,UnionFound UF)
{
    return UF.group[p];
}

void PrintUnionFound(UnionFound UF)
{
    for(int i = 1 ; i <= UF.numOfPoint ; i++ )
    {
        cout<<"Point "<<i<<" is on the group of "<<UF.group[i]<<"."<<endl;
    }
}

typedef struct AdjMatrix
{
    int degree = 0;
    int weight = INF;
} AdjMatrix;

typedef struct
{
    Point start;
    Point end;
    int weight;
}Edge;

typedef struct MatrixGraph  //有向图
{
    int numOfVertex;
    int numOfArcs;
    AdjMatrix M[MAXSIZE][MAXSIZE];
    Edge edge[MAXSIZE];
} Graph;

void CreateGraph( Graph &G )
{
    int x,y,w;
    cout<<"输入图的顶点数和边数:"<<endl;
    cin>>G.numOfVertex>>G.numOfArcs; //输入点数和边数
    if( G.numOfVertex > MAXSIZE || G.numOfVertex < 0 )
    {
        cout<<"顶点数输入不合法"<<endl;
    }
    cout<<"输入每条边的起点、终点、权值:"<<endl;
    for(int i = 1 ; i <= G.numOfArcs ; i++ )
    {
        cin>>x>>y>>w;
        if( x > G.numOfVertex || y > G.numOfVertex )
        {
            cout<<"顶点输入不合法"<<endl;
            exit(OVERFLOW);
        }
        if( G.M[x][y].weight != INF )
        {
            cout<<"边输入不合法"<<endl;
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

int main()

{
    UnionFound UF;
    InitUnionFound(UF,6);
    Union(1,2,UF);
    cout<<find(5,UF)<<endl;
    PrintUnionFound(UF);
}
