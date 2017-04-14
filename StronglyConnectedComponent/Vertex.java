/**
 * Created by 许家乐on 2017/4/10.
 */
public class Vertex {
    public String name;
    public int low;
    public int num;
    public boolean visited;

    public Vertex() {
        this.name = null;
    }

    public Vertex(String name) {
        this.name = name;
        this.low = -1;
        this.num = -1;
        this.visited = false;
    }

    public boolean equals(Vertex v) {
        return this.name.equals(v.name);
    }

    public String toString() {
        return this.name;
    }
}
