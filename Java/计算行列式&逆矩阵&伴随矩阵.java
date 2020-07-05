import java.util.Scanner;

public class Test
{
	public static void swap(double[][] arr,int x1,int y1,int x2,int y2)
	{
		double swap;
		swap  = arr[x1][y1];
		arr[x1][y1] = arr[x2][y2];
		arr[x2][y2] = swap;
	}	
	//用行列式按列展开实现计算行列式Matrix的值                 
	public static double det(double[][] Matrix)
	{
		//传递参数时是传递了引用，操作时会改变原来对象的内容，因此复制一份就不hi改变原对象的内容
		double[][] arr = new double[Matrix.length][Matrix[0].length];
		for(int x = 0 ; x <= arr.length-1 ; x++)
			for(int y = 0 ; y <= arr[0].length-1 ; y++)
				arr[x][y] = Matrix[x][y];
		//swap记录行列式两行有没有互换过
		boolean swap = false;
		//numofiterate记录迭代次数用于数组定位  迭代了几次就降阶了几次
		int numofiterate = (int) arr[0][0];
		//n是要求行列式的阶数
		int n = arr.length-1;
		//降阶到一阶或二阶行列式就直接计算 
		if(  1 == n - numofiterate )
		{
			return arr[1+numofiterate][1+numofiterate];
		}
		else if(  2 == n - numofiterate)
		{
			double ans = arr[1+numofiterate][1+numofiterate]*arr[1+numofiterate+1][1+numofiterate+1]-arr[1+numofiterate][1+numofiterate+1]*arr[1+numofiterate+1][1+numofiterate];
			return ans;
		}
		else 
		{
			boolean zero = true;
			
			for( int i = 1 + numofiterate  ; i <= n ; i++ )
			{
				if( arr[i][1 + numofiterate ] != 0)
				{
					zero = false;
					if( i != 1 + numofiterate  )
					{
						//把第一列不为0的那一行换到第一行
						for(int y = 1 + numofiterate  ; y <= n ; y++)
						{
							swap(arr, 1 + numofiterate , y, i, y);
						}
						swap = true;
					}
					break;
				}
			}
			
			//行列式中某一列全为0 则结果为0
			if( zero )
				return 0;
			
			for(int x = 1 + numofiterate + 1 ; x <= n ; x++)
			{
				if( arr[x][1+numofiterate] == 0)
				{
					continue;
				}
				double k = arr[x][1+numofiterate]/arr[1+numofiterate][1+numofiterate];
				//进行行变换让第一列只有一个不为0的数
				for(int y = 1 + numofiterate ; y <= n ; y++)
				{
					arr[x][y] = arr[x][y] - k*arr[1+numofiterate][y];
				}
			}
			
			arr[0][0]++;
			
			double ans = arr[1+numofiterate][1+numofiterate]*det(arr);
			
			if( swap )
				return -1*ans;
			else 
				return ans;
		}
	}
	//输出矩阵arr
	public static void printarr(double[][] arr)
	{
		for(int x = 1 ; x <= arr.length -1 ; x++ )
		{
			for(int y = 1 ; y <= arr[x].length - 1 ; y++)
			{
				System.out.print(arr[x][y] + " " );
			}
			System.out.println();
		}
		System.out.println();
	}
	//矩阵arr数乘k
	public static double[][] nummul(double[][] Matrix,double k)
	{
		double[][] arr = new double[Matrix.length][Matrix[0].length];
		for(int x = 0 ; x <= arr.length-1 ; x++)
			for(int y = 0 ; y <= arr[0].length-1 ; y++)
				arr[x][y] = Matrix[x][y];
		for(int x = 1 ; x <= arr.length-1 ; x++)
		{
			for(int y = 1 ; y <= arr[x].length-1 ; y++)
			{
				arr[x][y] *= k;
			}
		}
		return arr;
	}
	//矩阵arr转置
	public static void transpose(double[][] arr)
	{
		for(int x = 1 ; x <= arr.length-1 ; x++)
		{
			for(int y = 1 ; y <= arr[0].length-1 ; y++)
			{
				if( y > x )
				{
					swap(arr, x, y, y, x);
				}
			}
		}
	}
	//矩阵arr的逆矩阵 用arr的伴随矩阵除以arr的行列式来实现
	public static double[][] inv(double[][] arr)
	{
		//伴随矩阵
		double[][] adjoint = new double[arr.length][arr[0].length];
		//求伴随矩阵中x行y列的元素
		for(int x = 1 ; x <= adjoint.length - 1 ; x++)
		{
			for(int y = 1 ; y <= adjoint[x].length - 1 ; y++)
			{
				//找出去掉第x行和第y列后的矩阵
				double[][] m = new double[arr.length-1][arr[0].length-1];
				int m_x = 1;
				int m_y = 1;
				for(int i = 1 ; i <= arr.length - 1 ; i++)
				{
					for(int j = 1 ; j <= arr[0].length - 1 ; j++)
					{
						if( i != x && j != y )
						{
							m[m_x][m_y++] = arr[i][j];
						}
						if( i == x )
						{
							m_x--;
							break;
						}
					}
					m_x++;
					m_y = 1;
				}
				adjoint[x][y] = Math.pow(-1, x+y)*det(m);
			}
		}
		//转置一下就是伴随矩阵
		transpose(adjoint);
		
		return nummul(adjoint, 1/det(arr));
	}
	
	public static void main(String[] args)
	{
		double[][] arr1 = { {0,0},{0,1} };
		double[][] arr2 = { {0,0,0} , {0,3,4} , {0,5,6} };
		double[][] arr3 = { {0,0,0,0} , {0,1,2,-4} , {0,-2,2,1} ,{0,-3,4,-2} };
		double[][] arr4 = { {0,0,0,0,0}, {0,123,456,789,111} ,{0,5,6,7,8} ,{0,9,10,11,12} ,{0,13,14,15,16} };
		Scanner scanner = new Scanner(System.in);
		System.out.println("输入行列式阶数");
		int n = Integer.parseInt(scanner.nextLine().trim().split("\\s+")[0]);
		double[][] arrn = new double[n+1][n+1];
		String[] data = new String[n+1];
		for(int x = 1 ; x <= n ; x++ )
		{
			System.out.println("输入行列式的第" + x + "行");
			data[x] = scanner.nextLine();
			data[x] = data[x].trim();
		}
		for(int x = 1 ; x <= n ; x++ )
		{
			String[] strings = data[x].split("\\s+");
			for(int t = 0 ; t <= strings.length - 1 ; t++)
			{
				arrn[x][t+1] = Double.parseDouble(strings[t]);
			}
		}
		System.out.println("行列式为: ");
		printarr(arrn);
		
		System.out.println( "行列式的值为 : " + Math.round( det(arrn) ) );
		
		System.out.println("逆矩阵为: ");
		printarr(inv(arrn));
		
		System.out.println("伴随矩阵为 : ");
		printarr(nummul(inv(arrn), det(arrn)));
	}
}


