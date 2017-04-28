package test;

import redblacktree.Node;
import redblacktree.RedBlackTree;
import java.util.Scanner;

/**
 * @author Xu Jiale
 */

public class RedBlackTreeTest {
    public static void main(String[] args) {
        RedBlackTree<Double> tree = new RedBlackTree<>();
        long startTime;
        long endTime;
        double input;
        Scanner in = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("*           欢迎使用红黑树          *");
        System.out.println("************************************");
        String choice = new String();
        while (!choice.equals("5")) {
            System.out.println("************************************");
            System.out.println("*            1.插入数据            *");
            System.out.println("*            2.删除数据            *");
            System.out.println("*            3.查找数据            *");
            System.out.println("*            4.打印输出            *");
            System.out.println("*            5.退出菜单            *");
            System.out.println("************************************");
            System.out.print("请输入您的选择：");
            choice = in.next();
            switch(choice) {
                case "1":
                    System.out.print("请输入待插入数据：");
                    startTime = System.nanoTime();
                    input = in.nextDouble();
                    tree.insert(new Node<>(input));
                    endTime = System.nanoTime();
                    System.out.println("数据插入完成！");
                    System.out.println("用时：" + (endTime - startTime) + "ns");
                    break;
                case "2":
                    System.out.print("请输入待删除数据：");
                    input = in.nextDouble();
                    startTime = System.nanoTime();
                    boolean deleteResult = tree.delete(input);
                    endTime = System.nanoTime();
                    if (deleteResult)
                        System.out.println("数据删除成功！");
                    else
                        System.out.println("数据删除失败！");
                    System.out.println("用时：" + (endTime - startTime) + "ns");
                    break;
                case "3":
                    System.out.print("请输入待查找数据：");
                    input = in.nextDouble();
                    startTime = System.nanoTime();
                    Node<Double> result = tree.search(input);
                    endTime = System.nanoTime();
                    if (result != null)
                        System.out.println("数据查找成功！");
                    else
                        System.out.println("数据查找失败！");
                    System.out.println("用时：" + (endTime - startTime) + "ns");
                    break;
                case "4":
                    startTime = System.nanoTime();
                    tree.print();
                    endTime = System.nanoTime();
                    System.out.println();
                    System.out.println("用时：" + (endTime - startTime) + "ns");
                    break;
                case "5":
                    System.out.println("************************************");
                    System.out.println("*           感谢使用红黑树          *");
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
