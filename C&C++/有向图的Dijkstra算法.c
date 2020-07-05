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
    VexType vertexs[Maxnum];
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

void Dijkstra( Graph G,int indexOfStartPoint )
//从给定的一个点出发，求该点到其他顶点最短路径长度
{
    int Distance[111]; //改成DEFINE！
    string route[111];
    bool Visit[G.numOfVertex + 1] = {false};
    Visit[indexOfStartPoint] = visited;
    Distance[indexOfStartPoint] = 0;

    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        if( i != indexOfStartPoint)
        {
            Visit[i] = false;
            Distance[i] = G.M[indexOfStartPoint][i].weight;
        }
        if(Distance[i] < INF )
        {
            route[i] += toString(indexOfStartPoint);
            route[i] += "->";
            route[i] += toString(i);
        }
    }

    for(int i = 1  ; i <= G.numOfVertex - 1  ; i++ )
    {
        int min = INF;
        int indexOfNewPoint;

        for(int t = 1 ; t <= G.numOfVertex ; t++ )
        {
            if( !Visit[t] && Distance[t] <= min )
            {
                min = Distance[t];
                indexOfNewPoint = t;
            }
        }

        Visit[indexOfNewPoint] = visited;

        for(int t = 1 ; t <= G.numOfVertex ; t++ )
        {
            if( !Visit[t] && ( min + G.M[indexOfNewPoint][t].weight < Distance[t] ) )
            {
                Distance[t] = min + G.M[indexOfNewPoint][t].weight;
                route[t] = route[indexOfNewPoint];
                route[t] += "->";
                route[t] += toString(t);
            }
        }
    }

    cout<<"The shortest distance from vertex "<<indexOfStartPoint<<" to other point :"<<endl;
    for(int i = 1 ; i <= G.numOfVertex ; i++  )
    {
        if(Distance[i] != INF )
        cout<<"from "<<indexOfStartPoint<<" to "<<i<<" the shortest distance is "<<Distance[i]<<" and the shortest route is "<<route[i]<<endl;
        else
        cout<<"from "<<indexOfStartPoint<<" to "<<i<<" the shortest distance is ∞ and there is no route"<<endl;
    }
    cout<<endl;
}


int main()
{
    Graph G;
    CreateGraph(G);
    PrintGraph(G);
    /*int startPoint;
    cin>>startPoint;
    Dijkstra(G,startPoint);*/
    for(int i = 1 ; i <= G.numOfVertex ; i++ )
    {
        Dijkstra(G,i);
    }
}
