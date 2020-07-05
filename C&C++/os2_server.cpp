#include<iostream>
#include<windows.h>
#include<thread>

using namespace std;

int main()
{
	char buf[256] = "";
	DWORD rLen = 0;
	DWORD wLen = 0;
	HANDLE hPipe = NULL;
	hPipe = CreateNamedPipe(
		TEXT("\\\\.\\Pipe\\pipeTest"),							//管道名
		PIPE_ACCESS_DUPLEX,										//管道类型，双向通信
		PIPE_TYPE_MESSAGE | PIPE_READMODE_MESSAGE | PIPE_WAIT,  //管道参数
		PIPE_UNLIMITED_INSTANCES,								//管道能创建的最大实例数量
		0,														//输出缓冲区长度 0表示默认
		0,														//输入缓冲区长度 0表示默认
		NMPWAIT_WAIT_FOREVER,									//超时时间,NMPWAIT_WAIT_FOREVER为不限时等待
		NULL);													//指定一个SECURITY_ATTRIBUTES结构,或者传递零值.
	if (INVALID_HANDLE_VALUE == hPipe)
		cout << "创建管道失败: " << GetLastError() << endl;
	else
	{
		cout << "这是命名管道测试程序中的服务器端" << endl;
		cout << "现在等待客户端连接..." << endl;
		if (!ConnectNamedPipe(hPipe, NULL))						//阻塞等待客户端连接。
		{
			cout << "连接失败!" << endl;
			return 1;
		}
		else
			cout << "连接成功!" << endl;
		if (!ReadFile(hPipe, buf, 256, &rLen, NULL))			//接受客户端发送数据
		{
			cout << "从客户端接收并读取数据!" << endl;
			return 2;
		}
		else
			cout << "客户端接收的数据为 ： " << buf << endl << "数据长度为 " << rLen << endl;
		char strMessage[] = "命名管道测试程序";
		WriteFile(hPipe, strMessage, sizeof(strMessage), &wLen, 0); //向客户端发送数据
		CloseHandle(hPipe);											//关闭管道句柄
	}
	system("pause");
	return 0;
}
