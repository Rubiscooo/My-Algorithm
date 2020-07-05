import java.util.Scanner;

//某个节点输出几个|和它的父节点的父节点有关

public class Main
{
	
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		String string = scanner.nextLine();
		String[]strings = string.split(" ");
		
		int n = Integer.parseInt(strings[0]);
		Node tree = new Node(n);
		
		tree.indexofshu = Integer.toString(n).length() + 2;
		
		for(int i = 1 ; i <= strings.length - 1 ; i++)
		{
			tree.addData(Integer.parseInt(strings[i]));
			
		}
		
		tree.PrintTree(tree);

	}

}


class Node
{
	public Node left = null,right = null;
	public Node father = null;
	public int indexofshu = 0;
	public int data = 0;
	public int numOfLayer = 1;
	public Node()
	{
		
	}
	public Node(int n)
	{
		this.data = n;
	}
	
	public static boolean judge(Node node)
	{
		if( node.father !=null && node.father.father != null)
		{
			Node p	= node.father.father;
			if( (p.left != null && p.left.left != null && node == p.left.left) || ( p.right != null && p.right.right != null &&node == p.right.right) )
			{
				return false;
			}
			else 
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
	public void PrintTree(Node node)
	{
		if( node == null )
		{
			return;
		}
		else if( node.left == null && node.right == null )
		{
			if( node.numOfLayer == 1  )
			{
				System.out.println(node.data + "-|" );
			}
			else
			{
				int[] arr = new int[node.numOfLayer];
				Node p = node.father;
				int t = node.numOfLayer - 1;
				arr[t--] = p.indexofshu;
				p = node;
				while (true)
				{
					if (judge(p))
					{
						arr[t--] = p.father.father.indexofshu;
					} else
					{
						arr[t--] = 0;
					}
					if (t == -1 )
						break;
					p = p.father;
				}
				int x = 1;
				for (int i = 1; i <= node.father.indexofshu; i++)
				{
					if (arr[x] == 0)
					{
						x++;
					}
					if (i != arr[x])
					{
						System.out.print(".");
					} else if (i == arr[x])
					{
						System.out.print("|");
						x++;
					}
				}
				System.out.println("-" + node.data);
			}
		}
		else 
		{
			PrintTree(node.right);
			Print(node, node.numOfLayer);
			PrintTree(node.left);
		}
	}
	
	public static void Print(Node node,int layer)
	{

		if( node.numOfLayer == 1  )
		{
			System.out.println(node.data + "-|" );
		}
		else 
		{
			int[] arr = new int[layer];
			Node p = node.father;
			int t = layer-1;
			arr[t--] = p.indexofshu;
			p = node;
			while( true )
			{
				if( judge(p) )
				{
					arr[t--] = p.father.father.indexofshu;
				}
				else 
				{
					arr[t--] = 0;
				}
				if( t == -1 )
					break;
				p = p.father;
			}
			if( node.left != null || node.right != null )
			{
				int x = 1;
				for(int i = 1 ; i <= node.father.indexofshu ; i++)
				{
					if( arr[x] == 0)
					{
						x++;
					}
					if( i != arr[x] )
					{
						System.out.print(".");
					}
					else if( i == arr[x] )
					{
						System.out.print("|");
						x++;
					}
				}
				System.out.println("-" + node.data + "-|");
			}
		}
	}
	
 	public void addData(int n)
	{
		Node pos = this;
		Node newNode = new Node(n);
		while( true )
		{
			if (n > pos.data)
			{
				if (pos.right == null)
				{
					pos.right = newNode;
					newNode.father = pos;
					newNode.numOfLayer++;
					newNode.indexofshu = newNode.father.indexofshu + Integer.toString(newNode.data).length() + 3;
					return;
				} 
				else
				{
					pos = pos.right;
					newNode.numOfLayer++;
				}
			} 
			else if (n < pos.data)
			{
				if (pos.left == null)
				{
					pos.left = newNode;
					newNode.father = pos;
					newNode.numOfLayer++;
					newNode.indexofshu = newNode.father.indexofshu + Integer.toString(newNode.data).length() + 3;
					return;
				} 
				else
				{
					pos = pos.left;
					newNode.numOfLayer++;
				}
			}
		}
		
	}
 	
}

