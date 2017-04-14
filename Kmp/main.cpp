/*
* Copyright (c) 2017
* Xu Jiale
*
* This file was created on march 9th, and last modified on March 23rd.
* It is the first assignment of the course "The practice of software
* designing and programing" of Harbin Institute of Technology.
*
* This file is used to test the kmp algorithm.
*/
#include "kmp.h"

using namespace std;

int main()
{
	String main;
	string patternString;
	string fileName;
	int index;
	char choice = ' ';
	clock_t start;
	clock_t finish;

	cout << " ---------------------------------- " << endl;
	cout << "|       欢迎使用字符匹配系统       |" << endl;
	cout << " ---------------------------------- " << endl;
	while (choice != '6')
	{
		cout << " ---------------------------------- " << endl;
		cout << "|         1.文件读取主串           |" << endl;
		cout << "|         2.手动输入主串           |" << endl;
		cout << "|         3.暴力匹配               |" << endl;
		cout << "|         4.KMP匹配                |" << endl;
		cout << "|         5.STL方法匹配            |" << endl;
		cout << "|         6.退出                   |" << endl;
		cout << " ---------------------------------- " << endl;
		cout << "请选择功能：";
		cin >> choice;
		switch (choice)
		{
		case '1':
			cout << "请输入文件名：";
			cin >> fileName;
			main.open_file(fileName);
			cout << "主串读取完成！" << endl;
			break;
		case '2':
			cout << "请输入主串：";
			cin >> main.mainString;
			break;
		case '3':
			cout << "请输入模式串：";
			cin >> patternString;
			start = clock();
			index = main.index_kmp(patternString);
			finish = clock();
			if (index >= 0)
				cout << "匹配成功！模式串在主串中的位置为：" << index 
					<< endl;
			else
				cout << "匹配失败！" << endl;
			cout << "用时" << 1.0 * (finish - start) / CLOCKS_PER_SEC 
				<< "s" << endl;
			break;
		case '4':
			cout << "请输入模式串：";
			cin >> patternString;
			start = clock();
			index = main.index(patternString);
			finish = clock();
			if (index >= 0)
				cout << "匹配成功！模式串在主串中的位置为：" << index 
					<< endl;
			else
				cout << "匹配失败！" << endl;
			cout << "用时" << 1.0 * (finish - start) / CLOCKS_PER_SEC 
				<< "s" << endl;
			break;
		case '5':
			cout << "请输入模式串：";
			cin >> patternString;
			start = clock();
			index = main.mainString.find(patternString);
			finish = clock();
			if (index != string::npos)
				cout << "匹配成功！模式串在主串中的位置为：" << index 
					<< endl;
			else
				cout << "匹配失败！" << endl;
			cout << "用时" << 1.0 * (finish - start) / CLOCKS_PER_SEC 
				<< "s" << endl;
			break;
		case '6':
			cout << " ---------------------------------- " << endl;
			cout << "|       感谢使用字符匹配系统       |" << endl;
			cout << " ---------------------------------- " << endl;
			break;
		default:
			cout << " ---------------------------------- " << endl;
			cout << "|    您的输入有误，请重新输入！    |" << endl;
			cout << " ---------------------------------- " << endl;
			break;
		}
		cout << endl;
	}
	system("pause");
	return 0;
}