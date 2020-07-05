import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Main
{
	private JFrame frmi;
	private JTextField textField;
	public static JTextArea textArea = null;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Main window = new Main();
					window.frmi.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Main()
	{
		initialize();
	}

	private void initialize()
	{
		frmi = new JFrame();
		frmi.setTitle("\u76EE\u5F55i\u7ED3\u6784\u5206\u6790");
		frmi.setBounds(100, 100, 450, 300);
		frmi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmi.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u8F93\u5165\u76EE\u5F55\u540D\uFF1A");
		label.setBounds(10, 10, 121, 35);
		frmi.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(10, 48, 301, 21);
		frmi.getContentPane().add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(10, 79, 414, 172);
		frmi.getContentPane().add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 79, 414, 172);
		frmi.getContentPane().add(scrollPane);
		
		JButton button = new JButton("\u786E\u8BA4");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.textArea.setText("");
				Main.readFileAndDirectory(textField.getText(), 0);
				textField.setText("");
			}
		});
		button.setBounds(331, 47, 93, 23);
		frmi.getContentPane().add(button);
		
	}
	
	public static void readFileAndDirectory(String path,int num)
	{
		File file = new File(path);
		//使用File中的list()方法判断路径path是否为一个目录
		//若是就返回当前目录下所有文件和目录的名称 若不是就返回null
		if( file.list() == null )
		{
			Main.textArea.append("该路径不是目录！\n");
			return;
		}
		//遍历该目录下的每一个文件或目录名称 还是用list()方法判断是文件还是目录
		for (String string : file.list())
		{
			File childfile = new File(path + "\\\\" + string);
			//若是文件就打印其绝对路径
			if( childfile.list() == null )
			{
				Main.Print(childfile.getPath(), false, num);	
			}
			//若是目录就先打印其绝对路径然后递归分析该目录下的文件和目录并打印
			else
			{
				Main.Print(childfile.getPath(), true, num);
				Main.readFileAndDirectory(path + "\\\\" + string,num+2);
			}
		}
	}
	//以层次化结构打印文件或目录的绝对路径
	public static void Print(String name,boolean isDirectory,int num)
	{
		for(int i = 1 ; i <= num ; i++)
		{
			Main.textArea.append("-");
		}
		if(isDirectory)
		{
			Main.textArea.append("[目录] " + name + "\n");
		}
		else
		{
			Main.textArea.append("[文件] " + name + "\n");
		}
	}
	
}

使用命令行形式实现功能，完整代码如下。
package exp1_file;

import java.io.File;

public class Main 
{
	//在命令行中输入的参数会传到args中
	public static void main(String[] args)
	{
		String path = args[0];
		//如果目录名含有空格需要进行处理把空格补上
		for(int i = 1 ; i <= args.length - 1 ; i++)
		{
			path = path + " " + args[i];
		}
		FileList.readFileAndDirectory(path,0);
	}
}

class FileList
{
	//将输入参数path所指定的本地磁盘路径下的所有目录（包含子目录）和文件的名称（指明是目录还是文件）
	//以层次化结构打印出来
	//参数num表示递归深度 用于打印层次化结构
	public static void readFileAndDirectory(String path,int num)
	{
		File file = new File(path);
		//使用File中的list()方法判断路径path是否为一个目录
		//若是就返回当前目录下所有文件和目录的名称 若不是就返回null
		if( file.list() == null )
		{
			System.out.println("该路径不是目录！");
			return;
		}
		//遍历该目录下的每一个文件或目录名称 还是用list()方法判断是文件还是目录
		for (String string : file.list())
		{
			File childfile = new File(path + "\\\\" + string);
			//若是文件就打印其绝对路径
			if( childfile.list() == null )
			{
			    FileList.Print(childfile.getPath(), false, num);	
			}
			//若是目录就先打印其绝对路径然后递归分析该目录下的文件和目录并打印
			else
			{
				FileList.Print(childfile.getPath(), true, num);
				FileList.readFileAndDirectory(path + "\\\\" + string,num+2);
			}
		}
	}
	//以层次化结构打印文件或目录的绝对路径
	public static void Print(String name,boolean isDirectory,int num)
	{
		for(int i = 1 ; i <= num ; i++)
		{
			System.out.print("-");
		}
		if(isDirectory)
		{
			System.out.println("[目录] " + name);
		}
		else
		{
			System.out.println("[文件] " + name);
		}
	}
}
