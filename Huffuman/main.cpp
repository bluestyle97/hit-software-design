#include "huffuman.h"

using namespace std;

int main()
{
	HuffumanTree ht;
	int k;
	string name;
	char choice = ' ';

	cout << " ------------------------------------ " << endl;
	cout << "|      欢迎使用K叉哈夫曼树系统       |" << endl;
	cout << " ------------------------------------ " << endl;
	while (choice != '0')
	{
		cout << " ------------------------------------ " << endl;
		cout << "|           1.建立哈夫曼树           |" << endl;
		cout << "|           2.压缩文件               |" << endl;
		cout << "|           3.解压文件               |" << endl;
		cout << "|           4.打印编码               |" << endl;
		cout << "|           5.打印哈夫曼树           |" << endl;
		cout << "|           6.搜索最佳分支           |" << endl;
		cout << "|           0.退出当前菜单           |" << endl;
		cout << " ------------------------------------ " << endl;
		cout << "请选择功能：";
		cin >> choice;
		switch (choice)
		{
		case '1':
			cout << "请输入哈夫曼树分支数k：";
			cin >> k;
			ht.set_branch(k);
			cout << "请输入文件名（无需输入拓展名）：";
			cin >> name;
			ht.create_tree(name);
			cout << "哈夫曼树建立完成！" << endl;
			break;
		case '2':
			if (ht.empty())
			{
				cout << "请先建立哈夫曼树！" << endl;
				break;
			}
			ht.compress(name);
			cout << "文件压缩完成，压缩文件名为" << name + ".dat" << endl;
			break;
		case '3':
			if (ht.empty())
			{
				cout << "请先建立哈夫曼树！" << endl;
				break;
			}
			ht.decompress(name);
			cout << "文件解压完成，解压文件名为" << name + "_decompress.txt" << endl;
			break;
		case '4':
			if (ht.empty())
			{
				cout << "请先建立哈夫曼树！" << endl;
				break;
			}
			ht.print_code();
			break;
		case '5':
			if (ht.empty())
			{
				cout << "请先建立哈夫曼树！" << endl;
				break;
			}
			ht.print_tree();
			break;
		case '0':
			cout << " ------------------------------------ " << endl;
			cout << "|      感谢使用K叉哈夫曼树系统       |" << endl;
			cout << " ------------------------------------ " << endl;
			break;
		case '6':
			if (name.empty())
			{
				cout << "请输入文件名（无需输入拓展名）：";
				cin >> name;
			}
			cout << "哈夫曼树最佳分支数为：" << ht.find_bestbranch(name) << endl;
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