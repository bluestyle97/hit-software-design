import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by 许家乐 on 2017/4/10.
 */
public class StronglyConnected {
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
            }
        } catch (FileNotFoundException e) {
            System.exit(0);
        }
    }
    public static void initialize(Graph g) {
        Scanner in = new Scanner(System.in);
        System.out.print("请输入顶点数和边数：");
        int v = in.nextInt();
        int e = in.nextInt();
        System.out.print("请输入各结点名称：");
        for (int i = 0; i < v; ++i) {
            String str = in.next();
            g.addVertex(new Vertex(str));
        }
        System.out.println("请输入各边起点和终点：");
        for (int i = 0; i < e; ++i) {
            String head = in.next();
            String tail = in.next();
            g.addEdge(head, tail);
        }
    }

    public static void main(String[] args) {
        int counter;
        long startTime;
        long endTime;
        Scanner in = new Scanner(System.in);
        Graph g = null;
        System.out.println("************************************");
        System.out.println("*   欢迎使用有向图的强连通分量系统   *");
        System.out.println("************************************");
        String choice = new String();
        while (!choice.equals("3")) {
            System.out.println("************************************");
            System.out.println("*          1.初始化有向图           *");
            System.out.println("*          2.求强连通分量           *");
            System.out.println("*          3.退出当前菜单           *");
            System.out.println("************************************");
            System.out.print("请输入您的选择：");
            choice = in.next();
            switch(choice) {
            case "1": {
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
                        System.out.println("有向图初始化完成！");
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    case "2":
                        startTime = System.nanoTime();
                        initializeFile(g);
                        endTime = System.nanoTime();
                        System.out.println("有向图初始化完成！");
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    default:
                        System.out.println("您的输入有误！");
                        break;
                }
                break;
            }
            case "2": {
                String menu1 = "";
                while (!menu1.equals("4")) {
                    System.out.println("************************************");
                    System.out.println("*          1.使用Kosaraju算法       *");
                    System.out.println("*          2.使用Tarjan算法         *");
                    System.out.println("*          3.使用Gabow算法          *");
                    System.out.println("*          4.退出子菜单             *");
                    System.out.println("************************************");
                    System.out.print("请输入您的选择：");
                    menu1 = in.next();
                    switch(menu1) {
                    case "1":
                        counter = 1;
                        startTime = System.nanoTime();
                        g.kosaraju();
                        endTime = System.nanoTime();
                        System.out.println("该有向图中的强连通分量个数为：" + g.components.size());
                        for (HashSet<Vertex> component : g.components)
                            System.out.println("component " + counter++ + ": "+component);
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    case "2":
                        counter = 1;
                        startTime = System.nanoTime();
                        g.tarjan();
                        endTime = System.nanoTime();
                        System.out.println("该有向图中的强连通分量个数为：" + g.components.size());
                        for (HashSet<Vertex> component : g.components)
                            System.out.println("component " + counter++ + ": "+component);
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
                    case "3":
                        counter = 1;
                        startTime = System.nanoTime();
                        g.gabow();
                        endTime = System.nanoTime();
                        System.out.println("该有向图中的强连通分量个数为：" + g.components.size());
                        for (HashSet<Vertex> component : g.components)
                            System.out.println("component " + counter++ + ": "+component);
                        System.out.println("用时：" + (endTime - startTime) + "ns");
                        break;
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
12 17
A B C D E F G H I J K L
A B
A H
B C
B I
C G
D G
E D
E I
F E
G F
H C
H F
I K
J H
J K
K L
L F
 */

/*
5 7
A B C D E
A B
A C
B D
C B
C E
D C
E D
 */