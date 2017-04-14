import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by 许家乐 on 2017/4/12.
 */
public class ShortestPath {
    private static final int INFINITY = Integer.MAX_VALUE / 2;

    private static void initializeFile(Graph g) {
        Scanner in;
        try {
            in = new Scanner(new FileInputStream("graph.txt"));
            int v = in.nextInt();
            int e = in.nextInt();
            for (int i = 0; i < v; ++i) {
                String str = in.next();
                g.addVertex(new Vertex(str));
            }
            g.initialize();
            for (int i = 0; i < e; ++i) {
                String head = in.next();
                String tail = in.next();
                int weight = in.nextInt();
                g.addEdge(head, tail, weight);
            }
        } catch (FileNotFoundException e) {
            System.exit(0);
        }
    }
    private static void initialize(Graph g) {
        Scanner in = new Scanner(System.in);
        System.out.print("请输入顶点数和边数：");
        int v = in.nextInt();
        int e = in.nextInt();
        System.out.println("请输入各结点名称：");
        for (int i = 0; i < v; ++i) {
            String str = in.next();
            g.addVertex(new Vertex(str));
        }
        g.initialize();
        System.out.println("请输入各边起点、终点和权重：");
        for (int i = 0; i < e; ++i) {
            String head = in.next();
            String tail = in.next();
            int weight = in.nextInt();
            g.addEdge(head, tail, weight);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Graph g = null;
        long startTime, endTime;
        System.out.println("************************************");
        System.out.println("*     欢迎使用图的最短路径系统      *");
        System.out.println("************************************");
        String choice = "";
        while(!choice.equals("3")) {
            System.out.println("************************************");
            System.out.println("*          1.初始化带权图           *");
            System.out.println("*          2.寻找最短路径           *");
            System.out.println("*          3.退出当前菜单           *");
            System.out.println("************************************");
            System.out.print("请输入您的选择：");
            choice = in.next();
            switch(choice) {
            case "1":{
                g = new Graph();
                String menu;
                System.out.println("************************************");
                System.out.println("*          1.从键盘输入             *");
                System.out.println("*          2.从文件读取             *");
                System.out.println("************************************");
                System.out.print("请输入您的选择：");
                menu = in.next();
                switch(menu) {
                    case "1":
                        initialize(g);
                        break;
                    case "2":
                        initializeFile(g);
                        break;
                    default:
                        System.out.println("您的输入有误！");
                        break;
                }
                System.out.println("带权图初始化完成！");
                break;
            }
            case "2":{
                String menu = "";
                while (!menu.equals("4")) {
                    System.out.println("************************************");
                    System.out.println("*          1.使用Dijkstra算法       *");
                    System.out.println("*          2.使用Floyd算法          *");
                    System.out.println("*          3.使用Bellman_Ford算法   *");
                    System.out.println("*          4.退出子菜单             *");
                    System.out.println("************************************");
                    System.out.print("请输入您的选择：");
                    menu = in.next();
                    switch (menu) {
                    case "1": {
                        String beginName;
                        String endName;
                        Vertex begin = new Vertex();
                        Vertex end = new Vertex();
                        System.out.print("请输入起点和终点：");
                        beginName = in.next();
                        endName = in.next();
                        for (Vertex v : g.vertices) {
                            if (v.name.equals(beginName))
                                begin = v;
                            if (v.name.equals(endName))
                                end = v;
                        }
                        g.reset();
                        startTime = System.nanoTime();
                        g.Dijkstra(begin);
                        endTime = System.nanoTime();
                        if (end.distance < INFINITY) {
                            System.out.println("从顶点" + begin + "到顶点" + end + "的最短路径长度为" + end.distance);
                            System.out.print("从顶点" + begin + "到顶点" + end + "的最短路径为：");
                            g.printPathDijkstra(end);
                            System.out.println();
                        }
                        else
                            System.out.println("从顶点" + begin + "到顶点" + end + "的最短路径不存在！");
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        g.reset();
                        startTime = System.nanoTime();
                        g.dijkstra(begin);
                        endTime = System.nanoTime();
                        if (end.distance < INFINITY) {
                            System.out.println("从顶点" + begin + "到顶点" + end + "的最短路径长度为" + end.distance);
                            System.out.print("从顶点" + begin + "到顶点" + end + "的最短路径为：");
                            g.printPathDijkstra(end);
                            System.out.println();
                        }
                        else
                            System.out.println("从顶点" + begin + "到顶点" + end + "的最短路径不存在！");
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    }
                    case "2": {
                        String beginName;
                        String endName;
                        Vertex begin = new Vertex();
                        Vertex end = new Vertex();
                        System.out.print("请输入起点和终点：");
                        beginName = in.next();
                        endName = in.next();
                        for (Vertex v : g.vertices) {
                            if (v.name.equals(beginName))
                                begin = v;
                            if (v.name.equals(endName))
                                end = v;
                        }
                        int beginIndex = g.vertices.indexOf(begin);
                        int endIndex = g.vertices.indexOf(end);
                        g.reset();
                        startTime = System.nanoTime();
                        g.floyd();
                        endTime = System.nanoTime();
                        if (g.distance[beginIndex][endIndex] < INFINITY) {
                            System.out.println("从顶点" + begin + "到顶点" + end + "的最短路径长度为" + g.distance[beginIndex][endIndex]);
                            System.out.print("从顶点" + begin + "到顶点" + end + "的最短路径为：");
                            System.out.print(begin + " ");
                            g.printPathFloyd(begin, end);
                            System.out.println(end);
                        }
                        else
                            System.out.println("从顶点" + begin + "到顶点" + end + "的最短路径不存在！");
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    }
                    case "3":{
                        String beginName;
                        String endName;
                        Vertex begin = new Vertex();
                        Vertex end = new Vertex();
                        System.out.print("请输入起点和终点：");
                        beginName = in.next();
                        endName = in.next();
                        for (Vertex v : g.vertices) {
                            if (v.name.equals(beginName))
                                begin = v;
                            if (v.name.equals(endName))
                                end = v;
                        }
                        int beginIndex = g.vertices.indexOf(begin);
                        int endIndex = g.vertices.indexOf(end);
                        g.reset();
                        startTime = System.nanoTime();
                        boolean circle = g.bellman_ford();
                        endTime = System.nanoTime();
                        if (!circle) {
                            System.out.println("从顶点" + begin + "到顶点" + end + "的最短路径长度为" + g.distance[beginIndex][endIndex]);
                            System.out.print("从顶点" + begin + "到顶点" + end + "的最短路径为：");
                            System.out.print(begin + " ");
                            g.printPathFloyd(begin, end);
                            System.out.println(end);
                        }
                        else
                            System.out.println("该有向图中有负环路！");
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    }
                    case "4":
                        System.out.println();
                        break;
                    default:
                        System.out.println("您的输入有误！");
                        break;
                    }
                }
                break;
            }
            case "3":
                System.out.println("************************************");
                System.out.println("*     感谢使用图的最短路径系统      *");
                System.out.println("************************************");
                break;
            default:
                System.out.println("************************************");
                System.out.println("*     您的输入有误，请重新输入！     *");
                System.out.println("************************************");
                break;
            }
        }
    }
}
/*
12 17
A B C D E F G H I J K L
A B 2
A H 3
B C 5
B I 4
C G 3
D G 2
E D 3
E I 2
F E 2
G F 5
H C 1
H F 1
I K 3
J H 2
J K 4
K L 3
L F 1
 */
/*
4 5
A B C D
A B 1
B C 1
B D 2
C A -3
D C 5
 */