#include "huffuman.h"

/* Build a huffuman tree by reading a file. */
void HuffumanTree::create_tree(const std::string & filename)
{
	std::string filename_read = filename + ".txt";

	tree.clear();
	code.clear();
	k_leaf = 0;

	read(filename_read);
	merge_node();
	encode();
}

/* Invoked by public function "create_tree", read characters from a
 * txt file. */
void HuffumanTree::read(const std::string & filename)
{
	// open the file
	std::ifstream is(filename);
	if (!is.is_open())
		exit(EXIT_FAILURE);
	char input;

	// read charactrers from the file
	while (!is.eof())
	{
		is.get(input);
		std::vector<HNode>::iterator pos;
		// Find whether the character has been read into the tree.
		pos = std::find_if(tree.begin(), tree.end(), [input](const HNode & n)
			-> bool { return n->element == input; });
		// If the character has not been read, construt a new leaf node to 
		// store it, and push the node into the huffuman tree.
		if (pos == tree.end())
		{
			HNode node = new HuffumanNode(input, 1);
			tree.push_back(node);
			++k_leaf;
		}
		// If the character has been read, raise its weight.
		else
			(*pos)->weight += 1;
	}
	// close the file
	is.close();
	// Calculate how many empty nodes should be inserted into the tree 
	// in order to ensure every not-leaf node has k_branch branches.
	int empty_nodes = (k_branch == 2) ? 0 : k_branch - 1 - (k_leaf - 
		k_branch) % (k_branch - 1);
	// Insert empty nodes into the huffuman tree.
	for (int i = 0; i < empty_nodes; ++i)
	{
		HNode n = new HuffumanNode;
		tree.push_back(n);
	}
	// Intialize the minimum heap.
	for (auto it = tree.begin(); it != tree.end(); ++it)
		heap.push((*it)->weight);
}

/* Invoked by public function "create_tree", merge nodes to create
 * a huffuman tree. */
void HuffumanTree::merge_node()
{
	// Loop until the huffuman tree has been built.
	while (heap.size() != 1)
	{
		HNode node = new HuffumanNode;
		// Find k_branch nodes from the minimum heap to merge each time.
		for (int i = 0; i < k_branch; i++)
		{
			// Find the minimum weight which has not been merged yet.
			int min_weight = heap.top();
			node->weight += min_weight;
			std::vector<HNode>::iterator pos;
			// Find the node from the current huffuman tree which has 
			// the minimum weight and has not been merged yet.
			pos = std::find_if(tree.begin(), tree.end(), [min_weight]
			(const HNode & n) -> bool { return (n->parent == -1) &&
				(n->weight == min_weight); });
			// merge nodes
			node->child.push_back((int)(pos - tree.begin()));
			(*pos)->parent = tree.size();
			heap.pop();
		}
		// Push the new node into the huffuman tree.
		tree.push_back(node);
		// Push the weight of the new node into the heap.
		heap.push(node->weight);
	}
	heap.pop();
}

/* Invoked by public function "create_tree", encode each character
 * by the huffuman tree and store them in map "code". */
void HuffumanTree::encode()
{
	// Loop until all characters have been encoded.
	for (int i = 0; i < k_leaf; ++i)
	{
		int cursor_a = i;
		int cursor_b;
		std::string char_code;
		std::stack<int> s;
		// Set out from a leaf node, head to the root, get a reversed 
		// huffuman code in the stack.
		while (tree[cursor_a]->parent != -1)
		{
			cursor_b = tree[cursor_a]->parent;
			std::vector<int>::iterator pos;
			pos = std::find(tree[cursor_b]->child.begin(),
				tree[cursor_b]->child.end(), cursor_a);
			s.push(pos - tree[cursor_b]->child.begin());
			cursor_a = cursor_b;
		}
		// Get the correct huffuman code, use char_code to store it.
		while (!s.empty())
		{
			char_code += std::to_string(s.top());
			char_code += " ";
			s.pop();
		}
		// Insert the character and its code into the map "code".
		code.insert({ tree[i]->element, char_code });
	}
}

/* Compress a file by huffuman code. */
void HuffumanTree::compress(const std::string & filename)
{
	// "bits" is the length of a number in the huffuman code, for example,
	// if the huffuman tree is 2-branch, the huffuman code only contains
	// 0 and 1, so one bit can just meet our demand, then bits = 1; but
	// if the huffuman tree is 3-branch, the huffuman tree code contains 
	// 0, 1 and 2, so two bits can only meet our demand, then bits = 2.
	int bits = get_bits();
	char ch;
	int count = 0;
	// container
	unsigned char set;
	std::string char_code;
	std::string filename_read = filename + ".txt";
	std::string filename_compress = filename + ".dat";

	// make the container empty
	set &= 0;
	k_character = 0;
	// open file
	std::ifstream is(filename_read);
	if (!is.is_open())
		exit(EXIT_FAILURE);
	std::ofstream os(filename_compress, std::ios::binary);
	if (!os.is_open())
		exit(EXIT_FAILURE);
	while (!is.eof())
	{
		// read a character
		is.get(ch);
		// Calculate the number of characters in the file.
		++k_character;
		// Get the character's code.
		char_code = code[ch];
		std::istringstream iss(char_code);
		int child_index;
		// Read the code into the container.
		while (iss >> child_index)
		{
			set <<= bits;
			count += bits;
			set |= child_index;
			if (count == 8)
			{
				// Write the container into the compressed file.
				os.write((char *)&set, sizeof(unsigned char));
				set &= 0;
				count = 0;
			}
		}
	}
	// Write the remaining bits into the compressed file.
	set <<= (8 - count);
	os.write((char *)&set, sizeof(unsigned char));
	// close file
	is.close();
	os.close();
}

