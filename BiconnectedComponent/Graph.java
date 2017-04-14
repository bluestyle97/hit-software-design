import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.Math;

/**
 * Created by 许家乐 on 2017/4/8.
 */

public class Graph {
    public ArrayList<Vertex> points;
    public ArrayList<HashSet<Vertex>> components;

    private int vertexNumber;
    private int edgeNumber;
    private ArrayList<Vertex> vertices;
    private HashMap<Vertex, HashSet<Vertex>> edges;

    private static int counter;

    public Graph() {
        vertexNumber = 0;
        edgeNumber = 0;
        vertices = new ArrayList<>();
        edges = new HashMap<>();
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public int getEdgeNumber() {
        return edgeNumber;
    }

    public void setVertexNumber(int v) {
        vertexNumber = v;
    }

    public void setEdgeNumber(int e) {
        edgeNumber = e;
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
        edges.put(v, new HashSet<>());
        ++vertexNumber;
    }

    public void addEdge(String head, String tail) {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        for (Vertex v : edges.keySet()) {
            if (v.name.equals(head))
                a = v;
            if (v.name.equals(tail))
                b = v;
        }
        edges.get(a).add(b);
        ++edgeNumber;
    }

    public void findArtPoints() {
        counter = 1;
        points = new ArrayList<>();
        Vertex v = vertices.get(0);
        find(v);
        if (v.children.size() > 1)
            points.add(v);
    }

    public void findComponents() {
        components = new ArrayList<>();
        HashSet<Vertex> component = new HashSet<>();
        if (points.isEmpty()) {
            for (Vertex v : vertices)
                component.add(v);
            components.add(component);
            return;
        }
        for (Vertex v : vertices) {
            component.add(v);
            for (Vertex w: vertices)
                if (w.low == v.num)
                    component.add(w);
            if (component.size() == 1) {
                if (v.parent != null && v.parent.num < v.low) {
                    component.add(v.parent);
                    components.add(component);
                }
                component = new HashSet<>();
            }
            else {
                components.add(component);
                component = new HashSet<>();
            }
        }
    }

    public void print() {
        for (Vertex v : vertices)
            System.out.println(v.name + " " + "num:" + v.num + " " + "low:" + v.low);
    }

    private void find(Vertex v) {
        v.num = counter++;
        v.low = v.num;
        v.visited = true;
        for (Vertex w : edges.get(v)) {
            if (!w.visited) {
                v.children.add(w);
                w.parent = v;
                find(w);
                if (w.low >= v.num && v.num != 1)
                    points.add(v);
                v.low = Math.min(v.low, w.low);
            }
            else if (v.parent != null && !v.parent.equals(w))
                v.low = Math.min(v.low, w.num);
        }
    }
}