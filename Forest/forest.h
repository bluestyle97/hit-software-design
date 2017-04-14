/*
* Copyright(c) 2017
* Xu Jaile
*
* This file was created on March 25th, and last modified on April 6th.
* It is the second assignment of the course "The Practice of software
* designing and programing" of Harbin Institute of Technology.
*
* This file contents the declarations of five classes.
*/
#ifndef FOREST_H
#define FOREST_H

#include <iostream>
#include <sstream>
#include <string>
#include <vector>
#include <queue>
#include <stdexcept>

class BinaryTreeNode;
class BinaryTree;
class TreeNode;
class Tree;
class Forest;

using BTNode = BinaryTreeNode *;
using TNode = TreeNode *;
using T = Tree *;
using element_type = char;

/* Binary tree node class. */
class BinaryTreeNode
{
public:
	element_type element;		//element
	BinaryTreeNode *lchild;		//left child
	BinaryTreeNode *rchild;		//right child

	// constructors
	BinaryTreeNode() = default;
	BinaryTreeNode(element_type e, BTNode l = NULL, BTNode r = NULL) : 
		element(e), lchild(l), rchild(r) {}
};

/* Binary tree class. */
class BinaryTree
{
public:
	// constructors
	BinaryTree() : root(NULL) {}
	BinaryTree(BTNode t) : root(t) {};
	BinaryTree(BinaryTree & bt) : root(bt.get_root()) {}

	// methods
	/* Attain the root of binary tree which is a private member. */
	BTNode & get_root() { return root; }

	/* Build a binary tree with its pre-order and in-order sequences. */
	void create_btree(const std::string &, const std::string &);

	/* Transform a binary tree to a forest. */
	Forest to_forest();

	/* Transform a binary tree to a tree. */
	Tree to_tree();

	/* Judge whether a binary tree is empty. */
	bool empty() const { return root == NULL; }

	/* Pre-order traverse a binary tree. */
	void pre_order() const { pre_order(root); }

	/* In-order traverse a binary tree. */
	void in_order() const { in_order(root); }

	/* Post-order traverse a binary tree. */
	void post_order() const { post_order(root); }

	/* Level-order traverse a binary tree. */
	void level_order() const { level_order(root); }
private:
	BTNode root;		// the root of a binary tree

	/* Private functions, invoked by their homonymous public functions. */
	void create_btree(BTNode &, const std::string &, const std::string &);
	TNode to_tree(BTNode &);

	void pre_order(const BTNode &) const;
	void in_order(const BTNode &) const;
	void post_order(const BTNode &) const;
	void level_order(const BTNode &) const;
};

/* Tree node class. */
class TreeNode
{
public:
	element_type element;		// element
	TNode first_child;		// first child
	TNode right_sibling;		// right sibling

	// constructors
	TreeNode() = default;
	TreeNode(element_type e, TNode f = NULL, TNode r = NULL) : element(e),
		first_child(f), right_sibling(r) {};
};

/* Tree class. */
class Tree
{
public:
	// constructors
	Tree() : root(NULL) {}
	Tree(TNode t) : root(t) {}
	Tree(Tree & t) : root(t.get_root()) {}

	// methods
	/* Attain the root of a tree which is a private member. */
	TNode & get_root() { return root; }

	/* Build a tree with its pre-order and post-order sequences. */
	void create_tree(const std::string &, const std::string &);

	/* Transform a tree to a binary tree. */
	BinaryTree to_binarytree();

	/* Judge whether the a tree is empty. */
	bool empty() const { return root == NULL; }

	/* Pre-order traverse a tree. */
	void pre_order() const { pre_order(root); }

	/* Post-order traverse a tree. */
	void post_order() const { post_order(root); }

	/* Level-order traverse a tree. */
	void level_order() const { level_order(root); }
private:
	TNode root;		// the root of a tree

	/* Private methods, invoked by their homonymous public functions. */
	BTNode to_binarytree(TNode &);
	TNode from_binarytree(BTNode &);

	void pre_order(const TNode &) const;
	void post_order(const TNode &) const;
	void level_order(const TNode &) const;
};

/* Forest class. */
class Forest
{
public:
	// constructors
	Forest() = default;
	Forest(const std::vector<T> & v) : forest(v) {}

	// methods
	/* Build a forest.*/
	void create_forest(int size = 0, std::istream & is = std::cin);
	
	/* Transform a forest to a binary tree. */
	BinaryTree to_binarytree();

	/* Judge whether a forest is empty. */
	bool empty() const { return forest.empty(); }

	/* Measure the forest's size, which is equal to the number of trees.*/
	size_t size() const { return forest.size(); }

	/* Pre-order traverse a forest. */
	void pre_order() const;

	/* Post-order traverse a forest. */
	void post_order() const;

	/* Level-order traverse a forest. */
	void level_order() const;
private:
	// Use a vector to store the trees of forest.
	std::vector<T> forest;
};

#endif