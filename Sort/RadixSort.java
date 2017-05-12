package sort;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class RadixSort {
    public static void main(String[] args) {
        long startTime;
        long endTime;
        Scanner in = new Scanner(System.in);
        int n;
        int[] data;

        System.out.print("请输入数据个数：");
        n = in.nextInt();
        data = new int[n];
        System.out.print("请输入数据：");
        for (int i = 0; i < n; ++i) {
            data[i] = in.nextInt();
        }
        startTime = System.nanoTime();
        radixSort(data);
        endTime = System.nanoTime();
        for (int element : data) {
            System.out.print(element + " ");
        }
        System.out.println("用时：" + (endTime - startTime) + "ns");
    }

    public static void radixSort(int[] data) {
        Queue<Integer> dataQueue = new LinkedList<>();
        ArrayList<Queue<Integer>> queues = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            queues.add(new LinkedList<>());
        }

        for (int i = 0 ; i < 10; ++i);
        int dataTmp;
        int radix;
        int figure = figure(data);

        for (int i = 0; i < data.length; ++i) {
            dataQueue.add(data[i]);
        }

        for (int i = 0; i < figure; ++i) {
            while (!dataQueue.isEmpty()) {
                dataTmp = dataQueue.poll();
                String dataString = String.valueOf(dataTmp);
                StringBuilder sb = new StringBuilder(dataString);
                sb.reverse();
                radix = (i < sb.toString().length()) ? sb.toString().charAt(i) - '0' : 0;
                queues.get(radix).add(dataTmp);
            }
            for (int j = 0; j < 10; j++) {
                while (!queues.get(j).isEmpty()) {
                    dataTmp = queues.get(j).poll();
                    dataQueue.add(dataTmp);
                }
            }
        }
        //Write sorted records back to the array
        for (int i = 0; i < data.length && !dataQueue.isEmpty(); i++) {
            dataTmp = dataQueue.poll();
            data[i] = dataTmp;
        }
    }

    private static int figure(int[] data)
    {
        String str = String.valueOf(data[0]);
        int max = str.length();
        for (int i = 1; i < data.length; i++) {
            str = String.valueOf(data[i]);
            if (str.length() > max) {
                max = str.length();
            }
        }
        return max;
    }
}