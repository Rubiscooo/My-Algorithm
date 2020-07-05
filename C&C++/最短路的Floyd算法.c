#include <iostream>
#include <string>
#include <sstream>
#include <stdlib.h>
//#include <bits/stdc++.h>

using namespace std;

#define Maxnum 101
#define INF 10086
#define OVERFLOW -2
#define visited true

template<typename T> string toString(const T& t)
//把数值转成string的模板
{
    ostringstream oss;  //创建一个格式化输出流
    oss<<t;             //把值传递如流中
    return oss.str();
}

typedef char VexType;

typedef struct AdjMatrix
{
    int degree = 0;
    int weight = INF;
} AdjMatrix;

typedef struct MatrixGraph  //有向图
{
    int numOfVertex;
    int numOfArcs;
    VexType vertexs[Maxnum] = { 'a', 'a' , 'b' , 'c' ,'d','e','f','g','h','i','j','l','m','n'};
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
void PrintWeightMatrix(Graph G)
{
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

void Floid(Graph G)
{
    string route[101][101];

    for(int i = 1 ; i <= G.numOfVertex ; i++ ) //控制跳点个数
    {
        for(int x = 1 ; x <= G.numOfVertex ; x++ )
        {
            for(int y = 1 ; y <= G.numOfVertex ; y++ )
            {

                if( i == 1 )
                {
                    route[x][y] = toString(G.vertexs[x]);
                }

                if( G.M[x][i].weight + G.M[i][y].weight < G.M[x][y].weight )
                {
                    G.M[x][y].weight = G.M[x][i].weight + G.M[i][y].weight;
                    route[x][y] += "->";
                    route[x][y] += toString(G.vertexs[i]);
                }

                if( i == G.numOfVertex )
                {
                    route[x][y] += "->";
                    route[x][y] += toString(G.vertexs[y]);
                }

            }
        }
    }

    PrintWeightMatrix(G);

    for( int x = 1 ; x <= G.numOfVertex ; x++ )
    {
        for( int y = 1 ; y <= G.numOfVertex ; y++ )
        {
            cout<<"From "<<G.vertexs[x]<<" to "<<G.vertexs[y]<<" The shortest route is :"<<route[x][y]<<" and the length is "<<G.M[x][y].weight<<endl;
        }
    }

}

int main()
{
    Graph G;
    CreateGraph(G);
    PrintGraph(G);
    Floid(G);
}

/* 3 5
1 2 4
1 3 11
2 1 6
2 3 2
3 1 3
*/
