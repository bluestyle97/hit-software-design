import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;

/**
 * 无向图的双连通性算法
 * Created by 许家乐 on 2017/4/8.
 */

public class BiconnectedGraph {
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
            for (int i = 0; i < e; ++i) {
                String head = in.next();
                String tail = in.next();
                g.addEdge(head, tail);
                g.addEdge(tail, head);
            }
        } catch (FileNotFoundException e) {
            System.exit(0);
        }
    }
    public static void initialize(Graph g) {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入顶点数和边数：");
        int v = in.nextInt();
        int e = in.nextInt();
        System.out.println("请输入各结点数据：");
        for (int i = 0; i < v; ++i) {
            String str = in.next();
            g.addVertex(new Vertex(str));
        }
        System.out.println("请输入各边起点和终点：");
        for (int i = 0; i < e; ++i) {
            String head = in.next();
            String tail = in.next();
            g.addEdge(head, tail);
            g.addEdge(tail, head);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int counter = 1;
        long startTime;
        long endTime;
        Graph g = null;
        System.out.println("************************************");
        System.out.println("*   欢迎使用无向图的双连通分量系统   *");
        System.out.println("************************************");
        String choice = "";
        while(!choice.equals("3")) {
            System.out.println("************************************");
            System.out.println("*          1.初始化无向图           *");
            System.out.println("*          2.求双连通分量           *");
            System.out.println("*          3.退出当前菜单           *");
            System.out.println("************************************");
            System.out.print("请输入您的选择：");
            choice = in.next();
            switch (choice) {
                case "1":{
                    g = new Graph();
                    String menu;
                    System.out.println("************************************");
                    System.out.println("*          1.从键盘输入             *");
                    System.out.println("*          2.从文件读取             *");
                    System.out.println("************************************");
                    System.out.print("请输入您的选择：");
                    menu = in.next();
                    switch (menu) {
                        case "1":
                            startTime = System.nanoTime();
                            initialize(g);
                            endTime = System.nanoTime();
                            System.out.println("无向图初始化完成！");
                            System.out.println("用时：" + (endTime - startTime) + "ns");
                            break;
                        case "2":
                            startTime = System.nanoTime();
                            initializeFile(g);
                            endTime = System.nanoTime();
                            System.out.println("无向图初始化完成！");
                            System.out.println("用时：" + (endTime - startTime) + "ns");
                            break;
                        default:
                            System.out.println("您的输入有误！");
                            break;
                    }
                    break;
                }
                case "2":
                    g.findArtPoints();
                    g.findComponents();
                    System.out.println("该无向图中的割点有：" + g.points);
                    System.out.println("该无向图中的双连通分量个数为：" + g.components.size());
                    for (HashSet<Vertex> component : g.components)
                        System.out.println("component " + counter++ + ": "+component);
                    break;
                case "3":
                    System.out.println("************************************");
                    System.out.println("*   感谢使用有向图的强连通分量系统   *");
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
A B C D E F G H I J K L
A B
A H
B C
B I
C G
C H
D E
D G
E I
E F
F G
F H
F L
H J
I K
J K
K L
 */
/*
8 9
A B C D E F G H
A B
A D
B C
B H
C D
C F
C G
D E
F G
 */
