package sort;


import java.util.Scanner;

public class BucketSort {
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
        bucketSort(data);
        endTime = System.nanoTime();
        for (int element : data) {
            System.out.print(element + " ");
        }
        System.out.println("用时：" + (endTime - startTime) + "ns");
    }

    public static void bucketSort(int[] data) {
        int min = data[0];
        int max = data[0];
        for (int number : data) {
            if (number < min) min = number;
            if (number > max) max = number;
        }
        int[] bucket = new int[max - min + 1];
        for (int i = 0; i < bucket.length; ++i) {
            bucket[i] = 0;
        }

        for (int i = 0; i < data.length; ++i) {
            ++bucket[data[i] - min];
        }

        int count = 0;
        for (int i = 0; i < bucket.length; ++i) {
            for (int j = 0; j < bucket[i]; ++j) {
                data[count] = i + min;
                ++count;
            }
        }
    }
}
