package test;

import hash.ConsistentHash;
import hash.PhysicalNode;
import hash.VirtualNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsistentHashTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ConsistentHash hash = null;
        System.out.println("************************************");
        System.out.println("*     欢迎使用一致性哈希测试系统     *");
        System.out.println("************************************");
        String choice = "";
        while (!choice.equals("5")) {
            System.out.println("************************************");
            System.out.println("*          1.初始化哈希环          *");
            System.out.println("*          2.添加物理节点          *");
            System.out.println("*          3.删除物理节点          *");
            System.out.println("*          4.计算物理节点          *");
            System.out.println("*          5.退出当前菜单          *");
            System.out.println("************************************");
            System.out.print("请输入您的选择：");
            choice = in.next();
            switch(choice) {
                case "1":
                    String domain, ip;
                    int port, pNodeCount, vNodeCount;
                    List<PhysicalNode> nodes = new ArrayList<>();
                    System.out.print("请输入物理节点个数：");
                    pNodeCount = in.nextInt();
                    System.out.print("请输入虚拟节点个数：");
                    vNodeCount = in.nextInt();
                    for (int i = 1; i <= pNodeCount; ++i) {
                        System.out.print("请输入节点" + i + "的域名、ip、端口号:");
                        domain = in.next();
                        ip = in.next();
                        port = in.nextInt();
                        nodes.add(new PhysicalNode(domain, ip, port));
                    }
                    hash = new ConsistentHash(nodes, vNodeCount);
                    System.out.println("哈希环初始化完成！");
                    break;
                case "2":
                    String newDomain, newIp;
                    int newPort, newVisualNodeCount;
                    System.out.print("请输入新节点的域名、ip、端口号:");
                    newDomain = in.next();
                    newIp = in.next();
                    newPort = in.nextInt();
                    System.out.print("请输入虚拟节点个数：");
                    newVisualNodeCount = in.nextInt();
                    hash.addNode(new PhysicalNode(newDomain, newIp, newPort), newVisualNodeCount);
                    System.out.println("添加物理节点成功！");
                    break;
                case "3":
                    String delDomain, delIp;
                    int delPort;
                    System.out.print("请输入待删除节点的域名、ip、端口号:");
                    delDomain = in.next();
                    delIp = in.next();
                    delPort = in.nextInt();
                    hash.removeNode(new PhysicalNode(delDomain, delIp, delPort));
                    System.out.println("删除物理节点成功！");
                    break;
                case "4":
                    String key;
                    VirtualNode vNode;
                    System.out.print("请输入关键字：");
                    key = in.next();
                    vNode = hash.getNode(key);
                    System.out.print("关键字对应的虚拟节点为：");
                    System.out.println(vNode);
                    System.out.print("关键字对应的物理节点为：");
                    System.out.println(vNode.getParent());
                    break;
                case "5":
                    System.out.println("************************************");
                    System.out.println("*     感谢使用一致性哈希测试系统     *");
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
/*
4 2
xujiale.com 192.168.52.11 4000
tanwenzhe.com 210.46.72.143 4040
liulinrang.com 192.168.1.100 5000
huanglingbo.com 192.168.34.175 6000
 */
