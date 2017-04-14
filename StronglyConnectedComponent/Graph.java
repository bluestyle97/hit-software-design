import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by 心海飘忆 on 2017/4/10.
 */

public class Graph {
    public ArrayList<HashSet<Vertex>> components;

    private int vertexNumber;
    private int edgeNumber;
    private ArrayList<Vertex> vertices;
    private HashMap<Vertex, HashSet<Vertex>> edges;
    private HashMap<Vertex, HashSet<Vertex>> edgesReversed;

    private static int counter;
    private static Stack<Vertex> stk;
    private static HashSet<Vertex> component;
    private static HashMap<Vertex, Boolean> inStack;

    public Graph() {
        this.vertexNumber = 0;
        this.edgeNumber = 0;
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();
        this.edgesReversed = new HashMap<>();
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
        edgesReversed.put(v, new HashSet<>());
        ++vertexNumber;
    }

    public void addEdge(String head, String tail) {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        for (Vertex v : vertices) {
            if (v.name.equals(head))
                a = v;
            if (v.name.equals(tail))
                b = v;
        }
        edges.get(a).add(b);
        edgesReversed.get(b).add(a);
        ++edgeNumber;
    }

    public void kosaraju() {
        components = new ArrayList<>();

        stk = new Stack<>();
        component = new HashSet<>();

        reset();
        for (Vertex v : vertices)
            if (!v.visited)
                kosarajuDfs1(v);
        reset();
        while (!stk.empty()) {
            Vertex v = stk.peek();
            stk.pop();
            if (!v.visited) {
                kosarajuDfs2(v);
                if (component.size() > 1)
                    components.add(component);
                component = new HashSet<>();
            }
        }
    }

    public void tarjan() {
        components = new ArrayList<>();

        counter = 0;
        stk = new Stack<>();
        component = new HashSet<>();
        inStack = new HashMap<>();

        for (Vertex v : vertices)
            inStack.put(v, false);
        reset();
        for (Vertex v : vertices)
            if (!v.visited)
                tarjanDfs(v);
    }

    public void gabow() {
        components = new ArrayList<>();

        counter = 0;
        stk = new Stack<>();
        Stack<Vertex> stk2 = new Stack<>();
        component = new HashSet<>();
        inStack = new HashMap<>();

        for (Vertex v : vertices)
            inStack.put(v, true);
        reset();
        for (Vertex v : vertices)
            if (!v.visited)
                gabowDfs(v, stk2);
    }

    public void print() {
        System.out.println("正向：");
        for (Vertex v : vertices) {
            System.out.print(v + ": ");
            for (Vertex w : edges.get(v))
                System.out.print(w + " ");
            System.out.println();
        }
        System.out.println("逆向：");
        for (Vertex v : vertices) {
            System.out.print(v + ": ");
            for (Vertex w : edgesReversed.get(v))
                System.out.print(w + " ");
            System.out.println();
        }
    }

    private void kosarajuDfs1(Vertex v) {
        v.visited = true;
        for (Vertex w : edges.get(v))
            if (!w.visited)
                kosarajuDfs1(w);
        stk.push(v);
    }

    private void kosarajuDfs2(Vertex v) {
        v.visited = true;
        component.add(v);
        for (Vertex w : edgesReversed.get(v))
            if (!w.visited)
                kosarajuDfs2(w);
    }

    private void tarjanDfs(Vertex v) {
        v.num = ++counter;
        v.low = v.num;
        v.visited = true;
        inStack.replace(v, true);
        stk.push(v);
        for (Vertex w : edges.get(v)) {
            if (!w.visited) {
                tarjanDfs(w);
                if (w.low < v.low)
                    v.low = w.low;
            }
            else if (inStack.get(w) && w.num < v.low)
                v.low = w.num;
        }
        if (v.low == v.num) {
            while (!stk.empty() && !stk.peek().equals(v)) {
                inStack.replace(stk.peek(), false);
                component.add(stk.peek());
                stk.pop();
            }
            if (!stk.empty()) {
                inStack.replace(stk.peek(), false);
                component.add(stk.peek());
                stk.pop();
            }
            if (component.size() > 1)
                components.add(component);
            component = new HashSet<>();
        }
    }

    private void gabowDfs(Vertex v, Stack<Vertex> stk2) {
        v.num = ++counter;
        v.visited = true;
        stk.push(v);
        stk2.push(v);
        for (Vertex w : edges.get(v)) {
            if (!w.visited)
                gabowDfs(w, stk2);
            else if (inStack.get(w))
                while (!stk2.empty() && stk2.peek().num > w.num)
                    stk2.pop();
        }
        if (v.equals(stk2.peek())) {
            stk2.pop();
            while (! stk.empty() && !stk.peek().equals(v)) {
                inStack.replace(stk.peek(), false);
                component.add(stk.peek());
                stk.pop();
            }
            if (!stk.empty()) {
                inStack.replace(stk.peek(), false);
                component.add(stk.peek());
                stk.pop();
            }
            if (component.size() > 1)
                components.add(component);
            component = new HashSet<>();
        }
    }

    private void reset() {
        for (Vertex v : vertices) {
            v.low = -1;
            v.num = -1;
            v.visited = false;
        }
    }
}
