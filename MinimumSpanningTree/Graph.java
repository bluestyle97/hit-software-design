import java.util.*;

/**
 * Created by 许家乐 on 2017/4/10.
 */
public class Graph {
    public int vertexNumber;
    public int edgeNumber;
    public ArrayList<Edge> MSTree;

    private ArrayList<Vertex> vertices;
    private int[][] matrix;
    private ArrayList<Edge> edges;

    private static final int INFINITY = Integer.MAX_VALUE / 2;

    public Graph() {
        this.vertexNumber = 0;
        this.edgeNumber = 0;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void initialize() {
        this.matrix = new int[vertexNumber][vertexNumber];
        for (int i = 0; i < vertexNumber; ++i)
            for (int j = 0; j < vertexNumber; ++j)
                matrix[i][j] = INFINITY;
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
        ++vertexNumber;
    }

    public void addEdge(String head, String tail, int weight) {
        int a = 0, b = 0;
        for (int i = 0; i < vertexNumber; ++i) {
            if (vertices.get(i).name.equals(head))
                a = i;
            if (vertices.get(i).name.equals(tail))
                b = i;
        }
        matrix[a][b] = weight;
        matrix[b][a] = weight;
        Edge e = new Edge(vertices.get(a), vertices.get(b), weight);
        edges.add(e);
        ++edgeNumber;
    }

    public int Prim() {
        int weight = 0;
        Vertex min;
        PriorityQueue<Vertex> heap = new PriorityQueue<>();
        HashSet<Vertex> set = new HashSet<>();
        MSTree = new ArrayList<>();

        vertices.get(0).lowcost = 0;
        vertices.get(0).closest = 0;
        heap.add(vertices.get(0));
        for (int i = 1; i < vertexNumber; ++i) {
            vertices.get(i).lowcost = matrix[0][i];
            vertices.get(i).closest = 0;
            heap.add(vertices.get(i));
        }
        heap.poll();
        while (!heap.isEmpty()) {
            min = heap.poll();
            //System.out.println("Deletemin: " + min);
            weight += min.lowcost;
            //System.out.println("Weight: " + weight);
            Vertex close = vertices.get(min.closest);
            Edge e = new Edge(close, min, matrix[min.closest][vertices.indexOf(min)]);
            //System.out.println("Add Edge: " + e);
            MSTree.add(e);
            min.lowcost = 0;
            for (Vertex v : heap)
                if (matrix[vertices.indexOf(min)][vertices.indexOf(v)] < v.lowcost) {
                    v.lowcost = matrix[vertices.indexOf(min)][vertices.indexOf(v)];
                    v.closest = vertices.indexOf(min);
                    set.add(v);
                    //System.out.println("Update Vertex: " + v + " " + v.lowcost + " " + v.closest);
                }
            for (Vertex v : set) {
                heap.remove(v);
                heap.add(v);
            }
            set = new HashSet<>();
        }
        return weight;
    }

    public int prim() {
        int weight = 0;
        int k = 0;
        int min;

        int[] lowcost = new int[vertexNumber];
        int[] closest = new int[vertexNumber];
        MSTree = new ArrayList<>();

        for (int i = 0; i < vertexNumber; ++i) {
            lowcost[i] = matrix[0][i];
            closest[i] = 0;
        }
        lowcost[0] = 0;
        for (int i = 1; i < vertexNumber; ++i) {
            min = INFINITY;
            for (int j = 0; j < vertexNumber; ++j)
                if (lowcost[j] != 0 && lowcost[j] < min) {
                    min = lowcost[j];
                    k = j;
                }
            weight += lowcost[k];
            Vertex a = vertices.get(closest[k]);
            Vertex b = vertices.get(k);
            Edge e = new Edge(a, b, matrix[closest[k]][k]);
            MSTree.add(e);
            lowcost[k] = 0;
            for (int j = 0; j < vertexNumber; ++j)
                if (lowcost[j] != 0 && matrix[k][j] < lowcost[j]) {
                    lowcost[j] = matrix[k][j];
                    closest[j] = k;
                }
        }
        return weight;
    }

    public int kruskal() {
        int beginFather, endFather;
        int count = 0, weight = 0;
        int[] father = new int[vertexNumber];

        MSTree = new ArrayList<>();
        Collections.sort(edges);
        for (int i = 0; i < vertexNumber; ++i)
            father[i] = -1;
        for (int i = 0; i < edgeNumber; ++i) {
            beginFather = findFather(father, edges.get(i).begin);
            endFather = findFather(father, edges.get(i).end);
            if (beginFather != endFather) {
                father[endFather] = beginFather;
                MSTree.add(edges.get(i));
                ++count;
                weight += edges.get(i).weight;
            }
            if (count == vertexNumber - 1)
                return weight;
        }
        return 0;
    }

    private int findFather(int[] father, Vertex v) {
        int f = vertices.indexOf(v);
        while (father[f] >= 0)
            f = father[f];
        return f;
    }
}
