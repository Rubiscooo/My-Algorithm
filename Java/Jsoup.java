import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAPI
{
	//将API保存在项目的src文件夹内的apis内 每个类一个文本文件 以该类的类名命名
	public static String savepath = ".\\src\\apis";
	//API文档所在路径
	public static String apipath = ".\\api";
	//API文档中allclasses-frame.html这个页面的路径 
	public static String allclassespath = ".\\api\\allclasses-frame.html";
	
	public static void main(String[] args) throws IOException
	{
		//创建本地存储路径
		new File(savepath).mkdir();
		//从allclasses-frame.html为切入点进行内容提取
	    Document allclasses = Jsoup.parse(new File(allclassespath), "utf-8");
	    //提取所有<a>标签
	    Elements classnames = allclasses.select("a");
	    //一共690个类
	    for(int i = 0 ; i <= classnames.size()-1 ; i++)
	    {
	    	//创建该类的文本文件以保存该类的API
	    	File apitext = new File(savepath + "\\" + classnames.get(i).text() + ".txt" );
	    	apitext.createNewFile();
	    	//创建文件输出流以便将API内容写入文件
	    	BufferedWriter bw = new BufferedWriter(new FileWriter(apitext));
	    	//进入API中该类的页面 提取类名、方法名、方法API
	    	Document classhtml = Jsoup.parse(new File(apipath + "\\" + classnames.get(i).attr("href")),"utf-8");
	    	//提取类名并写入文件
	    	String classname = classhtml.select("h2").text();
	    	bw.write(classname + "\n\n");
	    	bw.flush();
	    	//选取API中包含细节介绍的<div>标签 若类中没有对成员变量和方法的介绍则跳过
		    Elements divdetails = classhtml.select("div[class=details]");
		    if(divdetails.size() == 0 ) {System.out.println("successfully extract " + classname + " classes num : " + i); continue;}
		    //获取该标签所包含的HTML内容 同时将&nbsp;替换为空格 否则提取结果会出现乱码
		    Document divdetailshtml = Jsoup.parse(divdetails.get(0).outerHtml().replace("&nbsp;", " "));
		    //获取API中包含细节介绍的<li>标签
		    Element liall = divdetailshtml.select("li[class=blockList]").get(0);
		    //获取每部分细节的无序列表<ul>
		    Elements uls = liall.children();
		    //对每个细节列表的内容进行提取
		    for(int j = 0 ; j <= uls.size()-1 ; j++)
		    {
		    	Element ul = uls.get(j);
		    	//提取该部分标题并写入文件
		    	bw.write(ul.select("h3").text() + "\n\n");
		    	//System.out.println(ul.select("h3").text());
		    	//提取每个成员、方法的API
		    	Elements apis = ul.select("li[class=blocklist]");
		    	//注意跳过第一个 下标从1开始
		    	for(int k = 1 ; k <= apis.size()-1 ; k++)
		    	{
		    		Element apidetails = apis.get(k);
		    		//提取成员变量或方法名并写入文件
		    		bw.write(apidetails.select("h4").get(0).text() + "\n");
		    		//提取成员变量或方法的完整定义并写入文件
		    		bw.write(apidetails.select("pre").get(0).text() + "\n");
		    		//若有对成员变量或方法的具体介绍则也写入文件
			    	if( apidetails.select("div[class=block]").size() != 0 )
			    	{
			    		bw.write(apidetails.select("div[class=block]").text() + "\n");
			    	}
			    	//如果方法有对返回值、参数、异常等信息的说明也写入文件
			    	if( apidetails.select("dl").size() != 0 ) 
			    	{
				    	Elements dls = apidetails.select("dl").get(0).children();
				    	for(Element dl : dls )
				    	{
				    		bw.write(dl.text() + "\n");
				    	}
			    	}
			    	bw.write("\n");
			    	bw.flush();
		    	}
		    }
		    bw.flush();
		    bw.close();
	    	System.out.println("successfully extract " + classname + " classes num : " + (i+1)+"" );
	    }
	}
}
