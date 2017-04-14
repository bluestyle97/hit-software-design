/*
 * Copyright (c) 2017
 * Xu Jiale
 *
 * This file was created on March 25th, and last modified on March 25th.
 * It is the second assignment of the course "The protice of software
 * designing and programing" of Harbin Institute of Technology.
 * 
 * This file is used for testing the data structure "threaded binary tree"
 * define in "threadtree.h".
 */

#include "threadtree.h"

using namespace std;

int main()
{
	ThreadTree tree;
	char choice = ' ';
	string preStr;
	string inStr;

	cout << " ---------------------------------- " << endl;
	cout << "|     欢迎使用线索二叉树系统       |" << endl;
	cout << " ---------------------------------- " << endl;
	while (choice != '0')
	{
		cout << " ---------------------------------- " << endl;
		cout << "|            1.先序建树            |" << endl;
		cout << "|            2.还原建树            |" << endl;
		cout << "|            3.先序线索化          |" << endl;
		cout << "|            4.中序线索化          |" << endl;
		cout << "|            5.后序线索化          |" << endl;
		cout << "|            6.清除线索化          |" << endl;
		cout << "|            7.先序遍历            |" << endl;
		cout << "|            8.中序遍历            |" << endl;
		cout << "|            9.后序遍历            |" << endl;
		cout << "|            0.退出                |" << endl;
		cout << " ---------------------------------- " << endl;
		cout << "请选择功能：";
		cin >> choice;
		switch (choice)
		{
		case '1':
			cout << "请输入先序序列（空结点用*填充）：";
			cin >> preStr;
			tree.createTree(preStr);
			cout << "线索二叉树建立完成！" << endl;
			break;
		case '2':
			cout << "请输入先序序列：";
			cin >> preStr;
			cout << "请输入中序序列：";
			cin >> inStr;
			tree.createTree(preStr, inStr);
			cout << "线索二叉树建立完成！" << endl;
			break;
		case '3':
			tree.preThreading();
			cout << "先序线索化完成！" << endl;
			break;
		case '4':
			tree.inThreading();
			cout << "中序线索化完成！" << endl;
			break;
		case '5':
			tree.postThreading();
			cout << "后序线索化完成！" << endl;
			break;
		case '6':
			tree.cleanThread();
			cout << "清除线索化完成！" << endl;
			break;
		case '7':
			tree.preOrder();
			cout << endl << "先序遍历完成！" << endl;
			break;
		case '8':
			tree.inOrder();
			cout << endl << "中序遍历完成！" << endl;
			break;
		case '9':
			tree.postOrder();
			cout << endl << "后序遍历完成！" << endl;
			break;
		case '0':
			cout << " ---------------------------------- " << endl;
			cout << "|      感谢使用线索二叉树系统      |" << endl;
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