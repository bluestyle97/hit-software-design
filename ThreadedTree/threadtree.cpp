#include "threadtree.h"

/* Build a binary tree with its pre-order sequence, input "*" to replace 
 * empty leaf nodes. */
void ThreadTree::createTree(const std::string & preStr)
{
	// Intialize a stringstream by the pre-order sequence.
	std::istringstream iss(preStr);
	// Invoke private function "createTree", use stringstream "iss" as a 
	// parameter.
	root = createTree(iss);
}

/* Build a binary tree recursively. */
TreeNode * ThreadTree::createTree(std::istringstream & iss)
{
	elementType x;

	iss >> x;
	if (x == '*')
		return NULL;
	Node node = new TreeNode(x);
	node->lchild = createTree(iss);
	node->rchild = createTree(iss);
	return node;
}

/* Build a binary tree with its pre-order and in-order sequences. */
void ThreadTree::createTree(const std::string & preStr,
	const std::string & inStr)
{
	// Invoke private function "createTree".
	createTree(root, preStr, inStr);
}

/* Build a binary tree by its pre-order and in-order sequences recursively. */
void ThreadTree::createTree(Node & t,
	const std::string & preStr, const std::string & inStr)
{
	if (preStr.size() == 0)
	{
		t = NULL;
		return;
	}
	t = new TreeNode(preStr[0]);
	int index = inStr.find(preStr[0]);
	int left_length = index;
	int right_length = preStr.size() - index - 1;
	if (left_length > 0)
		createTree(t->lchild, preStr.substr(1, left_length),
			inStr.substr(0, left_length));
	if (right_length > 0)
		createTree(t->rchild, preStr.substr(1 + left_length, right_length),
			inStr.substr(1 + left_length, right_length));
}

/* Pre-threading a binary tree. */
void ThreadTree::preThreading()
{
	// If the bianry tree has been pre-threaded, do nothing and return.
	if (tag == PreThread)
		return;
	// If the binary tree has been in-threaded or post-threaded, clean the 
	// threads first.
	if (tag == InThread || tag == PostThread)
		cleanThread(root);
	pre = NULL;
	// Invoke private function "preThreading".
	preThreading(root);
	tag = PreThread;
}

/* Procedure of pre-threading a binary tree. */
void ThreadTree::preThreading(Node & node)
{
	if (node == NULL)
		return;
	if (node->lchild == NULL)
	{
		node->ltag = TreeNode::PointerTag::Thread;
		node->lchild = pre;
	}
	if (pre != NULL && pre->rchild == NULL)
	{
		pre->rtag = TreeNode::PointerTag::Thread;
		pre->rchild = node;
	}
	pre = node;
	if (node->ltag == TreeNode::PointerTag::Link)
		preThreading(node->lchild);
	if (node->rtag == TreeNode::PointerTag::Link)
		preThreading(node->rchild);
}

/* In-threading a binary tree. */
void ThreadTree::inThreading()
{
	// If the binary tree has been in-threaded, do nothing and return.
	if (tag == InThread)
		return;
	// If the binary tree has been pre-threaded or post-threaded, clean 
	// the threads first.
	if (tag == PreThread || tag == PostThread)
		cleanThread(root);
	pre = NULL;
	// Invoke private function "inThreading".
	inThreading(root);
	tag = InThread;
}

/* Procedure of in-threading a binary tree. */
void ThreadTree::inThreading(Node & node)
{
	if (node == NULL)
		return;
	inThreading(node->lchild);
	if (node->lchild == NULL)
	{
		node->ltag = TreeNode::PointerTag::Thread;
		node->lchild = pre;
	}
	if (pre != NULL && pre->rchild == NULL)
	{
		pre->rtag = TreeNode::PointerTag::Thread;
		pre->rchild = node;
	}
	pre = node;
	inThreading(node->rchild);
}

/* Post-threading a binary tree. */
void ThreadTree::postThreading()
{
	// If the binary tree has been post-threaded, do nothing and return.
	if (tag == PostThread)
		return;
	// If the binary tree has been pre-threaded or in-threaded, clean the
	// threads first.
	if (tag == PreThread || tag == InThread)
		cleanThread(root);
	pre = NULL;
	// Invoke private function "postThreading".
	postThreading(root);
	tag = PostThread;
}

