#include <bits/stdc++.h>

using namespace std;
//定义结点类
typedef class Node
{
public:
    int f;  //叶结点的权值
    char c = ' '; //该结点对应的字符
    Node *left = NULL; //指向左孩子的指针
    Node *right = NULL; //指向右孩子的指针
public ://结点的构造函数
    Node()
    {

    }
    Node(int num)
    {
        this->f = num;
    }
    Node(int num,char c)
    {
        this->f = num;
        this->c = c;
    }
}Node;

int n = 5; //需要编码的字符数量
char chars[] = {'b','c','j','m','p'}; //需要编码的字符
int fs[] = {5,6,2,9,7}; //每个叶结点的权值
//冒泡排序 nodes是需要排序的元组 n是元组中需要排序的元素个数
void sort(Node* nodes[],int n)
{
    for(int i = 1 ; i <= n-1 ; i++ )
        for(int j = 0 ; j <= n-2 ; j++)
        {
            if( nodes[j]->f > nodes[j+1]->f )
            {
                swap(nodes[j]->f,nodes[j+1]->f);
                swap(nodes[j]->c,nodes[j+1]->c);
                swap(nodes[j]->left,nodes[j+1]->left);
                swap(nodes[j]->right,nodes[j+1]->right);
            }
        }
}
//测试输出
void testPrint(Node * nodes[],int n)
{
    for(int i = 0 ; i <= n-1 ; i++)
    {
        cout<<nodes[i]->f<<" ";
        //cout<<nodes[i]->c<<"-"<<nodes[i]->f<<" ";
    }
    cout<<endl;
}
//通过深度优先遍历确定每个字符的哈夫曼编码
void dfs(Node *root,string s)
{
    if( !root->left && !root->right )
    {
        cout<<root->c<<" : "<<s<<endl;
        return;
    }
    else
    {
        if( root->left ) dfs(root->left,s+"0");
        if( root->right ) dfs(root->right,s+"1");
    }
}

int main()
{
    Node ** nodes = (Node **)malloc(sizeof(Node *) * n);
    //建立初始的所有叶结点
    for(int i = 0 ; i <= n-1 ; i++)
    {
        nodes[i] = (Node *)new Node(fs[i],chars[i]);
    }
    //合并两个权值最小的结点 一共合并n-1次
    for(int i = 0 ; i <= n-2 ; i++)
    {
        int num = n-i; //当前剩余结点个数

        sort(nodes,num); //按权值对结点排序
        //testPrint(nodes,num);

        //建立合并后的新结点
        Node* newnode = (Node*)new Node();

        newnode->f = nodes[0]->f + nodes[1]->f;
        newnode->left = nodes[0];
        newnode->right = nodes[1];
        //把合并后的结点放在第一个位置
        nodes[0] = newnode;
        //后边的所有结点前移
        for(int i = 1 ; i <= num-1  ; i++)
        {
            swap( nodes[i],nodes[i+1] );
        }

    }
    //testPrint(nodes,1);
    //输出哈夫曼编码的结果
    cout<<"Huffman code : "<<endl;
    dfs(nodes[0],"");

}
