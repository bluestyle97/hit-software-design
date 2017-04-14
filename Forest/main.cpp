/*
 * Copyright (c) 2017
 * Xu Jiale
 *
 * This file was created on March 25th, and last modified on March 28th.
 * It is the second assignment of the course "The Practice of software
 * designing and programing" of Harbin Institute of Technology.
 *
 * This file is used for testing the algorithm of the transformation 
 * between binary trees and forest.
 */

#include "forest.h"

using namespace std;

int main()
{
	BinaryTree bt;
	Tree t;
	Forest ft;
	
	char choice = ' ';
	cout << " --------------------------------------- " << endl;
	cout << "|     欢迎使用二叉树与森林转换系统      |" << endl;
	cout << " --------------------------------------- " << endl;
	while (choice != '0')
	{
		cout << " --------------------------------------- " << endl;
		cout << "|           1.初始化二叉树              |" << endl;
		cout << "|           2.初始化森林                |" << endl;
		cout << "|           3.二叉树转化为森林          |" << endl;
		cout << "|           4.森林转化为二叉树          |" << endl;
		cout << "|           5.遍历二叉树                |" << endl;
		cout << "|           6.遍历森林                  |" << endl;
		cout << "|           0.退出菜单                  |" << endl;
		cout << " --------------------------------------- " << endl;
		cout << "请选择功能：";
		cin >> choice;
		switch (choice)
		{
		case '1':
		{
			string preStr, inStr;
			cout << "请输入前序序列：";
			cin >> preStr;
			cout << "请输入中序序列：";
			cin >> inStr;
			bt.create_btree(preStr, inStr);
			cout << "二叉树建立完成！" << endl;
			break;
		}
		case '2':
			ft.create_forest();
			cout << "森林建立完成！" << endl;
			break;
		case '3':
			ft = bt.to_forest();
			cout << "成功将二叉树转化为森林！" << endl;
			break;
		case '4':
			bt = ft.to_binarytree();
			cout << "成功将森林转化为二叉树！" << endl;
			break;
		case '5':
		{
			char choice = ' ';
			while (choice != '0')
			{
				cout << "***********************************" << endl;
				cout << "+        1.先序遍历二叉树         +" << endl;
				cout << "+        2.中序遍历二叉树         +" << endl;
				cout << "+        3.后序遍历二叉树         +" << endl;
				cout << "+        4.层序遍历二叉树         +" << endl;
				cout << "+        0.退出当前菜单           +" << endl;
				cout << "***********************************" << endl;
				cout << "请选择功能：";
				cin >> choice;
				switch (choice)
				{
				case '1':
					bt.pre_order();
					cout << endl << "二叉树先序遍历完成！" << endl;
					break;
				case '2':
					bt.in_order();
					cout << endl << "二叉树中序遍历完成！" << endl;
					break;
				case '3':
					bt.post_order();
					cout << endl << "二叉树后序遍历完成！" << endl;
					break;
				case '4':
					bt.level_order();
					cout << endl << "二叉树层序遍历完成！" << endl;
					break;
				case '0':
					cout << "退出二叉树遍历！" << endl;
					break;
				default:
					cout << "您的输入有误，请重新输入！" << endl;
					break;
				}
				cout << endl;
			}
			break;
		}
		case '6':
		{
			char choice = ' ';
			while (choice != '0')
			{
				cout << "***********************************" << endl;
				cout << "+         1.先序遍历森林          +" << endl;
				cout << "+         2.后序遍历森林          +" << endl;
				cout << "+         3.层序遍历森林          +" << endl;
				cout << "+         0.退出当前菜单          +" << endl;
				cout << "***********************************" << endl;
				cout << "请选择功能：";
				cin >> choice;
				switch (choice)
				{
				case '1':
					ft.pre_order();
					cout << "森林先序遍历完成！" << endl;
					break;
				case '2':
					ft.post_order();
					cout << "森林后序遍历完成！" << endl;
					break;
				case '3':
					ft.level_order();
					cout << "森林层序遍历完成！" << endl;
					break;
				case '0':
					cout << "退出森林遍历！" << endl;
					break;
				default:
					cout << "您的输入有误，请重新输入！" << endl;
					break;
				}
				cout << endl;
			}
			break;
		}
		case '0':
			cout << " --------------------------------------- " << endl;
			cout << "|     感谢使用二叉树与森林转换系统      |" << endl;
			cout << " --------------------------------------- " << endl;
			break;
		default:
			cout << " --------------------------------------- " << endl;
			cout << "|       您的输入有误，请重新输入！      |" << endl;
			cout << " --------------------------------------- " << endl;
			break;
		}
		cout << endl;
	}
	system("pause");
	return 0;
}

/*
forest: 
ABEFJCDGHI EJFBCGHIDA
KLNOPM NOPLMK
QRST STRQ

binary tree:
ABCDEFGHIJKLM CBEDAHIGJFLMK
*/