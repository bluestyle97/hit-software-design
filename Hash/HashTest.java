package test;

import hash.Hash;
import hash.HashByMod;
import hash.HashByFold;
import hash.HashBySquareMid;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author 许家乐
 * @since 2017
 */
public class HashTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Hash<Long> hashTest;
        long value;
        long startTime;
        long endTime;
        String methodChoice;
        System.out.println("************************************");
        System.out.println("*        欢迎使用散列查找系统       *");
        System.out.println("************************************");
        System.out.println("*           请选择散列方式          *");
        System.out.println("*           1.除数取余法            *");
        System.out.println("*           2.平方取中法            *");
        System.out.println("*           3.折叠法               *");
        System.out.println("************************************");
        System.out.print("请输入您的选择：");
        methodChoice = in.next();
        switch(methodChoice) {
            case "1":
                hashTest = new Hash<>(new HashByMod());
                break;
            case "2":
                hashTest = new Hash<>(new HashBySquareMid());
                break;
            case "3":
                hashTest = new Hash<>(new HashByFold());
                break;
            default:
                System.out.println("您的输入有误，将使用默认散列函数！");
                hashTest = new Hash<>();
                break;
        }
        System.out.println("散列初始化完成！");
        String choice = "";
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
                    ArrayList<Long> input = new ArrayList<>();
                    System.out.print("请输入您要插入的数据：");
                    while (true) {
                        value = in.nextLong();
                        if (value == 0) {
                            break;
                        }
                        input.add(value);
                    }
                    Long[] values = new Long[input.size()];
                    for (int i = 0; i < input.size(); ++i) {
                        values[i] = input.get(i);
                    }
                    startTime = System.nanoTime();
                    hashTest.insert(values);
                    endTime = System.nanoTime();
                    System.out.println("数据插入成功！");
                    System.out.println("用时：" + (endTime - startTime) + "ns");
                    break;
                case "2":
                    System.out.print("请输入待删除数据：");
                    value = in.nextLong();
                    startTime = System.nanoTime();
                    boolean deleteResult = hashTest.delete(value);
                    endTime = System.nanoTime();
                    if (deleteResult) {
                        System.out.println("数据删除成功！");
                    }
                    else {
                        System.out.println("数据删除失败！");
                    }
                    System.out.println("用时：" + (endTime - startTime) + "ns");
                    break;
                case "3":
                    System.out.print("请输入待查找数据：");
                    value = in.nextLong();
                    startTime = System.nanoTime();
                    int result = hashTest.search(value);
                    endTime = System.nanoTime();
                    if (result != -1) {
                        System.out.println("数据查找成功！其哈希值为：" + result);
                    }
                    else {
                        System.out.println("数据查找失败！");
                    }
                    System.out.println("用时：" + (endTime - startTime) + "ns");
                    break;
                case "4":
                    hashTest.print();
                    break;
                case "5":
                    System.out.println("************************************");
                    System.out.println("*        感谢使用散列查找系统       *");
                    System.out.println("************************************");
                    break;
                default:
                    System.out.println("************************************");
                    System.out.println("*     您的输入有误，请重新输入！    *");
                    System.out.println("************************************");
                    break;
            }
        }
    }
}
