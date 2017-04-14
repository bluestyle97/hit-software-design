/*
 * Copyright (c) 2017
 * Xu Jiale
 *
 * This file was created on March 3rd, and last modified on March 23rd.
 * It's the first assignment of the course "The practice of software 
 * designing and programming" of Harbin Institute of Technology.
 *
 * This file is the defination of data structure "skiplist".
 */

#ifndef SKIPLIST_H
#define SKIPLIST_H

#include <string>
#include <iostream>
#include <fstream>
#include <cstdlib>
#define MAX_LEVEL 20

// The template class defination of skiplist's node.
template <typename keyType, typename valueType>
class SkipNode
{
public:
	keyType key;		// the key stored in the node
	valueType value;		// the value stored in the node
	int level;		// the level on which the node lies
	SkipNode<keyType, valueType> **forward;		
										// the list of the forward nodes
};

// The template class defination of skiplist.
template <typename keyType, typename valueType>
class SkipList
{
public:
	// constructors
	SkipList();
	SkipList(int);

	// tools
	bool empty() const { return header->forward[0] == NULL; };	
									// Judge whether the skiplist is empty.
	int getLevel() const { return level; };
	SkipNode<keyType, valueType> * getHeader() const { return header; };
	void openFile(std::string);
	valueType search(keyType) const;
	bool insert(keyType, valueType);
	bool remove(keyType);
	void print() const;

	// destructor
	~SkipList();

	// friends
	template <typename keyType, typename valueType>
	friend std::ostream & operator<<
		(std::ostream &, SkipList<keyType, valueType> &);
private:
	int level;		// level of the skiplist
	SkipNode<keyType, valueType> *header;		// header of the skiplist

	int randomLevel() const;
};

template <typename keyType, typename valueType>
SkipList<keyType, valueType>::SkipList()
{
	level = 0;
	// Allocate space for the skiplist.
	header = new SkipNode<keyType, valueType>;
	header->forward = new SkipNode<keyType, valueType> *[MAX_LEVEL];
	for (int k = 0; k < MAX_LEVEL; k++)
		header->forward[k] = NULL;
}

// Intialize the skiplist with a given level number, parameter "aLevel" 
// is the given level number.
template <typename keyType, typename valueType>
SkipList<keyType, valueType>::SkipList(int aLevel)
{
	level = 0;
	// The given level shouldn't be bigger than the defined macro 
	// constant "MAX_LEVEL".
	int maxLevel = (aLevel < MAX_LEVEL) ? aLevel : MAX_LEVEL;
	// Allocate space for the skiplist.
	header = new SkipNode<keyType, valueType>;
	header->forward = new SkipNode<keyType, valueType> *[maxLevel];
	for (int k = 0; k < maxLevel; k++)
		header->forward[k] = NULL;
}

// Open a file to intialize the skiplist, parameter "fileName" is the name 
// of the file to be opened.
template <typename keyType, typename valueType>
void SkipList<keyType, valueType>::openFile(std::string fileName)
{
	std::ifstream is;
	is.open(fileName);
	//If failed to open the file, exit the program.
	if (!is.is_open())
		exit(EXIT_FAILURE);
	keyType keyInput;
	valueType valueInput;
	while (!is.eof())
	{
		is >> keyInput;
		is >> valueInput;
		this->insert(keyInput, valueInput);
	}
	is.close();
}

// Search a key in the skiplist, if found, return its value, parameter 
// "aKey" is the key to be searched.
template <typename keyType, typename valueType>
valueType SkipList<keyType, valueType>::search(keyType aKey) const
{
	SkipNode<keyType, valueType> *pointer = header;
	SkipNode<keyType, valueType> *pointerNext = NULL;

	// Search the key from the highest level of the skiplist, when find
	// a bigger key in current level, down to the lower level and 
	// continue searching, until finding aKey successfully.
	for (int k = level - 1; k >= 0; k--)
	{
		while ((pointerNext = pointer->forward[k]) 
			&& pointerNext->key <= aKey)
			pointer = pointerNext;
		// If succeed, return the value corresponding to the key.
		if (pointer != header && pointer->key == aKey)
			return pointer->value;
	}
	delete pointerNext;
	delete pointer;
	// If fail, return empty value type.
	return {};
}

