import java.util.*;
public class Main
{
	//花色编码
	static final int REALKING = 7;
	static final int KING = 6;
	static final int king = 5;
	static final int DIAMOND = 4;
	static final int CLUB = 3;
	static final int SPADE = 2;
	static final int HEART = 1;
	
	public static void main(String[] args)
	{
		
		//点数编码
        final int[] CARDS = new int[16];
        for(int i = 3 ; i <= 15 ; i++)
        {
        	CARDS[i] = 10 * i;
        }
        //用一个三位十进制数编码一张牌，个位代表花色，
        //十位和百位代表点数
        List<Integer> allCards = new LinkedList<Integer>();
        
        for(int color = 1 ; color <= 7 ; color++)
        {
        	if( color <= 4)
        	{
        	for(int point = 3 ; point <= 15 ; point++ )
        	  {
        		int newCard = CARDS[point] + color;
        		allCards.add(newCard);
        		allCards.add(newCard);
        		allCards.add(newCard);
        		allCards.add(newCard);
        	   }
        	}
        	else if(color == king )
        	{
        		int newCard = color + 160;
        		allCards.add(newCard);
        		allCards.add(newCard);
        		allCards.add(newCard);
        		allCards.add(newCard);
        	}
        	else if(color == KING )
        	{
        		int newCard = color + 170;
        		allCards.add(newCard);
        		allCards.add(newCard);
        		allCards.add(newCard);
        	}
        	else if(color == REALKING )
        	{
        		int newCard = color + 180;
        		allCards.add(newCard);
        	}
        }        

        Collections.shuffle(allCards);
        
        LinkedList[] players = new LinkedList[6];
        for(int i = 1 ; i <= 5 ; i++)
        {
        	players[i] = new LinkedList<Integer>();
        }
        
        int count = 0;
        
        for(int i = 1 ; i <= 5 ; i++)
        {
        	while(players[i].size() < 43 )
        	{
        		players[i].add(allCards.get(count));
        		count++;
        	}
        }
        players[1].add(allCards.get(count));

        for(int i = 1 ; i <= 5 ; i++)
        {
        	players[i].sort((a,b) -> (int)b-(int)a );
        	//System.out.println(players[i]);
        	//System.out.println(players[i].size());
        }
        
        int kingPlayers = 0;
        for(int i = 1 ; i <= 5 ; i++)
        {
        	if( players[i].contains(187))
        		kingPlayers = i;
        }
        
        int guardCard = 0;
        int guardPlayers = 0;
		for (int i = 0; i <= players[kingPlayers].size() - 1; i++)
		{
			if ( ( (int)players[kingPlayers].get(i)  / 10) < 160)
			{
				if (Collections.frequency(players[kingPlayers], players[kingPlayers].get(i)) == 3)
				{
					guardCard = (int) players[kingPlayers].get(i);
				} else if (Collections.frequency(players[kingPlayers], players[kingPlayers].get(i)) == 4)
				{
					guardCard = (int) players[kingPlayers].get(i);
					guardPlayers = kingPlayers;
				}
			}
		}
        
        for(int i = 1 ; i <= 5 ; i++)
        {
        	if( players[i].contains(guardCard) && guardPlayers == 0 && i != kingPlayers  )
        		guardPlayers = i;
        }
        
       /*for(int t = 1 ; t <= 5 ; t++ )
        {
    	   System.out.print("玩家" + t + "的牌是：");
        	for(int i = 0 ; i <= players[t].size() - 1 ; i++ )
        	{
        		if( i == 0 )
        			System.out.print("[" + PrintCard((int)players[t].get(i)) +",");
        		else 
        			System.out.print(PrintCard((int)players[t].get(i)) + ", ");
        	}
    		System.out.println("]");
        }*/
      
		System.out.println("皇帝是：玩家" + kingPlayers);
		System.out.print("皇帝的牌是：");
		for (int i = 0; i <= players[kingPlayers].size() - 1; i++)
		{
			if (i == 0)
				System.out.print("[" + PrintCard((int) players[kingPlayers].get(i)) + ",");
			else
				System.out.print(PrintCard((int) players[kingPlayers].get(i)) + ", ");
		}
		System.out.println("]");
		
       System.out.println("侍卫对应的牌是：" +PrintCard(guardCard));
       System.out.println("侍卫是：玩家" + guardPlayers);
       System.out.print("侍卫的牌是：");
		for (int i = 0; i <= players[guardPlayers].size() - 1; i++)
		{
			if (i == 0)
				System.out.print("[" + PrintCard((int) players[guardPlayers].get(i)) + ",");
			else
				System.out.print(PrintCard((int) players[guardPlayers].get(i)) + ", ");
		}
		System.out.println("]");
	}
	
	public static String PrintCard(int num)
	{
	   String ans = "";
       int color = num % 10;
       int point = num / 10;
       if( color == DIAMOND )
       {
    	   ans += "方块";
       }
       else if(color == CLUB )
       {
    	   ans += "梅花";
       }
       else if(color == HEART )
       {
    	   ans += "红桃";
       }
       else if(color == SPADE )
       {
    	   ans += "黑桃";
       }
       else if(color == king )
       {
    	   ans += "小王";
    	   return ans;
       }
       else if(color == KING )
       {
    	   ans += "大王";
    	   return ans;
       }
       else if(color == REALKING )
       {
    	   ans += "皇帝";
    	   return ans;
       }
       String s = "";
       if( point >= 3 && point <= 10 )
       {
           s = String.valueOf(point);
           ans += s;
           return ans;
       }
       else if( point == 11 )
       {
    	   return ans + "J";
       }
       else if( point == 12 )
       {
    	   return ans + "Q";
       }
       else if( point == 13 )
       {
    	   return ans + "K";
       }
       else if( point == 14 )
       {
    	   return ans + "A";
       }
       else if( point == 15 )
       {
    	   return ans + "2";
       }
       return ans;
	}
	
}


