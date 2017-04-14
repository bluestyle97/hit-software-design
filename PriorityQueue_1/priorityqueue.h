/*
 * Copyright (c) 2017
 * Xu Jiale
 *
 * This file was created on march 9th, and last modified on March 23rd.
 * It is the first assignment of the course "The practice of software 
 * designing and programing" of Harbin Institute of Technology.
 *
 * This file is the defination of data structure "priorityqueue" using
 * minimum heap.
 */

#ifndef PRIORITYQUEUE_H
#define PRIORITYQUEUE_H

#include <iostream>
#include <string>
#include <fstream>
#define MAX_SIZE 100

template <typename elementType>
class PriorityQueue
{
private:
	elementType *heap;	 // minimum heap
	size_t capacity;	// capacity of the priorityqueue
	size_t size;	// size of the priorityqueue
public:
	// constructors
	PriorityQueue();
	PriorityQueue(int maxElements);

	// tools
	// access to private members
	elementType * getHeap() const { return heap; };	
	size_t getCapacity() const { return capacity; };
	size_t getSize() const { return size; };
	bool empty() const { return size == 0; };
	bool full() const { return capacity == size; };
	void openFile(std::string);
	void push(elementType element);
	elementType findMin() const { return heap[1]; };
	void deleteMin();
	void print() const;

	// destructor
	~PriorityQueue();

	// operators
	PriorityQueue<elementType> & operator+(elementType);

	// friends
	template <typename elementType>
	friend std::ostream & operator<<(std::ostream &, 
		PriorityQueue<elementType> &);
};

template <typename elementType>
PriorityQueue<elementType>::PriorityQueue()
{
	capacity = MAX_SIZE;
	size = 0;
	// Allocate space for the heap.
	heap = new elementType[MAX_SIZE + 1];
}

// Intialize the heap by a given size "maxElements".
template <typename elementType>
PriorityQueue<elementType>::PriorityQueue(int maxElements)
{
	int heapSize = ((maxElements + 1) < MAX_SIZE) ? 
		(maxElements + 1) : MAX_SIZE;
	capacity = maxElements;
	size = 0;
	heap = new elementType[heapSize];
}

// Open a file named "fileName" to intialize the heap.
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
		push(input);
	}
	is.close();
}

// Insert a element into the heap by "percolate up" method.
template <typename elementType>
void PriorityQueue<elementType>::push(elementType element)
{
	if (empty())
	{
		heap[0] = heap[1] = element;
		size = 1;
		return;
	}
	if (full())
		return;
	int k;
	// "percolate up"
	for (k = ++size; (heap[k / 2] > element) && (k / 2 > 0); k /= 2)
		heap[k] = heap[k / 2];
	heap[k] = element;
	heap[0] = heap[1];
}

// Delete the minimum element of the priorityqueue by "percolate down" 
// method.
template <typename elementType>
void PriorityQueue<elementType>::deleteMin()
{
	int k, child;
	elementType lastElement;
	if (empty())
		return;
	lastElement = heap[size--];
	// "percolate down"
	for (k = 1; k * 2 <= (int)size; k = child)
	{
		child = k * 2;
		if (child != size && heap[child + 1] < heap[child])
			++child;
		if (lastElement > heap[child])
			heap[k] = heap[child];
		else
			break;
	}
	heap[k] = lastElement;
}

// Print all elements in the priorityqueue.
template <typename elementType>
void PriorityQueue<elementType>::print() const
{
	for (int i = 1; i <= (int)size; i++)
		cout << heap[i] << " ";
	cout << endl;
}

template <typename elementType>
PriorityQueue<elementType>::~PriorityQueue()
{
	delete heap;
}

// Overload the operator "+".
template <typename elementType>
PriorityQueue<elementType> & PriorityQueue<elementType>::operator+
	(elementType element)
{
	push(element);
	return *this;
}

// Overload the operator "<<".
template <typename elementType>
std::ostream & operator<<(std::ostream & os, PriorityQueue<elementType> & queue)
{
	for (int i = 1; i <= (int)queue.getSize(); i++)
		os << queue.getHeap()[i] << " ";
	os << endl;
	return os;
}

#endif