// Insert a new node to the skiplist, if succeed, return true, parameters
// "aKey" and "aValue" are the contents of the node.
template <typename keyType, typename valueType>
bool SkipList<keyType, valueType>::insert(keyType aKey, valueType aValue)
{
	int nodeLevel = randomLevel();
	SkipNode<keyType, valueType> *node = new SkipNode<keyType, valueType>;
	SkipNode<keyType, valueType> *pointerNext = NULL;
	// Array "updates" is used to record the positions of every level in
	// which the new node will be inserted.
	SkipNode<keyType, valueType> **updates = 
		new SkipNode<keyType, valueType> *[nodeLevel];

	// Intialize a node to insert into the skiplist.
	node->key = aKey;
	node->value = aValue;
	node->level = nodeLevel;
	node->forward = new SkipNode<keyType, valueType> *[nodeLevel];

	for (int k = nodeLevel - 1; k >= 0; k--)
		updates[k] = header;
	for (int k = nodeLevel - 1; k >= 0; k--)
	{
		while ((pointerNext = updates[k]->forward[k]) 
			&& pointerNext->key <= aKey)
			updates[k] = pointerNext;
		// If the key has existed in the skiplist, the insertion fails.
		if (updates[k] != header && updates[k]->key == aKey)
			return false;
	}
	// Insert the node according to the array "updates".
	for (int k = nodeLevel - 1; k >= 0; k--)
	{
		node->forward[k] = updates[k]->forward[k];
		updates[k]->forward[k] = node;
	}
	// Updates the level of the skiplist.
	if (nodeLevel > level)
		level = nodeLevel;
	delete[] updates;
	// If succeed, return true.
	return true;
}

// Remove a node from the skiplist by its key, if succeed, return true,
// parameter "aKey" is the key of the node to be removed.
template <typename keyType, typename valueType>
bool SkipList<keyType, valueType>::remove(keyType aKey)
{
	SkipNode<keyType, valueType> *pointer = header;
	SkipNode<keyType, valueType> *pointerNext = NULL;
	// "nodePointer" is used to record the position of the node to be 
	// removed.
	SkipNode<keyType, valueType> *nodePointer = NULL;
	// "nodeLevel" is used to record the level of the node to be removed.
	int nodeLevel = 0;

	for (int k = level - 1; k >= 0; k--)
	{
		while ((pointerNext = pointer->forward[k]) 
			&& pointerNext->key <= aKey)
			pointer = pointerNext;
		if (pointer != header && pointer->key == aKey)
		{
			// If find the node successfully, record the position and 
			// level, then exit this loop.
			nodePointer = pointer;
			nodeLevel = k + 1;
			break;
		}
	}
	// "nodeLevel" = 0 means failing to find the key, so the removal 
	// fails, return false.
	if (nodeLevel == 0)
		return false;
	// Remove the node level by level.
	for (int k = nodeLevel - 1; k >= 0; k--)
	{
		pointer = header;
		while (pointer->forward[k] != nodePointer)
			pointer = pointer->forward[k];
		pointer->forward[k] = pointer->forward[k]->forward[k];
	}
	delete nodePointer;
	// Updates the level of the skiplist.
	if (nodeLevel == level)
		for (int k = nodeLevel - 1; k >= 0; k--)
			if (header->forward[k] != NULL)
			{
				level = k + 1;
				break;
			}
	return true;
}

// Print every node's key, value and level by list order.
template <typename keyType, typename valueType>
void SkipList<keyType, valueType>::print() const
{
	if (empty())
	{
		std::cout << "跳表为空！" << endl;
		return;
	}
	SkipNode<keyType, valueType> *pointer = header->forward[0];
	while (pointer != NULL)
	{
		std::cout << "key:" << pointer->key
			<< ", value:" << pointer->value
			<< ", level:" << pointer->level
			<< std::endl;
		pointer = pointer->forward[0];
	}
}

template <typename keyType, typename valueType>
SkipList<keyType, valueType>::~SkipList()
{
	delete[] header->forward;
	delete header;
}

// Defination of friend function "operator<<", print every node's key,
// value and level by list order.
template <typename keyType, typename valueType>
std::ostream & operator<<(std::ostream & os, 
	SkipList<keyType, valueType> & list)
{
	if (list.empty())
	{
		os << "跳表为空！" << endl;
		return os;
	}
	SkipNode<keyType, valueType> *pointer = 
		list.getHeader()->forward[0];
	while (pointer != NULL)
	{
		os << "key:" << pointer->key
			<< ", value:" << pointer->value
			<< ", level:" << pointer->level
			<< std::endl;
		pointer = pointer->forward[0];
	}
	return os;
}

// Return a random level number, invoked by the public function "insert".
template <typename keyType, typename valueType>
int SkipList<keyType, valueType>::randomLevel() const
{
	int level = 1;
	while (rand() % 2)
		++level;
	return (level < MAX_LEVEL) ? level : MAX_LEVEL;
}

#endif