import java.util.ArrayList;

/**
 * Created by 许家乐 on 2017/4/8.
 */


public class Vertex {
    public String name;
    public int low;
    public int num;
    public boolean visited;
    public Vertex parent;
    public ArrayList<Vertex> children;

    public Vertex() {
        this.name = null;
    }

    public Vertex(String name) {
        this.name = name;
        this.low = -1;
        this.num = -1;
        this.visited = false;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public boolean equals(Vertex node) {
        return this.name.equals(node.name);
    }

    public String toString() {
        return this.name;
    }
}
