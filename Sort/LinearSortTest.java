package sort;

import java.io.*;
import java.util.Random;

public class LinearSortTest {
    public static void main(String[] args) {
        writeFileRadix();
        writeFileBucket();
        writeFileCounting();
    }

    public static void writeFileRadix() {
        PrintWriter fileOutput = null;
        Random rand = new Random();
        try {
            File file = new File("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\performanceRadix.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutput = new PrintWriter(new FileWriter(file));
            for (int count = 10000; count <= 50000; count += 4000) {
                int[] data = new int[count];
                long time = 0;
                for (int i = 0; i < count; ++i) {
                    data[i] = rand.nextInt(1000000);
                }
                for (int n = 0; n < 100; ++n) {
                    long startTime = System.nanoTime();
                    RadixSort.radixSort(data);
                    long endTime = System.nanoTime();
                    time += (endTime - startTime);
                }
                fileOutput.println(count + " " + time / 100);
            }
            fileOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileOutput.close();
        }
    }

    public static void writeFileBucket() {
        PrintWriter fileOutput = null;
        Random rand = new Random();
        try {
            File file = new File("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\performanceBucket.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutput = new PrintWriter(new FileWriter(file));
            for (int count = 10000; count <= 50000; count += 4000) {
                int[] data = new int[count];
                long time = 0;
                for (int n = 0; n < 100; ++n) {
                    for (int i = 0; i < count; ++i) {
                        data[i] = rand.nextInt(1000000);
                    }
                    long startTime = System.nanoTime();
                    BucketSort.bucketSort(data);
                    long endTime = System.nanoTime();
                    time += (endTime - startTime);
                }
                fileOutput.println(count + " " + time / 100);
            }
            fileOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileOutput.close();
        }
    }

    public static void writeFileCounting() {
        PrintWriter fileOutput = null;
        Random rand = new Random();
        try {
            File file = new File("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\performanceCounting.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutput = new PrintWriter(new FileWriter(file));
            for (int count = 10000; count <= 50000; count += 4000) {
                int[] data = new int[count];
                long time = 0;
                for (int i = 0; i < count; ++i) {
                    data[i] = rand.nextInt(1000000);
                }
                for (int n = 0; n < 100; ++n) {
                    long startTime = System.nanoTime();
                    CountingSort.countingSort(data);
                    long endTime = System.nanoTime();
                    time += (endTime - startTime);
                }
                fileOutput.println(count + " " + time / 100);
            }
            fileOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileOutput.close();
        }
    }
}