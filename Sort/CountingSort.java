package sort;

import java.util.Scanner;

public class CountingSort {
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
        countingSort(data);
        endTime = System.nanoTime();
        for (int element : data) {
            System.out.print(element + " ");
        }
        System.out.println("用时：" + (endTime - startTime) + "ns");
    }

    public static void countingSort(int[] data) {
        int max = data[0];
        for (int number : data) {
            if (number > max) max = number;
        }

        int[] count = new int[max + 1];
        for (int i = 0; i < count.length; ++i) {
            count[i] = 0;
        }

        for (int number : data) {
            ++count[number];
        }
        for (int i = 1; i <= max; ++i) {
            count[i] = count[i] + count[i - 1];
        }

        int[] dataTmp = new int[data.length];
        for (int i = data.length - 1; i >= 0; --i) {
            int pos= count[data[i]];
            dataTmp[pos - 1] = data[i];
            --count[data[i]];
        }
        for (int i = 0; i < data.length; ++i) {
            data[i] = dataTmp[i];
        }
    }
}