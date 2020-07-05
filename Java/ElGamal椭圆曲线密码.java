public class ElGamal
{
	public static int n = 1; 
	//椭圆曲线的参数
	public static int a = 0;
	public static int b = 0;
	public static int p = 0;
	//生成元
	public static Point G = null;
	
	//存放计算点和的中间结果 优化时可用
	//public static Hashtable<Integer, Point> media = new Hashtable<Integer, Point>();
	
	//初始化椭圆曲线和生成元
	public static void initEcplise(int a,int b,int p,int x,int y)
	{
		ElGamal.a = a;
		ElGamal.b = b;
		ElGamal.p = p;
		ElGamal.G = new Point(x,y);
	}
	//计算a,b的最大公因数
	public static int gcd(int a,int b) 	//int可改成long
	{
		if( a % b == 0 )
			{
				return b;
			}
		else 
			{
				n++;
				return gcd(b, a%b);
			}
	}
	//计算a在模m下的乘法逆元
	public static int inv(int a,int m)
	{
		n = 1;
				
		int gcd = gcd(a, m);
		
		//打表求实现 s,t的求解
		int[] s = new int[n-1];
		int[] t = new int[n-1];
		int[] q = new int[n];
		int[] r = new int[n];
		
		q[0] = a / m;
		r[0] = a % m;
		int s_2 = 1,s_1 = 0;
		int t_2 = 0,t_1 = 1;
		
		for(int i = 0 ; i <= n - 2 ; i++ )
		{
			if( i == 0 )
			{
				q[1] = m / r[0];
				r[1] = m % r[0];
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
				
		if( gcd != 1 )
		{
			//System.out.println("a,b不互素没有模逆元");
			return -1;
		}
		else
		{
			int a_rev = s[n-2];
			while( a_rev < 0 )
			{
				a_rev += m;
			}
			return a_rev;
		}
		
	}
	//计算a模m
	public static int mod(int a,int m)
	{
		if( a > 0 )
			return a % m;
		else 
		{
			while( a < 0 )
			{
				a += m;
			}
			return a % m;
		}
	}
	//计算点px+点py
	public static Point add(Point px,Point py)
	{
		int lamda = 0;
		int fz = 0;
		int fm = 0;
		int x1 = px.x;
		int y1 = px.y;
		int x2 = py.x;
		int y2 = py.y;
		
		if( x1 == x2 && y1 == y2 )
		{
			fz = 3*x1*x1+a;
			fm = 2*y1;
		}
		else 
		{
			fz = y2-y1;
			fm = x2-x1;
		}
		
		if( gcd(Math.abs(fm) , p) != 1 )
		{
			return null;
		}
		else 
		{
			if( (fz > 0 && fm > 0) || (fz < 0 && fm < 0) )
			{
				lamda = mod( Math.abs(fz)*inv(Math.abs(fm) , p) , p);
			}
			else 
			{
				lamda = mod( -(Math.abs(fz)*inv(Math.abs(fm) , p)) , p);
			}
			
			int x3 = mod(lamda*lamda-x1-x2, p);
			int y3 = mod(lamda*(x1-x3)-y1, p);
				
			return new Point(x3,y3);
		}


		
	}
	//计算kp
	public static Point calMulPoint(Point p,int k)
	{
		Point ans = p;
		//可以递归二分 加快计算过程
		for(int i = 1 ; i <= k - 1 ; i++ )
		{
			ans = add(ans, p);
			if( ans == null )
			{
				System.out.println("该点不在曲线上 ！");
				return null;
			}
		}
		
		//System.out.println(k + "" + p + " = " + ans);
		
		return ans;
	}
	//计算公钥
	public static Point calPublicKey(int na)
	{
		return calMulPoint(G, na);
	}
	//加密 
	public static Point[] encrypt(int k,Point pm,Point pa)
	{
		Point[] pair = new Point[2];
		
		pair[0] = calMulPoint(G, k);
		pair[1] = add(pm, calMulPoint(pa, k));
		
		return pair;
	}
	
	public static void main(String[] args)
	{
		initEcplise(1, 6, 11,2,7);
		//initEcplise(-1, 188, 751, 0, 376);
		Point publicKey = calPublicKey(7);
		System.out.println("公钥 : " + publicKey);
		Point[] cm = encrypt(3, new Point(10, 9) , publicKey );
		System.out.println("密文  :  { " + cm[0] + " , " + cm[1] + " } " );
	}

}
//点类
class Point
{
	public int x;
	public int y;
	
	public Point()
	{
		
	}
	
	public Point(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public String toString()
	{
		return "( " + x + " , " + y + " )";
	}
}