/**
 * Created by 许家乐 on 2017/4/12.
 */
public class Vertex implements Comparable<Vertex> {
    public String name;
    public Vertex path;
    public int distance;
    public boolean known;

    private static final int INFINITY = Integer.MAX_VALUE / 2;

    public Vertex() {
        this.name = null;
        this.path = null;
        this.distance = INFINITY;
        this.known = false;
    }

    public Vertex(String name) {
        this.name = name;
        this.path = null;
        this.distance = INFINITY;
        this.known = false;
    }

    public int compareTo(Vertex v) {
        return this.distance - v.distance;
    }

    public boolean equals(Vertex v) {
        return this.name.equals(v.name);
    }

    public boolean empty() {
        return this.name == null;
    }

    public String toString() {
        return this.name;
    }
}