/* Procedure of post-threading a binary tree. */
void ThreadTree::postThreading(Node & node)
{
	if (node == NULL)
		return;
	postThreading(node->lchild);
	postThreading(node->rchild);
	if (node->lchild == NULL)
	{
		node->ltag = TreeNode::PointerTag::Thread;
		node->lchild = pre;
	}
	if (pre != NULL && pre->rchild == NULL)
	{
		pre->rtag = TreeNode::PointerTag::Thread;
		pre->rchild = node;
	}
	pre = node;
}

/* Clean the threads of a binary tree. */
void ThreadTree::cleanThread()
{
	// If the binary tree has not been threaded, do nothing and return.
	if (tag == NoneThread)
		return;
	// Invoke private function "cleanThread".
	cleanThread(root);
	tag = NoneThread;
}

/* Procedure of clean the threads of a threaded binary tree. */
void ThreadTree::cleanThread(Node & node)
{
	if (node == NULL)
		return;
	if (node->ltag == TreeNode::PointerTag::Thread)
	{
		node->ltag = TreeNode::PointerTag::Link;
		node->lchild = NULL;
	}
	if (node->rtag == TreeNode::PointerTag::Thread)
	{
		node->rtag = TreeNode::PointerTag::Link;
		node->rchild = NULL;
	}
	cleanThread(node->lchild);
	cleanThread(node->rchild);
}

/* Pre-order traverse a threaded binary tree. */
void ThreadTree::preOrder() const
{
	// Choose a method of traversing according to the tree's state.
	if (tag == PreThread)
		preOrderThread(root);
	else
		preOrder(root);
}

/* Pre-order traverse a binary tree which is not pre-threaded. */
void ThreadTree::preOrder(const Node & node) const
{
	if (node == NULL)
		return;
	std::cout << node->element << " ";
	if (node->ltag == TreeNode::PointerTag::Link)
		preOrder(node->lchild);
	if (node->rtag == TreeNode::PointerTag::Link)
		preOrder(node->rchild);
}

/* Pre-order traverse a pre-threaded binary tree. */
void ThreadTree::preOrderThread(const Node & node) const
{
	Node p = node;
	while (p != NULL)
	{
		std::cout << p->element << " ";
		if (p->ltag == TreeNode::PointerTag::Link)
			p = p->lchild;
		else
			p = p->rchild;
	}
}

/* In-order traverse a threaded binary tree. */
void ThreadTree::inOrder() const
{
	// Choose a method of traversing according to the tree's state.
	if (tag == InThread)
		inOrderThread(root);
	else
		inOrder(root);
}

/* In-order traverse a binary tree which is not in-threaded. */
void ThreadTree::inOrder(const Node & node) const
{
	if (node == NULL)
		return;
	if (node->ltag == TreeNode::PointerTag::Link)
		inOrder(node->lchild);
	std::cout << node->element << " ";
	if (node->rtag == TreeNode::PointerTag::Link)
		inOrder(node->rchild);
}

/* In-order traverse a in-threaded binary tree. */
void ThreadTree::inOrderThread(const Node & node) const
{
	Node p = node;
	while (p != NULL)
	{
		while (p->ltag == TreeNode::PointerTag::Link)
			p = p->lchild;
		std::cout << p->element << " ";
		while (p->rtag == TreeNode::PointerTag::Thread)
		{
			p = p->rchild;
			std::cout << p->element << " ";
		}
		p = p->rchild;
	}
}

/* Post-order traverse a threaded binary tree. */
void ThreadTree::postOrder() const
{
	// Choose a method of traversing according to the tree's state.
	if (tag == PostThread)
		postOrderThread(root);
	else
		postOrder(root);
}

/* Post-order traverse a binary tree which is not post-threaded. */
void ThreadTree::postOrder(const Node & node) const
{
	if (node == NULL)
		return;
	if (node->ltag == TreeNode::PointerTag::Link)
		postOrder(node->lchild);
	if (node->rtag == TreeNode::PointerTag::Link)
		postOrder(node->rchild);
	std::cout << node->element << " ";
}

/* Post-order traverse a post-threaded binary tree. */
void ThreadTree::postOrderThread(const Node & node) const
{
	Node p = node;
	std::stack<elementType> s;
	while (p != NULL)
	{
		s.push(p->element);
		if (p->rtag == TreeNode::PointerTag::Link)
			p = p->rchild;
		else
			p = p->lchild;
	}
	while (!s.empty())
	{
		std::cout << s.top() << " ";
		s.pop();
	}
}

