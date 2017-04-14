/*
* Copyright(c) 2017
* Xu Jaile
*
* This file was created on March 25th, and last modified on April 6th.
* It is the second assignment of the course "The Practice of software
* designing and programing" of Harbin Institute of Technology.
*
* This file contents the definations of the functions of the classes 
* declared in "forest.h".
*/
#include "forest.h"

/* Build a binary tree with its pre-order and in-order sequences. */
void BinaryTree::create_btree(const std::string & preStr,
	const std::string & inStr)
{
	create_btree(root, preStr, inStr);
}

/* Procedure of building a binary tree with its pre-order and in-order 
 * sequences. */
void BinaryTree::create_btree(BTNode & t, const std::string
	& preStr, const std::string & inStr)
{
	if (preStr.size() == 0)
	{
		t = NULL;
		return;
	}
	t = new BinaryTreeNode(preStr[0]);
	int index = inStr.find(preStr[0]);
	int left_length = index;
	int right_length = preStr.size() - index - 1;
	if (left_length > 0)
		create_btree(t->lchild, preStr.substr(1, left_length),
			inStr.substr(0, left_length));
	if (right_length > 0)
		create_btree(t->rchild, preStr.substr(1 + left_length, right_length),
			inStr.substr(1 + left_length, right_length));
}

/* Transform a binary tree to a forest. */
Forest BinaryTree::to_forest()
{
	std::vector<T> trees;
	std::vector<BTNode> nodes;
	BTNode t = root;
	// Push all the right-branch nodes into a vector.
	while (t != NULL)
	{
		nodes.push_back(t);
		t = t->rchild;
	}
	// Make every right-branch node's right child empty, because they will 
	// be the transformed trees' roots. Then transform every binary tree to
	// tree, and push every tree into a vector.
	for (auto it = nodes.begin(); it != nodes.end(); ++it)
	{
		BTNode n = (*it);
		n->rchild = NULL;
		T tree = new Tree(to_tree(n));
		trees.push_back(tree);
	}
	// Construct a forest with the vector and return it.
	Forest ft(trees);
	return ft;
}

/* Transform a binary tree to a tree. */
Tree BinaryTree::to_tree()
{
	// Judge whether the right child of the binary tree's root is empty.
	try 
	{
		if (root->rchild != NULL)
			throw std::runtime_error("The root\'s right child is\'t NULL");
	}
	catch (std::runtime_error err) 
	{
		std::cout << err.what() << std::endl;
		char choice;
		std::cout << "Make the root\'s right child NULL?(y or n):";
		std::cin >> choice;
		if (choice == 'y')
			root->rchild = NULL;
		else
			return NULL;
	}
	// Invoke the private function "to_tree" and attain the transformed 
	// tree's root.
	TNode n = to_tree(root);
	// Construct a new tree with the root and return it.
	Tree t(n);
	return t;
}

/* Transform a binary tree into a tree recursively. */
TNode BinaryTree::to_tree(BTNode & t)
{
	if (t == NULL)
		return NULL;
	// The "first child" of a tree node is equal to the "left child" of a 
	// binary tree node, and the "right sibling" of a tree node is equal 
	// to the "right child" of a binary tree node. So we can recursively 
	// transform a binary tree into a tree easily.
	TNode node = new TreeNode(t->element);
	node->first_child = to_tree(t->lchild);
	node->right_sibling = to_tree(t->rchild);
	return node;
}

/* Pre-order traverse a binary tree. */
void BinaryTree::pre_order(const BTNode & t) const
{
	if (t == NULL)
		return;
	std::cout << t->element << " ";
	pre_order(t->lchild);
	pre_order(t->rchild);
}

/* In-order traverse a binary tree. */
void BinaryTree::in_order(const BTNode & t) const
{
	if (t == NULL)
		return;
	in_order(t->lchild);
	std::cout << t->element << " ";
	in_order(t->rchild);
}

/* Post-order traverse a binary tree. */
void BinaryTree::post_order(const BTNode & t) const
{
	if (t == NULL)
		return;
	post_order(t->lchild);
	post_order(t->rchild);
	std::cout << t->element << " ";
}

/* Level-order traverse a binary tree. */
void BinaryTree::level_order(const BTNode & t) const
{
	if (t == NULL)
		return;
	std::queue<BTNode> q;
	q.push(t);
	while (!q.empty())
	{
		std::cout << q.front()->element << " ";
		if (q.front()->lchild != NULL)
			q.push(q.front()->lchild);
		if (q.front()->rchild != NULL)
			q.push(q.front()->rchild);
		q.pop();
	}
}

/* Build a tree with its pre-order and post-order sequences. */
void Tree::create_tree(const std::string & preStr, const std::string & postStr)
{
	BinaryTree bt;
	bt.create_btree(preStr, postStr);
	BTNode n = bt.get_root();
	root = from_binarytree(n);
}

