/*
 * Copyright (c) 2017
 * Xu Jiale
 *
 * This file was created on march 9th, and last modified on March 23rd.
 * It is the first assignment of the course "The practice of software
 * designing and programing" of Harbin Institute of Technology.
 *
 * This file is used to test the priorityqueue defined in "priority-
 * queue.h".
 */

#include "priorityqueue.h"

using namespace std;

int main()
{
	PriorityQueue<int> queue;
	char choice = ' ';
	string fileName;
	int element;
	cout << " ---------------------------------- " << endl;
	cout << "|       欢迎使用优先队列系统       |" << endl;
	cout << " ---------------------------------- " << endl;
	while (choice != '6')
	{
		cout << " ---------------------------------- " << endl;
		cout << "|         1.打开文件               |" << endl;
		cout << "|         2.插入元素               |" << endl;
		cout << "|         3.寻找最小元素           |" << endl;
		cout << "|         4.删除最小元素           |" << endl;
		cout << "|         5.打印优先队列           |" << endl;
		cout << "|         6.退出                   |" << endl;
		cout << " ---------------------------------- " << endl;
		cout << "请选择功能：";
		cin >> choice;
		switch (choice)
		{
		case '1':
			cout << "请输入文件名：";
			cin >> fileName;
			queue.openFile(fileName);
			cout << "优先队列建立成功！" << endl;
			break;
		case '2':
			cout << "请输入待插入元素：";
			cin >> element;
			queue = queue + element;
			cout << "插入成功！" << endl;
			break;
		case '3':
			cout << "优先队列的最小元素为：" << queue.findMin() << endl;
			break;
		case '4':
			queue.deleteMin();
			cout << "已删除最小元素！" << endl;
			break;
		case '5':
			cout << "优先队列元素：" << queue;
			break;
		case '6':
			cout << " ---------------------------------- " << endl;
			cout << "|       感谢使用优先队列系统       |" << endl;
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