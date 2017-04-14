/*
 * Copyright (c) 2017
 * Xu Jiale
 *
 * This file was created on March 17th, and last mofified on March 24th.
 * It is the second assignment of the course "The practice of software
 * designing and programing" of Harbin Institute of Technology.
 *
 * This file is the defination of data structure "threaded binary tree".
 */

#ifndef THREADTREE_H
#define THREADTREE_H

#include <iostream>
#include <string>
#include <sstream>
#include <stack>

/* Threaded binary tree's node class. */
class TreeNode
{
public:
	using PointerTag = enum { Link, Thread };
	using elementType = char;

	elementType element;	// element
	TreeNode *lchild;		// left child
	TreeNode *rchild;		// right child
	PointerTag ltag;		// left tag
	PointerTag rtag;		// right tag

	// constructors
	TreeNode() = default;
	TreeNode(elementType x, TreeNode *l = NULL, TreeNode *r = NULL, 
		PointerTag lt = Link, PointerTag rt = Link) : element(x), 
		lchild(l), rchild(r), ltag(lt), rtag(rt) {}
};

/* Threaded binary tree class. */
class ThreadTree
{
public:
	// "Treetag" is used to indicate the current state of threaded binary
	// tree, which can be pre-threaded, in-threaded, post-threaded or none-
	// threaded.
	using TreeTag = enum { NoneThread, PreThread, InThread, PostThread };
	using Node = TreeNode *;
	using elementType = char;

	// constructors
	ThreadTree() : root(NULL), tag(NoneThread) {}
	ThreadTree(Node t) : root(t) {}

	// methods
	/* Build a binary tree with its pre-order sequence, input "*" to replace
	 * empty leaf nodes. */
	void createTree(const std::string &);

	/* Build a binary tree with its pre-order and in-order sequences. */
	void createTree(const std::string &, const std::string &);

	/* Pre-threading a binary tree. */
	void preThreading();

	/* In-threading a binary tree. */
	void inThreading();

	/* Post-threading a binary tree. */
	void postThreading();

	/* Clean the threads of a binary tree. */
	void cleanThread();

	/* Attain threaded binary tree's root which is a private member. */
	Node getRoot() const { return root; }

	/* Judge whether threaded binary tree is empty. */
	bool empty() const { return root == NULL; }

	/* Pre-order traverse a threaded binary tree. */
	void preOrder() const;

	/* In-order traverse a threaded binary tree. */
	void inOrder() const;

	/* Post-order traverse a threaded binary tree. */
	void postOrder() const;

	// destructor
	~ThreadTree() { delete root; }
private:
	Node root;		// the root of a threaded binary tree
	TreeTag tag;		// the tag of tree's threading-state
	Node pre;		// will be used while threading

	/* Private functions, invoked by their homonymous public functions. */
	Node createTree(std::istringstream &);
	void createTree(Node &, const std::string &, const std::string &);
	void preThreading(Node &);
	void inThreading(Node &);
	void postThreading(Node &);
	void cleanThread(Node &);

	void preOrder(const Node &) const;
	void inOrder(const Node &) const;
	void postOrder(const Node &) const;
	void preOrderThread(const Node &) const;
	void inOrderThread(const Node &) const;
	void postOrderThread(const Node &) const;
};

#endif