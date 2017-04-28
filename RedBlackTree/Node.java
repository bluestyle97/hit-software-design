package redblacktree;

/**
 * @author Xu Jiale
 */

public class Node<T extends Comparable<? super T>> {
    enum Color {Red, Black}
    T key;
    Color color;
    Node parent;
    Node left;
    Node right;

    public Node() {
        this.key = null;
        this.color = Color.Red;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public Node(T key) {
        this.key = key;
        this.color = Color.Red;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.key);
        return sb.toString();
    }
}
