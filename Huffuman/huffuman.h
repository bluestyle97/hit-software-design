/*
* Copyright (c) 2017
* Xu Jiale
*
* This file was created on March 28th, and last modified on March 28th.
* It is the second assignment of the course "The practice of software
* designing and programing." of Harbin Institute of Technology.
*
* This file contents the defination of data structure "huffuman tree".
*/
#ifndef HUFFUMAN_H
#define HUFFUMAN_H

#include <iostream>
#include <sstream>
#include <fstream>
#include <vector>
#include <map>
#include <string>
#include <stack>
#include <queue>
#include <functional>
#include <algorithm>
#include <cstdio>
#include <Windows.h>

class HuffumanNode;
class HuffumanTree;

using HNode = HuffumanNode *;

/* Huffuman tree node class. */
class HuffumanNode
{
public:
	char element;		// element
	int weight;		// weight
	int parent;		// cursor of parent
	std::vector<int> child;		// cursors of children

	// constructor
	HuffumanNode(char e = '\0', int w = 0, int p = -1) : element(e), 
		weight(w), parent(p) {}
};

/* Huffuman tree class. */
class HuffumanTree
{
public:
	std::map<char, std::string> code;		// huffuman code

	// constructor
	HuffumanTree(const int branch = 2) : k_branch(branch), k_leaf(0) {}

	// methods
	/* Judge whether the huffuman tree is empty. */
	bool empty() const { return tree.empty(); }

	/* Set up the number of each node's branches */
	void set_branch(const int branch) { k_branch = branch; }

	/* Build a huffuman tree by reading a file. */
	void create_tree(const std::string &);

	/* Compress a file by huffuman code. */
	void compress(const std::string &);

	/* Decompress a binary file by huffuman code. */
	void decompress(const std::string &);

	/* Find the best branch number which makes the compress rate highest. */
	const int find_bestbranch(const std::string &);

	/* Print the huffuman tree. */
	void print_tree() const;

	/* Print the huffuman code. */
	void print_code() const;
private:
	int k_branch;		// the branch number of each node
	int k_leaf;		// the number of leaf nodes
	int k_character;		// will be used while reading a file
	std::vector<HNode> tree;	// huffuman tree, stored by a vector
	// minimum heap, will be used while merging huffuman nodes
	std::priority_queue<int, std::vector<int>, std::greater<int>> heap;	

	/* Invoked by public function "create_tree", read characters from a 
	 * txt file. */
	void read(const std::string &);

	/* Invoked by public function "create_tree", merge nodes to create 
	 * a huffuman tree. */
	void merge_node();

	/* Invoked by public function "create_tree", encode each character 
	 * by the huffuman tree and store them in map "code". */
	void encode();

	/* Invoked by public methods "compress" and "decompress", calculate 
	 * how many bits a code number should have. */
	int get_bits() const;

	/* Invoked by public method "decompress", get an unsigned char to 
	 * work as a reader while decompressing. */
	unsigned char get_reader() const;
};

#endif