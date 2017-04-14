/*
* Copyright (c) 2017
* Xu Jiale
*
* This file was created on March 5rd, and last modified on March 23rd.
* It's the first assignment of the course "The practice of software
* designing and programming" of Harbin Institute of Technology.
*/

#ifndef KMP_H
#define KMP_H

#include <iostream>
#include <string>
#include <fstream>
#include <ctime>

class String
{
private:
	void get_next(std::string);

	int *next;		// "next" array
public:
	std::string mainString;		// the main string

	// constructors
	String() {};
	String(std::string mstr) { mainString = mstr; };

	// tools
	void open_file(std::string);
	int index(std::string);
	int index_kmp(std::string);

	// destructor
	~String() {};
};

// Open a file to intialize the main string, "fileName" is the name of
// the file to be opened.
void String::open_file(std::string fileName)
{
	std::ifstream is(fileName);
	if (!is.is_open())
		exit(EXIT_FAILURE);
	is >> mainString;
	is.close();
}

// Get the "next" array.
void String::get_next(std::string pattern)
{
	int i = 0;
	int j = -1;
	next[0] = -1;
	while (i < (int)pattern.size() - 1)
	{
		if (j == -1 || pattern[i] == pattern[j])
			next[++i] = ++j;
		else
			j = next[j];
	}
}

// The ordinary method of finding pattern string in main string.
int String::index(std::string pattern)
{
	int i = 0, j = 0;
	int cursor;
	for (int i = 0; i <= (int)mainString.size() 
		- (int)pattern.size(); i++)
	{
		cursor = i;
		while (mainString[i] == pattern[j])
		{
			++i;
			++j;
		}
		if (j == (int)pattern.size())
			return cursor;
		i = cursor;
		j = 0;
	}
	return -1;
}

int String::index_kmp(std::string pattern)
{
	// "i" is the cursor of the main string during the match, always 
	// point to the character being compared in the main string.
	int i = 0;
	// "j" is the cursor of the pattern string during the match, always 
	// point to the character being compared in the pattern string.
	int j = 0;
	// "Next" is the array "next" of the pattern string.
	next = new int[(int)pattern.size()];

	// Intialize the array "next" of the pattern string.
	get_next(pattern);
	// Loop until having reached the end of the main or the pattern 
	// string.
	while (i < (int)mainString.size() && j < (int)pattern.size())
	{
		// If j = -1, it means we should compare the next character of 
		// the main string with the head character of the pattern string, 
		// so ++i and ++j. 
		// If main[i] = main[j], it means the current  match succeed, so 
		// we should compare the next character of the main string with
		// the next character of the pattern string, so ++i and ++j.
		if (j == -1 || mainString[i] == pattern[j])
		{
			++i;
			++j;
		}
		// If the current match fail, we should compare the current 
		// character of the main string with the "Next[j]" character of 
		// the pattern string, so make j = Next[j].
		else
			j = next[j];
		// If j has reached the end of the pattern string, then the match 
		// succeed, return the index of the first character.
		if (j == (int)pattern.size())
			return (i - (int)pattern.size());
	}

	// If the match fail eventually, return -1.
	return -1;
}

#endif