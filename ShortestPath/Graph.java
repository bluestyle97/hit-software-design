import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by 许家乐 on 2017/4/12.
 */
public class Graph {
    public int vertexNumber;
    public int edgeNumber;
    public ArrayList<Vertex> vertices;

    private HashMap<Vertex, HashSet<EdgeNode>> edges;
    private int[][] matrix;

    private static Vertex[][] path;
    public static int[][] distance;

    private static final int INFINITY = Integer.MAX_VALUE / 2;

    public Graph() {
        this.vertexNumber = 0;
        this.edgeNumber = 0;
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();
    }

    public void initialize() {
        this.matrix = new int[vertexNumber][vertexNumber];
        for (int i = 0; i < vertexNumber; ++i)
            for (int j = 0; j < vertexNumber; ++j)
                matrix[i][j] = INFINITY;
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
        edges.put(v, new HashSet<>());
        ++vertexNumber;
    }

    public void addEdge(String head, String tail, int weight) {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        for (Vertex v : vertices) {
            if (v.name.equals(head))
                a = v;
            if (v.name.equals(tail))
                b = v;
        }
        EdgeNode e = new EdgeNode(b, weight);
        edges.get(a).add(e);
        matrix[vertices.indexOf(a)][vertices.indexOf(b)] = weight;
        ++edgeNumber;
    }

    public void Dijkstra(Vertex v) {
        v.distance = 0;
        Vertex w = v;
        ArrayList<Vertex> set = new ArrayList<>();
        while (true) {
            if (w == null) {
                break;
            }
            w.known = true;
            set.remove(w);
            for (EdgeNode e : edges.get(w))
                if (!e.end.known && w.distance + e.weight < e.end.distance) {
                    e.end.distance = w.distance + e.weight;
                    e.end.path = w;
                    set.add(e.end);
                }
            w = (set.isEmpty()) ? null : Collections.min(set);
        }
    }

    public void dijkstra(Vertex v) {
        Vertex w;
        v.distance = 0;
        while (true) {
            w = findMinVertex();
            if (w == null)
                break;
            w.known = true;
            for (EdgeNode e : edges.get(w))
                if (!e.end.known && w.distance + e.weight < e.end.distance) {
                    e.end.distance = w.distance + e.weight;
                    e.end.path = w;
                }
        }
    }

    public void printPathDijkstra(Vertex v) {
        if (v.path != null)
            printPathDijkstra(v.path);
        System.out.print(v + " ");
    }

    public void floyd() {
        path = new Vertex[vertexNumber][vertexNumber];
        distance = new int[vertexNumber][vertexNumber];
        for (int i = 0; i < vertexNumber; ++i)
            for (int j = 0; j < vertexNumber; ++j) {
                path[i][j] = null;
                distance[i][j] = matrix[i][j];
            }
        for (int k = 0; k < vertexNumber; ++k)
            for (int i = 0; i < vertexNumber; ++i)
                for (int j = 0; j < vertexNumber; ++j)
                    if (distance[i][k] + distance[k][j] < distance[i][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                        path[i][j] = vertices.get(k);
                    }
    }

    public boolean bellman_ford() {
        path = new Vertex[vertexNumber][vertexNumber];
        distance = new int[vertexNumber][vertexNumber];
        for (int i = 0; i < vertexNumber; ++i)
            for (int j = 0; j < vertexNumber; ++j) {
                path[i][j] = null;
                distance[i][j] = matrix[i][j];
            }
        for (int k = 0; k < vertexNumber; ++k)
            for (int i = 0; i < vertexNumber; ++i)
                for (int j = 0; j < vertexNumber; ++j)
                    if (distance[i][k] + distance[k][j] < distance[i][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                        path[i][j] = vertices.get(k);
                    }
        for (int k = 0; k < vertexNumber; ++k)
            for (int i = 0; i < vertexNumber; ++i)
                for (int j = 0; j < vertexNumber; ++j)
                    if (distance[i][k] + distance[k][j] < distance[i][j]) {
                        distance[i][j] = INFINITY;
                        path[i][j] = null;
                        return true;
                    }
        return false;
    }

    public void printPathFloyd(Vertex v, Vertex w) {
        Vertex u = path[vertices.indexOf(v)][vertices.indexOf(w)];
        if (u != null) {
            printPathFloyd(v, u);
            System.out.print(u + " ");
            printPathFloyd(u, w);
        }
    }

    public void reset() {
        for (int i = 0; i < vertexNumber; ++i) {
            vertices.get(i).path = null;
            vertices.get(i).distance = INFINITY;
            vertices.get(i).known = false;
        }
    }

    private Vertex findMinVertex() {
        Vertex min = null;
        int temp = INFINITY;
        for (Vertex v : vertices)
            if (!v.known && v.distance < temp) {
                temp = v.distance;
                min = v;
            }
        return min;
    }
}
