/*
 * Copyright (c) 2017
 * Xu Jiale
 *
 * This file was created on March 3rd, and last modified on March 23rd.
 * It's the first assignment of the course "The practice of software
 * designing and programming" of Harbin Institute of Technology.
 *
 * This file is used to test the skiplist defined in file "skiplist.h".
 */

#include "skiplist.h"

using namespace std;

int main()
{
	SkipList<int, string> list;
	int key;
	string value;
	string fileName;
	char choice = ' ';

	cout << " ---------------------------------- " << endl;
	cout << "|         欢迎使用跳表系统         |" << endl;
	cout << " ---------------------------------- " << endl;
	while (choice != '6')
	{
		cout << " ---------------------------------- " << endl;
		cout << "|            1.打开文件            |" << endl;
		cout << "|            2.插入数据            |" << endl;
		cout << "|            3.查找数据            |" << endl;
		cout << "|            4.删除数据            |" << endl;
		cout << "|            5.打印跳表            |" << endl;
		cout << "|            6.退出                |" << endl;
		cout << " ---------------------------------- " << endl;
		cout << "请选择功能：";
		cin >> choice;
		switch (choice)
		{
		case '1':
			cout << "请输入文件名：";
			cin >> fileName;
			list.openFile(fileName);
			cout << "跳表构建成功！" << endl;
			break;
		case '2':
			cout << "请输入您想插入的数据：";
			cin >> key >> value;
			if (list.insert(key, value))
				cout << "插入成功！" << endl;
			else
				cout << "插入失败！" << endl;
			break;
		case '3':
			cout << "请输入您想查找的关键字：";
			cin >> key;
			value = list.search(key);
			if (!value.empty())
				cout << "查找成功！关键字对应的值为："<< value << endl;
			else
				cout << "查找失败！" << endl;
			break;
		case '4':
			cout << "请输入您想删除的关键字：";
			cin >> key;
			if (list.remove(key))
				cout << "删除成功！" << endl;
			else
				cout << "删除失败！" << endl;
			break;
		case '5':
			cout << "跳表中的元素有：" << endl;
			cout << list;
			break;
		case '6':
			cout << " ---------------------------------- " << endl;
			cout << "|         感谢使用跳表系统         |" << endl;
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