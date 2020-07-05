#include<iostream>
#include<windows.h>
#include<thread>

using namespace std;

int main()
{
	cout << "这是命名管道测试程序的客户端" << endl;
	char buf[256] = "";
	DWORD rLen = 0;
	DWORD wLen = 0;
	Sleep(1000);						//等待管道创建成功！
	if (!WaitNamedPipe(TEXT("\\\\.\\Pipe\\pipeTest"), NMPWAIT_WAIT_FOREVER))
	{
		cout << "connect the namedPipe failed!" << endl;
		return 1;
	}

	HANDLE hPipe = CreateFile(          //创建管道文件，即链接管道
		TEXT("\\\\.\\Pipe\\pipeTest"),	//管道名称
		GENERIC_READ | GENERIC_WRITE,   //文件模式
		0,                              //是否共享
		NULL,                           //指向一个SECURITY_ATTRIBUTES结构的指针
		OPEN_EXISTING,                  //创建参数
		FILE_ATTRIBUTE_NORMAL,          //文件属性，NORMAL为默认属性
		NULL);                          //模板创建文件的句柄

	if (INVALID_HANDLE_VALUE == hPipe)
	{
		cout << "打开通道失败!" << endl;
		return 2;
	}
	char strMessage[] = "命名管道测试程序";
	if (!WriteFile(hPipe, strMessage, sizeof(strMessage), &wLen, 0)) //向管道发送数据
	{
		cout << "向通道写数据失败!" << endl;
		return 3;
	}
	if (!ReadFile(hPipe, buf, 256, &rLen, NULL))					//读取管道数据
	{
		cout << "从通道读数据失败!" << endl;
		return 4;
	}
	else
		cout << "从服务器端接收数据 ： " << buf << endl << "数据长度为：" << rLen << endl;

	Sleep(1000);   //执行挂起一段时间
	CloseHandle(hPipe);					//关闭管道
	system("pause");
	return 0;
}
