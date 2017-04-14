/*
 * Copyright (c) 2017
 * Xu Jiale
 *
 * This file was created on march 9th, and last modified on March 23rd.
 * It is the first assignment of the course "The practice of software
 * designing and programing" of Harbin Institute of Technology.
 *
 * This file is the defination of data structure "priorityqueue" using
 * leftist heap.
 */

#ifndef PRIORITYQUEUE_H
#define PRIORITYQUEUE_H

#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>

// Template class of the leftist heap's node.
template <typename elementType>
class HeapNode
{
public:
	elementType element;	// element of the node
	HeapNode<elementType> *lchild;	// left child
	HeapNode<elementType> *rchild;	// right child
	int npl;	// null path length

	// constructors
	HeapNode() {}
	HeapNode(elementType x, HeapNode<elementType> *l = NULL,
		HeapNode<elementType> *r = NULL, int n = 0) :
		element(x), lchild(l), rchild(r), npl(n) {}
};

// Template class of leftist heap.
template <typename elementType>
class PriorityQueue
{
private:
	HeapNode<elementType> *root;	// root of the leftist heap

	// private functions
	HeapNode<elementType> *merge(HeapNode<elementType> *,
								HeapNode<elementType> *);
	HeapNode<elementType> *merge1(HeapNode<elementType> *, 
								HeapNode<elementType> *);
	void swapChildren(HeapNode<elementType> *);
	void print(HeapNode<elementType> *) const;
	void print(HeapNode<elementType> *, std::ostream &) const;
public:
	// constructors
	PriorityQueue() : root(NULL) {}
	PriorityQueue(PriorityQueue<elementType> & queue) { *this = queue; };

	// tools
	bool empty() const { return root == NULL; };
	void openFile(std::string);
	const elementType findMin() const;
	void merge(PriorityQueue<elementType> &);
	void insert(elementType);
	void deleteMin();
	void print() const;

	// destructors
	~PriorityQueue() { delete root; };

	// operators
	PriorityQueue<elementType> & operator+(PriorityQueue<elementType> &);
	PriorityQueue<elementType> & operator+(elementType);

	// friends
	template <typename elementType>
	friend std::ostream & operator<<(std::ostream &, 
		PriorityQueue<elementType> &);
	template <typename elementType>
	friend PriorityQueue<elementType> & operator+(elementType, 
		PriorityQueue<elementType> &);
};

// Open the file named "fileName" to intialize the priorityqueue.
template <typename elementType>
void PriorityQueue<elementType>::openFile(std::string fileName)
{
	std::ifstream is(fileName);
	if (!is.is_open())
		exit(EXIT_FAILURE);
	elementType input;
	while (!is.eof())
	{
		is >> input;
		insert(input);
	}
	is.close();
}

// Return the minimum element in the priorityqueue. If the priorityqueue
// is empty, return empty type.
template <typename elementType>
const elementType PriorityQueue<elementType>::findMin() const
{
	if (!empty())
		return root->element;
	return{};
}

// The driver function of "merge" operation, parameter "queue" is the 
// leftist heap to merge with.
// Invoke private function "merge".
template <typename elementType>
void PriorityQueue<elementType>::merge(PriorityQueue<elementType> & queue)
{
	// The heap can not merge with itself.
	if (this == &queue)
		return;
	root = merge(root, queue.root);
}

// The procedure of "merge" operation, parameters "h1" and "h2" are the 
// leftist heaps to be merged.
// Invoked by public function "merge" and private function "merge1".
template <typename elementType>
HeapNode<elementType> * PriorityQueue<elementType>::merge
	(HeapNode<elementType> *h1, HeapNode<elementType> *h2)
{
	// If h1 is empty, the root of the new heap should be h2.
	if (h1 == NULL)
		return h2;
	// If h2 is empty, the root of the new heap should be h1.
	if (h2 == NULL)
	    return h1;
	// If h1's element is smaller than h2's, h1 merge h2.
	if (h1->element < h2->element)
	    return merge1(h1, h2);
	// If h1's element is bigger than h2's, h2 merge h1.
	else
	    return merge1(h2, h1);
}

