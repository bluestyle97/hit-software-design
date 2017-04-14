/**
 * Created by 许家乐 on 2017/4/10.
 */
public class Vertex implements Comparable<Vertex> {
    public String name;
    public int lowcost;
    public int closest;

    public Vertex() {
        this.name = null;
        this.lowcost = 0;
        this.closest = 0;
    }

    public Vertex(String name) {
        this.name = name;
        this.lowcost = 0;
        this.closest = 0;
    }

    public int compareTo(Vertex v) {
        return this.lowcost - v.lowcost;
    }

    public boolean equals(Vertex v) {
        return this.name.equals(v.name);
    }

    public String toString() {
        return this.name;
    }
}
