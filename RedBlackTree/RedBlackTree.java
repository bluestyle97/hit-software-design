package redblacktree;

/**
 * @author Xu Jiale
 */

public class RedBlackTree<T extends Comparable<? super T>> {
    private Node<T> root;

    public RedBlackTree() {
        this.root = null;
    }

    public Node<T> search(T key) {
        Node<T> node = this.root;
        while (node != null && node.key.compareTo(key) != 0) {
            if (node.key.compareTo(key) > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    public void insert(Node<T> node) {
        Node<T> tempNode = this.root;
        Node<T> parentNode = null;
        while (tempNode != null) {
            parentNode = tempNode;
            if (node.key.compareTo(tempNode.key) < 0) {
                tempNode = tempNode.left;
            } else {
                tempNode = tempNode.right;
            }
        }
        node.parent = parentNode;
        if (node.parent == null) {
            this.root = node;
        } else if (node.key.compareTo(parentNode.key) < 0) {
            parentNode.left = node;
        } else {
            parentNode.right = node;
        }
        node.color = Node.Color.Red;
        insertFixUp(node);
    }

    public boolean delete(T key) {
        Node<T> node = search(key);
        if (node == null) {
            return false;
        }
        Node<T> removedOrMovedNode = node;
        Node<T> replaceNode = null;
        Node.Color colorOriginal = removedOrMovedNode.color;
        if (node.left == null) {
            replaceNode = node.right;
            transPlant(node, replaceNode);
        }
        else if (node.right == null) {
            replaceNode = node.left;
            transPlant(node, replaceNode);
        }
        else {
            removedOrMovedNode = minimum(node.right);
            colorOriginal = removedOrMovedNode.color;
            replaceNode = removedOrMovedNode.right;
            if (removedOrMovedNode.parent == node) {
                replaceNode.parent = removedOrMovedNode;
            }
            else {
                transPlant(removedOrMovedNode, removedOrMovedNode.right);
                removedOrMovedNode.right = node.right;
                removedOrMovedNode.right.parent = removedOrMovedNode;
            }
            transPlant(node, removedOrMovedNode);
            removedOrMovedNode.left = node.left;
            removedOrMovedNode.left.parent = removedOrMovedNode;
            removedOrMovedNode.color = node.color;
        }
        if (colorOriginal == Node.Color.Black) {
            deleteFixUp(replaceNode);
        }
        return true;
    }

    public void print() {
        preOrder(this.root);
        System.out.println();
        inOrder(this.root);
        System.out.println();
        postOrder(this.root);
    }

    public T minimum() {
        return minimum(root).key;
    }

    public T maximum() {
        return maximum(root).key;
    }

    private Node<T> leftRotate(Node<T> node) {
        Node<T> temp = node.right;
        node.right = temp.left;
        if (temp.left != null) {
            temp.left.parent = node;
        }
        temp.parent = node.parent;
        if (node.parent == null) {
            this.root = temp;
        } else if (node == node.parent.left) {
            node.parent.left = temp;
        } else {
            node.parent.right = temp;
        }
        temp.left = node;
        node.parent = temp;

        return temp;
    }

    private Node<T> rightRotate(Node<T> node) {
        Node<T> temp = node.left;
        node.left = temp.right;
        if (temp.right != null) {
            temp.right.parent = node;
        }
        temp.parent = node.parent;
        if (node.parent == null) {
            this.root = temp;
        } else if (node == node.parent.left) {
            node.parent.left = temp;
        } else {
            node.parent.right = temp;
        }
        temp.right = node;
        node.parent = temp;

        return temp;
    }

    private void insertFixUp(Node<T> node) {
        while (node.parent != null && node != this.root && node.parent.color == Node.Color.Red) {
            Node<T> parentNode = node.parent;
            Node<T> grandParentNode = node.parent.parent;
            Node<T> uncleNode = null;
            if (grandParentNode != null && parentNode == grandParentNode.left) {
                uncleNode = grandParentNode.right;
                if (uncleNode != null && uncleNode.color == Node.Color.Red) {
                    parentNode.color = Node.Color.Black;
                    uncleNode.color = Node.Color.Black;
                    grandParentNode.color = Node.Color.Red;
                    node = grandParentNode;
                    continue;
                }
                else {
                    if (node == parentNode.right) {
                        node = parentNode;
                        leftRotate(node);
                    }
                    parentNode.color = Node.Color.Black;
                    grandParentNode.color = Node.Color.Red;
                    rightRotate(grandParentNode);
                }
            }
            else if (grandParentNode != null && parentNode == grandParentNode.right) {
                uncleNode = grandParentNode.left;
                if (uncleNode != null && uncleNode.color == Node.Color.Red) {
                    parentNode.color = Node.Color.Black;
                    uncleNode.color = Node.Color.Black;
                    grandParentNode.color = Node.Color.Red;
                    node = grandParentNode;
                    continue;
                }
                else {
                    if (node == parentNode.left) {
                        node = parentNode;
                        rightRotate(node);
                    }
                    parentNode.color = Node.Color.Black;
                    grandParentNode.color = Node.Color.Red;
                    leftRotate(grandParentNode);
                }
            }
        }
        this.root.color = Node.Color.Black;
    }

    private void deleteFixUp(Node<T> node) {
        while (node != this.root && node != null && node.color == Node.Color.Black) {
            if (node == node.parent.left) {
                Node<T> siblingNode = node.parent.right;
                if (siblingNode.color == Node.Color.Red) {
                    siblingNode.color = Node.Color.Black;
                    node.parent.color = Node.Color.Red;
                    leftRotate(node.parent);
                    siblingNode = node.parent.right;
                }
                if ((siblingNode.left == null || siblingNode.left.color == Node.Color.Black) &&
                        (siblingNode.right == null || siblingNode.right.color == Node.Color.Black)) {
                    siblingNode.color = Node.Color.Red;
                    node = node.parent;
                }
                else {
                    if (siblingNode.right == null || siblingNode.right.color == Node.Color.Black) {
                        siblingNode.left.color = Node.Color.Black;
                        siblingNode.color = Node.Color.Red;
                        rightRotate(siblingNode);
                        siblingNode = node.parent.right;
                    }
                    siblingNode.color = node.parent.color;
                    node.parent.color = Node.Color.Black;
                    siblingNode.right.color = Node.Color.Black;
                    leftRotate(node.parent);
                    node = this.root;
                    break;
                }
            }
            else {
                Node<T> siblingNode = node.parent.left;
                if (siblingNode.color == Node.Color.Red) {
                    siblingNode.color = Node.Color.Black;
                    node.parent.color = Node.Color.Red;
                    rightRotate(node.parent);
                    siblingNode = node.parent.left;
                }
                if ((siblingNode == null || siblingNode.left.color == Node.Color.Black) &&
                        (siblingNode == null || siblingNode.right.color == Node.Color.Black)) {
                    siblingNode.color = Node.Color.Red;
                    node = node.parent;
                }
                else {
                    if (siblingNode == null || siblingNode.left.color == Node.Color.Black) {
                        siblingNode.right.color = Node.Color.Black;
                        siblingNode.color = Node.Color.Red;
                        leftRotate(siblingNode);
                        siblingNode = node.parent.left;
                    }
                    siblingNode.color = node.parent.color;
                    node.parent.color = Node.Color.Black;
                    siblingNode.left.color = Node.Color.Black;
                    rightRotate(node.parent);
                    node = this.root;
                }
            }
        }
    }

    private Node<T> minimum(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node<T> maximum(Node<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private void transPlant(Node<T> oldNode, Node<T> newNode) {
        if (oldNode.parent == null) {
            this.root = newNode;
        } else if (oldNode == oldNode.parent.left) {
            oldNode.parent.left = newNode;
        } else {
            oldNode.parent.right = newNode;
        }
        if (newNode != null) {
            newNode.parent = oldNode.parent;
        }
    }

    private void preOrder(Node<T> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.key + "," + node.color + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    private void inOrder(Node<T> node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.key + "," + node.color + " ");
        inOrder(node.right);
    }

    private void postOrder(Node<T> node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.key + "," + node.color + " ");
    }
}