/* Invoked by public methods "compress" and "decompress", calculate
* how many bits a code number should have. */
int HuffumanTree::get_bits() const
{
	if (k_branch == 2) return 1;
	else if (k_branch == 3 || k_branch == 4) return 2;
	else if (k_branch > 4 && k_branch <= 16) return 4;
	else return 8;
}

void HuffumanTree::decompress(const std::string & filename)
{
	// "bits" is the length of a number in the huffuman code, for example,
	// if the huffuman tree is 2-branch, the huffuman code only contains
	// 0 and 1, so one bit can just meet our demand, then bits = 1; but
	// if the huffuman tree is 3-branch, the huffuman tree code contains 
	// 0, 1 and 2, so two bits can only meet our demand, then bits = 2.
	int root = tree.size() - 1;
	int pos = root;		// cursor
	int bits = get_bits();
	int p_count = 0;
	unsigned char set;		// container
	unsigned reader = get_reader();
	std::string filename_compress = filename + ".dat";
	std::string filename_decompress = filename + "_decompress.txt";

	// open file
	std::ifstream is(filename_compress, std::ios::binary);
	if (!is.is_open())
		exit(EXIT_FAILURE);
	std::ofstream os(filename_decompress);
	if (!os.is_open())
		exit(EXIT_FAILURE);
	while (!is.eof())
	{
		// read a byte from the binary file to the container.
		is.read((char *)&set, sizeof(unsigned char));
		for (int i = 0; i < (8 / bits); ++i)
		{
			// Set out from the root and move down according to the code
			// read from the container, until reaching a leaf node, then 
			// write the character stored in the leaf node into the 
			// decompressed txt file.
			unsigned char set_head = set & reader;
			set_head >>= (8 - bits);
			int child_index = (int)(set_head - 0x00);
			pos = tree[pos]->child[child_index];
			set <<= bits;
			if (tree[pos]->child.empty())
			{
				os << tree[pos]->element;
				++p_count;
				// When we has write all characters contained in the 
				// original file, exit the loop and stop writing characters
				// into the decompressed txt file.
				if (p_count == k_character - 1)
					goto end_output;
				pos = root;
			}
		}
	}
end_output:
	// close file
	is.close();
	os.close();
}

/* Invoked by public method "decompress", get an unsigned char to
* work as a reader while decompressing. */
unsigned char HuffumanTree::get_reader() const
{
	unsigned char reader;
	reader &= 0;
	if (k_branch == 2) reader |= 0x80;
	else if (k_branch == 3 || k_branch == 4) reader |= 0xC0;
	else if (k_branch > 4 && k_branch <= 16) reader |= 0xF0;
	else reader |= 0xFF;
	return reader;
}

/* Print the huffuman tree. */
void HuffumanTree::print_tree() const
{
	std::cout << "The branches of the huffuman tree:" << k_branch << std::endl;
	std::cout << "The leaf_number of the huffuman tree:" << k_leaf
		<< std::endl;
	std::cout << "The size of the huffuman tree:" << tree.size()
		<< std::endl;
	for (auto it = tree.begin(); it != tree.end(); ++it)
	{
		std::cout << "index:" << it - tree.begin() << " " << "element:"
			<< (*it)->element << ", weight:" << (*it)->weight << ", parent:"
			<< (*it)->parent << ", children:";
		for (auto i = (*it)->child.begin(); i != (*it)->child.end(); ++i)
			std::cout << (*i) << " ";
		std::cout << std::endl;
	}
}

/* Print the huffuman code. */
void HuffumanTree::print_code() const
{
	std::cout << "Code numbers:" << code.size() << std::endl;
	for (auto it = code.begin(); it != code.end(); ++it)
		std::cout << (*it).first << ": " << (*it).second << std::endl;
}

/* Find the best branch number which makes the compress rate highest. */
const int HuffumanTree::find_bestbranch(const std::string & filename)
{
	if (tree.empty())
		create_tree(filename);
	std::vector<DWORD> file_size;		// size of compressed file
	std::vector<double> compress_rate;
	std::string filename_init = filename + ".txt";
	std::string filename_compress = filename + ".dat";
	const char * name = filename_compress.c_str();

	// Invoke windows API to get the size of original file.
	HANDLE fhandle = CreateFileA(filename_init.c_str(), 0, 0, 0, 
		OPEN_EXISTING, 0, 0);
	DWORD size_init = GetFileSize(fhandle, 0);

	for(int k = 2; k <= 16; ++k)
	{
		set_branch(k);
		create_tree(filename);
		compress(filename);
		// Invoke windows API to get the size of compressed file.
		HANDLE fhandle = CreateFileA(name, 0, 0, 0, OPEN_EXISTING, 0, 0);
		DWORD size = GetFileSize(fhandle, 0);
		file_size.push_back(size);
		compress_rate.push_back(1.0 * size / size_init);
	}
	// Delete the compressed file.
	remove(name);
	DWORD min_size = file_size[0];
	for (int i = 0; i < compress_rate.size(); ++i)
		std::cout << "branch " << /*(int)pow(2, i + 1)*/ i + 2 << ": file size: "
		<< file_size[i] << "Byte, compress rate: " << 100 * compress_rate[i] 
		<< "%" << std::endl;
	decltype(file_size.begin()) it;
	// Use standard algorithm to find the minimum compressed file size.
	it = std::min_element(file_size.begin(), file_size.end());
	return int(it - file_size.cbegin()) + 2;
}