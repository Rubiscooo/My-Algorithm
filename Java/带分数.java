import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main
{

	static int[] arr = new int[100];
	static boolean[] visit = new boolean[100];
	static int tot = 0;
	static int n = 9;
	static int tag;
	static int num = 0;
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		tag = scanner.nextInt();
		f(1,n+1);
		System.out.println(num);
	}

	public static void f(int flag ,int n)
	{
	    if( flag == n )
	    {
	    	Test(arr);
	    }
		for(int i = 1 ; i <= n - 1 ; i++)
		{
			if( visit[i] )
			{
				continue;
			}
			arr[flag] = i;
			visit[i] = true;
			f(flag+1, n);
			visit[i] = false;
		}
	}
	
	public static void Test(int[] arr)
	{
		int a,b,k;
		for(int i = 1 ; i <= n ; i++ )
		{
			a = sum(1, i);
			if( a >= tag ) return;
			for(int  j = (n-i) / 2 ; j <= 8-i ; j++)
			{
				b = sum(i+1, i + 1 + j - 1);
				k = sum(i+j+1, n);
				if( b > k && b % k == 0 && tag == a + b / k)
				{
					System.out.println(tag + " = " + a + " + " + b + "/" + k );
					num++;
				}
			}
		}
	}
	
	public static int sum(int start,int end)
	{
		int ans = 0;
		for(int i = start ; i <= end ; i++)
		{
			ans = 10 * ans + arr[i];
		}
		return ans;
	}

}