/* Build a tree from a binary tree. */
TNode Tree::from_binarytree(BTNode & t)
{
	if (t == NULL)
		return NULL;
	TNode node = new TreeNode(t->element);
	node->first_child = from_binarytree(t->lchild);
	node->right_sibling = from_binarytree(t->rchild);
	return node;
}

/* Transform a tree to a binary tree. */
BinaryTree Tree::to_binarytree()
{
	// Invoke private function "to_binarytree".
	BTNode t = to_binarytree(root);
	// Construct a new binary tree and return it.
	BinaryTree bt(t);
	return bt;
}

/* Transform a tree into a binary tree recursively. */
BTNode Tree::to_binarytree(TNode & t)
{
	if (t == NULL)
		return NULL;
	// The "left child" of a binary tree node is equal to the "first child" 
	// of a tree node, and the "right child" of a binary tree node is equal 
	// to the "right sibling" of a tree node. So we can recursively 
	// transform a tree into a binary tree easily.
	BTNode node = new BinaryTreeNode(t->element);
	node->lchild = to_binarytree(t->first_child);
	node->rchild = to_binarytree(t->right_sibling);
	return node;
}

/* Pre-order traverse a tree. */
void Tree::pre_order(const TNode & t) const
{
	if (t == NULL)
		return;
	std::cout << t->element << " ";
	pre_order(t->first_child);
	pre_order(t->right_sibling);
}

/* Post-order traverse a tree. */
void Tree::post_order(const TNode & t) const
{
	if (t == NULL)
		return;
	post_order(t->first_child);
	std::cout << t->element << " ";
	post_order(t->right_sibling);
}

/* Level-order traverse a tree. */
void Tree::level_order(const TNode & t) const
{
	std::queue<TNode> q;
	q.push(t);
	while (!q.empty())
	{
		TNode n = q.front();
		while (n != NULL)
		{
			std::cout << n->element << " ";
			if (n->first_child != NULL)
				q.push(n->first_child);
			n = n->right_sibling;
		}
		q.pop();
	}
}

/* Build a forest by each tree's pre-order and post-order sequences. */
void Forest::create_forest(int size, std::istream & is)
{
	std::string preStr, postStr;
	if (size == 0)
	{
		std::cout << "请输入各树的先序和后序序列：" << std::endl;
		while (is >> preStr >> postStr)
		{
			try 
			{
				if (is.fail())
					throw std::runtime_error("Input error!");
				if (preStr.size() != postStr.size())
					throw std::invalid_argument("Invalid input!");
			}
			catch (std::runtime_error err)
			{
				std::cout << err.what() << std::endl;
				is.clear();
				continue;
			}
			catch (std::invalid_argument err)
			{
				std::cout << err.what() << std::endl;
				is.clear();
				std::cout << "Please input again:" << std::endl;
				continue;
			}
			T t = new Tree;
			t->create_tree(preStr, postStr);
			forest.push_back(t);
		}
		is.clear();
	}
	else
	{
		for (int i = 0; i < size; ++i)
		{
			try
			{
				std::cout << "请输入第" << i + 1 << "棵树的先序和后序序列：";
				is >> preStr >> postStr;
				if (is.fail())
					throw std::runtime_error("Input error!");
				if (preStr.size() != postStr.size())
					throw std::invalid_argument("Invalid input!");
			}
			catch (std::runtime_error err)
			{
				std::cout << err.what() << std::endl;
				is.clear();
				--i;
				continue;
			}
			catch (std::invalid_argument err)
			{
				std::cout << err.what() << std::endl;
				is.clear();
				--i;
				std::cout << "Please input again:" << std::endl;
				continue;
			}
			T t = new Tree;
			t->create_tree(preStr, postStr);
			forest.push_back(t);
		}
	}
}

/* Transform a forest to a binary tree. */
BinaryTree Forest::to_binarytree()
{
	/* Transform each tree into binary tree */
	std::vector<BinaryTree> btrees;
	for (auto it = forest.begin(); it != forest.end(); ++it)
		btrees.push_back((*it)->to_binarytree());

	/* Merge the binary trees. */
	for (auto it = btrees.begin(); it != btrees.end() - 1; ++it)
		((*it).get_root())->rchild = (*(it + 1)).get_root();
	auto it = btrees.end() - 1;
	((*it).get_root())->rchild = NULL;

	BTNode t = btrees[0].get_root();
	BinaryTree bt(t);
	return bt;
}

/* Pre-order traverse a forest. */
void Forest::pre_order() const
{
	for (auto it = forest.begin(); it != forest.end(); ++it)
	{
		(*it)->pre_order();
		std::cout << std::endl;
	}
}

/* Post-order traverse a forest. */
void Forest::post_order() const
{
	for (auto it = forest.begin(); it != forest.end(); ++it)
	{
		(*it)->post_order();
		std::cout << std::endl;
	}
}

/* Level-order traverse a forest. */
void Forest::level_order() const
{
	for (auto it = forest.begin(); it != forest.end(); ++it)
	{
		(*it)->level_order();
		std::cout << std::endl;
	}
}
