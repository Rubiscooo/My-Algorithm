import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main1
{
	public static void main(String[] args) throws IOException
	{
		File file = new File("D:\\javaaa\\山科大Java学习交流群.txt");
		//这个文本文件的编码格式是UTF-8，而ecplise默认编码格式是GBK
		//如果不修改默认值输出中文时会出现乱码
		//可用getBytes()方法指定编码格式
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		Set<String> names = new HashSet<String>();
		String pattrenOfDate = "\\d{4}\\-\\d{2}\\-\\d{2}";
		String pattrenOfName = "[\u4e00-\u9fa5|\\w|\\W|\\s]+";
		String pattrenOfTime = "\\d{2}:\\d{2}:\\d{2}";
		String patternOfQQ = "\\((\\d+)\\)"; 
        String patternOFEmail = "<([\\d|\\w|\\W]+)>";
		String patternOfQQorEmail = "[<|\\(]([\\d|\\w|\\W]+)[>|\\)]";
		String pattern = "(\\d{4}\\-\\d{2}\\-\\d{2})\\s+(\\d{2}:\\d{2}:\\d{2})\\s([\u4e00-\u9fa5|\\w|\\W|\\s]+)[<|\\(]([\\d|\\w|\\W]+)[>|\\)]";
		Pattern r = Pattern.compile(pattern);
		Map<String, List<Message>> nameandmessages = new HashMap<String, List<Message>>();
		Map<String, String> nametoQQorEmail = new HashMap<String,String>();
		String line = null;
		String name = null;
		String qqoremail = null;
		String[] yymmdd = new String[3];
		String[] hhmmss = new String[3];
		while ((line = br.readLine()) != null)
		{
			Matcher m = r.matcher(line);
			if (m.find())
			{
				yymmdd = m.group(1).split("-");
				hhmmss = m.group(2).split(":");
				name = m.group(3);
				qqoremail = m.group(4);
				if( names.add(name) )
				{
					List<Message> list = new ArrayList<Message>();
					nametoQQorEmail.put(name, qqoremail);
					nameandmessages.put(name, list);
					list.add(new Message(
							Integer.parseInt(yymmdd[0]),
							Integer.parseInt(yymmdd[1]),
							Integer.parseInt(yymmdd[2]),
							Integer.parseInt(hhmmss[0]),
							Integer.parseInt(hhmmss[1]),
							Integer.parseInt(hhmmss[2])
							) 
							);
				}
				else
				{
					nameandmessages.get(name).add(new Message(
							Integer.parseInt(yymmdd[0]), 
							Integer.parseInt(yymmdd[1]), 
							Integer.parseInt(yymmdd[2]),
							Integer.parseInt(hhmmss[0]),
							Integer.parseInt(hhmmss[1]),
							Integer.parseInt(hhmmss[2]) 
							) 
							);
				}
			}
			else if( !line.equals("") )
			{
				int index = nameandmessages.get(name).size() - 1;
				if( nameandmessages.get(name).size() == 0 )
				{
					nameandmessages.get(name).get(0).AddMessage(line);
				}
				else
				{
					nameandmessages.get(name).get(index).AddMessage(line);
				}
			}
		}
		
		Date beginTime = new Date(2018, 9, 18, 21, 5, 39);
		Date endTime = new Date(2018, 10, 8, 21, 5, 39);
		PrintMessage(getMessagesById("计科17-2韩承磊",beginTime,endTime,nameandmessages));
		System.out.println( "话痨是 : " + getChatter(names, nameandmessages) +"   QQorEmail: " + nametoQQorEmail.get(getChatter(names, nameandmessages)) );
	}

	public static List<Message> getMessagesById(String id,Date beginTime,Date endTime,Map<String, List<Message>> nameandmessages)
    {
    	List<Message> list = nameandmessages.get(id);
    	List<Message> ans = new ArrayList<Message>();
    	for( Message message : list )
    	{
    		if( Message.compareDate(message.date, beginTime) && !Message.compareDate(message.date, endTime)  )
    		{
    			ans.add(message);
    		}
    	}
    	return list;
    }
    
    public static void PrintMessage(List<Message> list)
	{
		for( Message message : list )
		{
			System.out.println(message.messageList);
		}
	}

    public static String getChatter(Set<String> names,Map<String, List<Message>> nameandmessages)
    {
		String talkativePerson = null;
		int max = 0;
		for(String string : names)
		{
			if ( !string.equals(" ") && !string.equals("Java技术-张峰") )
			{
				if (nameandmessages.get(string).size() > max)
				{
					max = nameandmessages.get(string).size();
					talkativePerson = string;
				}
			}
		}
		return talkativePerson;
    }
}

class Date
{
	public Date(int year, int month, int day, int hour, int minute, int second)
	{
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	public int year;
	public int month;
	public int day;
	public int hour;
	public int minute;
	public int second;
	
}

class Message 
{
	public void AddMessage(String message)
	{
		this.messageList.add(message);
	}
	public Message(int year, int month, int day,int hour,int minute,int second)
	{
		this.date = new Date(year, month, day, hour, minute, second);
		this.messageList = new ArrayList<String>();
	}
    public static boolean compareDate(Date date1,Date date2)
    {
    	int ymd1 = 10000 * date1.year + 100 * date1.month + date1.day;
    	int ymd2 = 10000 * date2.year + 100 * date2.month + date2.day;
    	int hms1 = 1000  * date1.hour + 100 * date1.minute + date1.second;
    	int hms2 = 1000  * date2.hour + 100 * date2.minute + date2.second;
    	if( ymd1 > ymd2 )
    		return true;
    	else if( ymd1 < ymd2)
    		return false;
    	else if(ymd1 == ymd2)
    	{
    		if( hms1 >= hms2 )
    			return true;
    		else if( hms1 < hms2 )
    			return false;
    	}
    	return false;
    }
	public Date date;
	public List<String> messageList;
	
}
