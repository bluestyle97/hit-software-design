import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by 许家乐 on 2017/4/10.
 */
public class MinimumSpanningTree {
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
        int counter;
        int mstWeight;
        long startTime;
        long endTime;
        Scanner in = new Scanner(System.in);
        Graph g = null;
        System.out.println("************************************");
        System.out.println("*     欢迎使用图的最小生成树系统     *");
        System.out.println("************************************");
        String choice = "";
        while (!choice.equals("3")) {
            System.out.println("************************************");
            System.out.println("*          1.初始化带权图           *");
            System.out.println("*          2.求最小生成树           *");
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
                        startTime = System.nanoTime();
                        initialize(g);
                        endTime = System.nanoTime();
                        System.out.println("带权图初始化完成！");
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    case "2":
                        startTime = System.nanoTime();
                        initializeFile(g);
                        endTime = System.nanoTime();
                        System.out.println("带权图初始化完成！");
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    default:
                        System.out.println("您的输入有误！");
                        break;
                }
                break;
            }
            case "2": {
                String menu = "";
                while (!menu.equals("3")) {
                    System.out.println("************************************");
                    System.out.println("*          1.使用Prim算法           *");
                    System.out.println("*          2.使用Kruskal算法        *");
                    System.out.println("*          3.退出子菜单             *");
                    System.out.println("************************************");
                    System.out.print("请输入您的选择：");
                    menu = in.next();
                    switch (menu) {
                    case "1":
                        counter = 1;

                        startTime = System.nanoTime();
                        mstWeight = g.prim();
                        endTime = System.nanoTime();
                        System.out.println("最小生成树中的边有：");
                        for (Edge e : g.MSTree)
                            System.out.println("Edge " + counter++ + ": " + e);
                        System.out.println("最小生成树的权值为：" + mstWeight);
                        System.out.println("用时：" + (endTime - startTime) + "ns");

                        startTime = System.nanoTime();
                        mstWeight = g.Prim();
                        endTime = System.nanoTime();
                        System.out.println("最小生成树中的边有：");
                        for (Edge e : g.MSTree)
                            System.out.println("Edge " + counter++ + ": " + e);
                        System.out.println("最小生成树的权值为：" + mstWeight);
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    case "2":
                        counter = 1;
                        startTime = System.nanoTime();
                        mstWeight = g.kruskal();
                        endTime = System.nanoTime();
                        System.out.println("最小生成树中的边有：");
                        for (Edge e : g.MSTree)
                            System.out.println("Edge " + counter++ + ": " + e);
                        System.out.println("最小生成树的权值为：" + mstWeight);
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    case "3":
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
                System.out.println("*     感谢使用图的最小生成树系统     *");
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
C H 1
D E 3
D G 2
E F 2
E I 2
F G 5
F H 1
F L 1
H J 2
I K 3
J K 4
K L 3
 */