// The procedure of "merge" operation, parameters "h1" and "h2" are the 
// leftist heaps to be merged.
// Invoked by private function merge.
template <typename elementType>
HeapNode<elementType> * PriorityQueue<elementType>::merge1
	(HeapNode<elementType> *h1, HeapNode<elementType> *h2)
{
	// If h1 has no left child, attach h2 to it.
	if (h1->lchild == NULL)
		h1->lchild = h2;
	else
	{
		h1->rchild = merge(h1->rchild, h2);
		// If the npl of h1's left child is smaller than its right child,
		// swap the two children.
		if (h1->lchild->npl < h1->rchild->npl)
			swapChildren(h1);
		h1->npl = h1->rchild->npl + 1;
	}
	return h1;
}

// Swap the two children of a node.
template <typename elementType>
void PriorityQueue<elementType>::swapChildren(HeapNode<elementType> *node)
{
	HeapNode<elementType> *tmp = node->lchild;
	node->lchild = node->rchild;
	node->rchild = tmp;
}

// Insert a new node into the priorityqueue by "merge" operation.
template <typename elementType>
void PriorityQueue<elementType>::insert(elementType element)
{
	HeapNode<elementType> *node = new HeapNode<elementType>(element);
	root = merge(node, root);
}

// Delete the minimum element of the priorityqueue by "merge" operation.
template <typename elementType>
void PriorityQueue<elementType>::deleteMin()
{
	if (empty())
		return;
	HeapNode<elementType> *node = root;
	// Merge the children of the root node to accomplish "deletemin"
	root = merge(root->lchild, root->rchild);
	delete node;
}

// The driver function of printing all elements in the priorityqueue.
// Invoke private function print.
template <typename elementType>
void PriorityQueue<elementType>::print() const
{
	print(root);
	std::cout << std::endl;
}

// Print all elements in the priorityqueue by preorder.
// Invoked by public function "print".
template <typename elementType>
void PriorityQueue<elementType>::print
	(HeapNode<elementType> *node) const
{
	if (node == NULL)
		return;
	std::cout << node->element << " ";
	print(node->lchild);
	print(node->rchild);
}

// Overload operator "+" to merge two leftist heaps.
// Invoke public function "merge".
template <typename elementType>
PriorityQueue<elementType> & PriorityQueue<elementType>::operator+
	(PriorityQueue<elementType> &queue)
{
	merge(queue);
	return *this;
}

// Overload operator "+" to insert a new element into the priorityqueue.
// Invoke public function "insert".
template <typename elementType>
PriorityQueue<elementType> & PriorityQueue<elementType>::operator+
	(elementType element)
{
	insert(element);
	return *this;
}

// Overload operator "<<" to print all elements in the priorityqueue.
// Invoke private function "print".
template <typename elementType>
std::ostream & operator<<(std::ostream & os, 
	PriorityQueue<elementType> & queue)
{
	queue.print(queue.root, os);
	os << std::endl;
	return os;
}

// Print all elements in the priorityqueue using output stream "os"
// by preorder.
// Invoked by public function "operator<<".
template <typename elementType>
void PriorityQueue<elementType>::print
	(HeapNode<elementType> *node, std::ostream & os) const
{
	if (node == NULL)
		return;
	os << node->element << " ";
	print(node->lchild, os);
	print(node->rchild, os);
}

// Overload operator "+" to insert a new element into the priorityqueue, 
// make sure that "priorityqueue + element" and "element + priorityqueue"
// are both legal.
// Invoke public function "insert".
template <typename elementType>
PriorityQueue<elementType> & operator+
	(elementType element, PriorityQueue<elementType> & queue)
{
	queue.insert(element);
	return queue;
}

#endif