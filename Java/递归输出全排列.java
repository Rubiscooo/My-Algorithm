import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main
{

	static int[] arr = new int[100];
	static boolean[] visit = new boolean[100];
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		f(1,n+1);
	}

	public static void f(int flag ,int n)
	{
	    if( flag == n )
	    {
	    	Print(arr,n-1);
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
	
	public static void Print(int[] arr,int n)
	{
		for(int i = 1 ; i <= n ; i++)
		{
				System.out.print(arr[i]);
		}
		System.out.println();
	}
}
