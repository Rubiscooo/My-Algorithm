import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class WinZipUI
{

	private JFrame frmWinzip;

	public static JTextArea statusTextArea = null;
	public static JTextArea zipPathTextArea = null;
	public static JTextArea filePathTextArea = null;
	public static JTextArea unzipPathTextArea = null;
	
	public static ZipOutputStream zos = null;
	public static String parentPath = null;
	public static HashSet<String> entryPaths = null;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					WinZipUI window = new WinZipUI();
					window.frmWinzip.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WinZipUI()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmWinzip = new JFrame();
		frmWinzip.setTitle("WinZip");
		frmWinzip.setBounds(100, 100, 611, 417);
		frmWinzip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWinzip.getContentPane().setLayout(null);
		
		zipPathTextArea = new JTextArea();
		zipPathTextArea.setLineWrap(true);
		zipPathTextArea.setWrapStyleWord(true);
		zipPathTextArea.setEditable(false);
		zipPathTextArea.setBounds(190, 23, 224, 24);
		frmWinzip.getContentPane().add(zipPathTextArea);
		
		unzipPathTextArea = new JTextArea();
		unzipPathTextArea.setWrapStyleWord(true);
		unzipPathTextArea.setLineWrap(true);
		unzipPathTextArea.setEditable(false);
		unzipPathTextArea.setBounds(190, 57, 224, 24);
		frmWinzip.getContentPane().add(unzipPathTextArea);
		
		statusTextArea = new JTextArea();
		statusTextArea.setEditable(false);
		statusTextArea.setBounds(0, 10, 437, 117);
		frmWinzip.getContentPane().add(statusTextArea);
		
		JLabel zipPathLabel = new JLabel("\u538B\u7F29\u5305\u8DEF\u5F84");
		zipPathLabel.setBounds(10, 23, 95, 24);
		frmWinzip.getContentPane().add(zipPathLabel);
		
		JLabel unzipPathLabel = new JLabel("\u89E3\u538B\u7F29\u8DEF\u5F84");
		unzipPathLabel.setBounds(10, 57, 95, 24);
		frmWinzip.getContentPane().add(unzipPathLabel);

		JButton zipPathButton = new JButton("\u9009\u62E9");
		zipPathButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				statusTextArea.setText("");
				zipPathTextArea.setText("");
				// 显示打开的文件对话框
				JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.dir")));
				JFrame frmIpa = new JFrame();
				jfc.showSaveDialog(frmIpa);
				try
				{
					// 使用文件类获取选择器选择的文件
					File file = jfc.getSelectedFile();
					String filepath = file.getPath();
					zipPathTextArea.append(filepath);
				}
				catch (Exception e2)
				{
					JPanel panel3 = new JPanel();
					JOptionPane.showMessageDialog(panel3, "没有选中任何文件", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		zipPathButton.setBounds(475, 23, 93, 23);
		frmWinzip.getContentPane().add(zipPathButton);
		
		JButton unzipPathButton = new JButton("\u9009\u62E9");
		unzipPathButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				statusTextArea.setText("");
				unzipPathTextArea.setText("");
				// 显示打开的文件对话框
				JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.dir")));
				//设置只能选择目录 默认只能单选
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				JFrame frmIpa = new JFrame();
				jfc.showOpenDialog(frmIpa);
				try
				{
					// 使用文件类获取选择器选择的文件
					File file = jfc.getSelectedFile();
					String filepath = file.getPath();
					unzipPathTextArea.append(filepath);
				}
				catch (Exception e2)
				{
					JPanel panel3 = new JPanel();
					JOptionPane.showMessageDialog(panel3, "没有选中任何文件", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		unzipPathButton.setBounds(475, 58, 93, 23);
		frmWinzip.getContentPane().add(unzipPathButton);
		
		//压缩按钮事件
		JButton zipButton = new JButton("\u538B\u7F29");
		zipButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// 显示打开的文件对话框 默认打开当前路径
				JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.dir")));
				//设置可以选择目录和文件
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				//设置一次可选多个文件或目录
				jfc.setMultiSelectionEnabled(true);
				JFrame frmIpa = new JFrame();
				jfc.showSaveDialog(frmIpa);
				
				entryPaths = new HashSet<String>();
				String zfilePath = "temp.zip";
				if( !zipPathTextArea.getText().equals("") )
					zfilePath = zipPathTextArea.getText();
				//判断文件存在性
				File zfile = new File(zfilePath);
				//如果存在就先删除
				if( zfile.exists() )
				{
					zfile.delete();
				}
				//创建.zip文件 准备压缩
				try
				{

				zfile.createNewFile();
				zos = new ZipOutputStream(new FileOutputStream(zfile));
				
				for( File file : jfc.getSelectedFiles() )
				{
					//判断文件存在性
					//文件存在才压缩 否则提示
					if( file.exists() )
					{
						parentPath = file.getParent() + "\\"; //用于去掉目录前缀
						//开始压缩
						readAndZipWithTrim(file);
						statusTextArea.append("压缩完毕！\n");
					}
					else 
					{
						statusTextArea.append("该文件或目录不存在！\n");
					}
				}
				//}
				
				zos.close();
				entryPaths.clear();
				statusTextArea.append("压缩结束！\n");
				zipPathTextArea.setText("");
				unzipPathTextArea.setText("");
				
				}
				catch (IOException ioe) 
				{
					ioe.printStackTrace();
				}
			}
		});
		zipButton.setBounds(118, 91, 95, 41);
		frmWinzip.getContentPane().add(zipButton);
		
		JButton unzipButton = new JButton("\u89E3\u538B\u7F29");
		unzipButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					//将压缩包路径所指定的压缩文件解压缩到解压缩路径下
					unzipFileWithPath(zipPathTextArea.getText(),unzipPathTextArea.getText());
					zipPathTextArea.setText("");
					unzipPathTextArea.setText("");
				} 
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		unzipButton.setBounds(319, 91, 95, 41);
		frmWinzip.getContentPane().add(unzipButton);
		
		JScrollPane scrollPane = new JScrollPane(statusTextArea);
		scrollPane.setBounds(85, 154, 437, 185);
		frmWinzip.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane(zipPathTextArea);
		scrollPane_1.setBounds(85, 23, 380, 24);
		frmWinzip.getContentPane().add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane(unzipPathTextArea);
		scrollPane_2.setBounds(85, 57, 380, 24);
		frmWinzip.getContentPane().add(scrollPane_2);
	}
	
	//压缩file所指的文件或目录 去掉目录前缀
	public static void readAndZipWithTrim(File file) throws IOException
	{
		//如果是文件就直接压缩
		if( file.isFile() )
		{
			zipFileWithTrim(file.getPath());
		}
		//如果是目录就分析目录结构并压缩目录中的文件
		else 
		{
			for(String childFile : file.list())
			{
				readAndZipWithTrim(new File(file.getPath() + "\\\\" +childFile));
			}
		}
	}

	//向通过zos压缩文件zipfile 去掉目录前缀
	public static void zipFileWithTrim(String path) throws IOException
	{
		int c;
		statusTextArea.append("压缩文件： " + path + "\n");
		//FileInputStream对象不能以路径名作为参数构造否则会拒绝访问
		FileInputStream fin = new FileInputStream(path);
		String entryPath = path.replace(parentPath, "");
		ZipEntry zipEntry = new ZipEntry(entryPath);
		//如果zip里没有文件 就直接压缩
		//判断是否有重复的文件
		if( entryPaths.contains(entryPath) )
		{
			statusTextArea.append("该文件已存在，不再压缩\n");
		}
		else 
		{
			entryPaths.add(entryPath);
			zos.putNextEntry(zipEntry);
			while ((c = fin.read()) != -1)
				zos.write(c);
		}
		
		fin.close();
	}
	
	//解压缩zip文件
	public static void unzipFileWithPath(String zipPath,String unzipPath) throws IOException
	{
		statusTextArea.append("解压缩文件\n");
		File zipFile = new File(zipPath);
		if( !zipFile.exists() )
		{
			statusTextArea.append("该压缩文件不存在！\n");
			return;
		}
		else 
		{
			int c;
			ZipInputStream zis =new ZipInputStream(new FileInputStream(zipFile));  
			ZipEntry entry;
			while((entry = zis.getNextEntry()) != null) 
			{
			   statusTextArea.append("解压缩文件：" + entry.getName() + "\n");
			   File file = new File(unzipPath + "\\" + entry.getName());
			   if( file.exists() )
			   {
				   file.delete();
			   }
			   else
			   {
				   //创建该文件的父目录 之后创建文件
				   new File(file.getParent()).mkdirs();
				   file.createNewFile();
			   }
			    
			   FileOutputStream fos =new  FileOutputStream(file);
			   while((c = zis.read()) != -1)  
			             fos.write(c); 
			   fos.close();
			   
			}    
			zis.close();
			statusTextArea.append("解压缩完毕！\n");
		}
		 
	}
}
