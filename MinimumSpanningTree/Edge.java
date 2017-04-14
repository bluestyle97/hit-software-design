/**
 * Created by 许家乐 on 2017/4/11.
 */
public class Edge implements Comparable<Edge> {
    public Vertex begin;
    public Vertex end;
    public int weight;

    public Edge() {
        this.begin = null;
        this.end = null;
        this.weight = 0;
    }

    public Edge(Vertex v, Vertex w, int weight) {
        this.begin = v;
        this.end = w;
        this.weight = weight;
    }

    public boolean equals(Edge e) {
        return (this.begin.equals(e.begin)&& this.end.equals(e.end) && this.weight == e.weight);
    }

    public String toString() {
        String s = "";
        s = s + "{" + this.begin + ", " + this.end + "}";
        return s;
    }

    public int compareTo(Edge e) {
        if (this.weight < e.weight) return -1;
        else if (this.weight == e.weight) return 0;
        else return 1;
    }
}
