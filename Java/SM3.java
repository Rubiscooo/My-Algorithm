

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * SM3杂凑算法实现
 */
public class sm3 implements java.io.Serializable
{
	// 十六进制数符
	private static char[] hexDigits =
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	//每个十六进制数符的二进制
	public static String[] bins = { "0000","0001","0010","0011","0100","0101","0110","0111",
									"1000","1001","1010","1011","1100","1101","1110","1111"};
	// 初始值的十六进制
	private static final String ivHexStr = "7380166f 4914b2b9 172442d7 da8a0600 a96f30bc 163138aa e38dee4d b0fb0e4e";
	// 初始值的十进制
	private static final BigInteger IV = new BigInteger(ivHexStr.replaceAll(" ", ""), 16);
	// 常量T0-T15
	private static final Integer Tj15 = Integer.valueOf("79cc4519", 16);
	// 常量T16-T63
	private static final Integer Tj63 = Integer.valueOf("7a879d8a", 16);
	//填充用常量 将1填充到消息末尾时用 
	private static final byte[] FirstPadding =
	{ (byte) 0x80 };
	//填充用常量 用于填充0 一次能填充8个0
	private static final byte[] ZeroPadding =
	{ (byte) 0x00 };
	// 根据j返回常量Tj的值
	private static int T(int j)
	{
		if (j >= 0 && j <= 15)
		{
			return Tj15.intValue();
		} else if (j >= 16 && j <= 63)
		{
			return Tj63.intValue();
		} else
		{
			throw new RuntimeException("data invalid");
		}
	}
	// 布尔函数FF 根据j的值取不同的表达式
	private static Integer FF(Integer x, Integer y, Integer z, int j)
	{
		if (j >= 0 && j <= 15)
		{
			return Integer.valueOf(x.intValue() ^ y.intValue() ^ z.intValue());
		} else if (j >= 16 && j <= 63)
		{
			return Integer.valueOf(
					(x.intValue() & y.intValue()) | (x.intValue() & z.intValue()) | (y.intValue() & z.intValue()));
		} else
		{
			throw new RuntimeException("data invalid");
		}
	}
	// 布尔函数GG 根据j的值取不同的表达式
	private static Integer GG(Integer x, Integer y, Integer z, int j)
	{
		if (j >= 0 && j <= 15)
		{
			return Integer.valueOf(x.intValue() ^ y.intValue() ^ z.intValue());
		} else if (j >= 16 && j <= 63)
		{
			return Integer.valueOf((x.intValue() & y.intValue()) | (~x.intValue() & z.intValue()));
		} else
		{
			throw new RuntimeException("data invalid");
		}
	}
	// 置换函数P0
	private static Integer P0(Integer x)
	{
		return Integer
				.valueOf(x.intValue() ^ Integer.rotateLeft(x.intValue(), 9) ^ Integer.rotateLeft(x.intValue(), 17));
	}
	// 置换函数P1
	private static Integer P1(Integer x)
	{
		return Integer
				.valueOf(x.intValue() ^ Integer.rotateLeft(x.intValue(), 15) ^ Integer.rotateLeft(x.intValue(), 23));
	}
	// 消息填充 返回填充完毕的消息
	private static byte[] padding(byte[] source) throws IOException
	{
		// 消息m的长度要小于2^61
		if (source.length >= 0x2000000000000000l)
		// 0x2000000000000000l = 2^61
		{
			throw new RuntimeException("src data invalid.");
		}
		// source是字节数组 所以source的长度是source.length * 8
		long l = source.length * 8;
		long k = 448 - (l + 1) % 512;// 添加k个0
		if (k < 0)
		{
			k = k + 512;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(source);
		baos.write(FirstPadding);
		long i = k - 7; // 上一行已经完成了添加一个比特1和7个0
		while (i > 0)
		{
			baos.write(ZeroPadding);// 添加剩余的i个0
			i -= 8;
		}
		// 最后填充一个64位的比特串 即长度l的二进制表示
		baos.write(long2bytes(l));
		return baos.toByteArray();
	}
	// long类型转换为byte类型
	private static byte[] long2bytes(long l)
	{
		byte[] bytes = new byte[8];
		for (int i = 0; i < 8; i++)
		{
			//强制类型转换为(byte)时只保留最后一个字节
			bytes[i] = (byte) (l >>> ((7 - i) * 8));
		}
		return bytes;
	}
	//SM3哈希算法
	public static byte[] hash(byte[] source) throws IOException
	{
		byte[] m1 = padding(source); // 消息填充 字节数组source的长度为 length*8比特
		int n = m1.length / (512 / 8);// 填充后的消息按512比特进行分组能分n组
		byte[] b;
		byte[] vi = IV.toByteArray();
		byte[] vi1 = null;
		// 分组迭代 每组长度512比特 即64个字节
		for (int i = 0; i < n; i++)
		{
			b = Arrays.copyOfRange(m1, i * 64, (i + 1) * 64);
			vi1 = CF(vi, b);// CF是压缩函数
			vi = vi1;
		}
		return vi1;
	}
	// 消息拓展 及 压缩函数
	private static byte[] CF(byte[] vi, byte[] bi) throws IOException
	{
		// int 相当于字寄存器 一个字32位 vi是256位 32个字节
		int a, b, c, d, e, f, g, h;
		a = toInteger(vi, 0);
		b = toInteger(vi, 1);
		c = toInteger(vi, 2);
		d = toInteger(vi, 3);
		e = toInteger(vi, 4);
		f = toInteger(vi, 5);
		g = toInteger(vi, 6);
		h = toInteger(vi, 7);

		// 消息拓展 
		//bi是填充完之后的消息分组 长512比特 共64个字节
		int[] w = new int[68];
		int[] w1 = new int[64];
		// a)将消息分组bi划分为16个字
		for (int i = 0; i < 16; i++)
		{
			w[i] = toInteger(bi, i);
		}
		// b)FOR j=16 TO 67
		for (int j = 16; j < 68; j++)
		{
			w[j] = P1(w[j - 16] ^ w[j - 9] ^ Integer.rotateLeft(w[j - 3], 15)) ^ Integer.rotateLeft(w[j - 13], 7)
					^ w[j - 6];
		}
		// c)FOR j = 0 TO 63
		for (int j = 0; j < 64; j++)
		{
			w1[j] = w[j] ^ w[j + 4];
		}
		// 消息拓展结束

		// 压缩函数
		int ss1, ss2, tt1, tt2;
		for (int j = 0; j < 64; j++)
		{
			ss1 = Integer.rotateLeft(Integer.rotateLeft(a, 12) + e + Integer.rotateLeft(T(j), j), 7);
			ss2 = ss1 ^ Integer.rotateLeft(a, 12);
			tt1 = FF(a, b, c, j) + d + ss2 + w1[j];
			tt2 = GG(e, f, g, j) + h + ss1 + w[j];
			d = c;
			c = Integer.rotateLeft(b, 9);
			b = a;
			a = tt1;
			h = g;
			g = Integer.rotateLeft(f, 19);
			f = e;
			e = P0(tt2);
		}
		byte[] v = toByteArray(a, b, c, d, e, f, g, h);
		for (int i = 0; i < v.length; i++)
		{
			v[i] = (byte) (v[i] ^ vi[i]);
		}
		return v;
	}
	//把四个字节划分为一个字 实现是先转成16进制数符再转成int
	private static int toInteger(byte[] source, int index)
	{
		StringBuilder valueStr = new StringBuilder("");
		for (int i = 0; i < 4; i++)
		{
			//把字节的高四位转成16进制数符								   0xF0 = 1111 0000
			valueStr.append(hexDigits[(byte) ((source[index * 4 + i] & 0xF0) >> 4)]);
			//把字节的低四位转成16进制数符								   0xF0 = 0000 1111
			valueStr.append(hexDigits[(byte) (source[index * 4 + i] & 0x0F)]);
		}
		return Long.valueOf(valueStr.toString(), 16).intValue();

	}
	//把所有字全部转成字节数组 每个字32位会转换成4个字节
	private static byte[] toByteArray(int a, int b, int c, int d, int e, int f, int g, int h) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(32);
		baos.write(toByteArray(a));
		baos.write(toByteArray(b));
		baos.write(toByteArray(c));
		baos.write(toByteArray(d));
		baos.write(toByteArray(e));
		baos.write(toByteArray(f));
		baos.write(toByteArray(g));
		baos.write(toByteArray(h));
		return baos.toByteArray();
	}
	//和long2bytes类似 通过移位和强制类型转换成byte实现
	public static byte[] toByteArray(int i)
	{
		byte[] byteArray = new byte[4];
		byteArray[0] = (byte) (i >>> 24);
		byteArray[1] = (byte) ((i & 0xFFFFFF) >>> 16);
		byteArray[2] = (byte) ((i & 0xFFFF) >>> 8);
		byteArray[3] = (byte) (i & 0xFF);
		return byteArray;
	}
	//把字节转换成16进制数符
	private static String byteToHexString(byte b)
	{
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16; //取高四位
		int d2 = n % 16; //取低四位
		return "" + hexDigits[d1] + hexDigits[d2];
	}
	//把字节数组转换为16进制数
	public static String byteArrayToHexString(byte[] b)
	{
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
		{
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	
	
	public static String sm3hashcode(String str) throws IOException
	{
		
		// getBytes()获取字符串的字节形式 采用ASCII编码 byte限制了数的取值范围  byte[]以字节为单位存储
		return(sm3.byteArrayToHexString(sm3.hash(str.getBytes())));
		
	}
	
	
}