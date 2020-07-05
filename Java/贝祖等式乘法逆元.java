public class Main
{
	public static int numofiterate = 1; //迭代次数可以确定数组大小 意义是广义欧几里得除法写了几个式子
	public static int gcd(int a,int b) 	//int可改成long
	{
		if( a % b == 0 )
			{
				System.out.println( a + " = " + a/b + "*" + b + " + " + a%b); //输出广义欧几里得除法的每一步
				return b;
			}
		else 
			{
				numofiterate++;
				System.out.println( a + " = " + a/b + "*" + b + " + " + a%b);
				return gcd(b, a%b);
			}
	}
	
	public static void main(String[] args)
	{
		int a = 1234567898,b = 987654321;
		int gcd = gcd(a, b);
		
		System.out.println("gcd(" + a + "," + b + ") = " + gcd + "\n" + "iterator = " + numofiterate );
		
		//打表求实现 s,t的求解
		int[] s = new int[numofiterate-1];
		int[] t = new int[numofiterate-1];
		int[] q = new int[numofiterate];
		int[] r = new int[numofiterate];
		
		q[0] = a / b;
		r[0] = a % b;
		int s_2 = 1,s_1 = 0;
		int t_2 = 0,t_1 = 1;
		
		for(int i = 0 ; i <= numofiterate - 2 ; i++ )
		{
			if( i == 0 )
			{
				q[1] = b / r[0];
				r[1] = b % r[0];
				t[0] = t_2 - q[0]*t_1;
				s[0] = s_2 - q[0]*s_1;
			}
			else if( i == 1 )
			{
				q[2] = r[0] / r[1];
				r[2] = r[0] % r[1];
				t[1] = t_1 - q[1]*t[0];
				s[1] = s_1 - q[1]*s[0];
			}
			else 
			{
				q[i+1] = r[i-1] / r[i];
				r[i+1] = r[i-1] % r[i];
				t[i] = t[i-2] - q[i]*t[i-1];
				s[i] = s[i-2] - q[i]*s[i-1];
			}
		}
		
		System.out.println("a = " + a + " , " + "b = " + b);
		System.out.println("s = " + s[numofiterate-2] + " , " + "t = " + t[numofiterate-2]);
		
		if( t[numofiterate-2] >= 0)
			System.out.println(s[numofiterate-2] + " * " + a + " + " + t[numofiterate-2] + " * " + b + " = " + gcd);
		else 
			System.out.println(s[numofiterate-2] + " * " + a + " + (" + t[numofiterate-2] + ") * " + b + " = " + gcd);

		if( gcd != 1 )
		{
			System.out.println("a,b不互素没有模逆元");
		}
		else
		{
			int a_rev = s[numofiterate-2];
			int b_rev = t[numofiterate-2];
			while( a_rev < 0 )
			{
				a_rev += b;
			}
			while( b_rev < 0 )
			{
				b_rev += a;
			}
			//System.out.println( a + " 在模 " + b + " 下的乘法逆元是 " + s[numofiterate-2]);
			System.out.println( a + " 在模 " + b + " 下的乘法逆元是 " + a_rev);
			//System.out.println( b + " 在模 " + a + " 下的乘法逆元是 " + t[numofiterate-2]);
			System.out.println( b + " 在模 " + a + " 下的乘法逆元是 " + b_rev);
		}
		
	}
}