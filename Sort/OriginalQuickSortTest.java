package sort;

import java.io.*;
import java.util.Random;

public class OriginalQuickSortTest {
    public static void main(String[] args) {
        writeFileRandom();
        writeFileAscending();
        writeFileDescending();
        writeFileSame();
    }

    private static void writeFileRandom() {
        PrintWriter fileOutput = null;
        Random rand = new Random();
        try {
            File file = new File("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\originalPerformanceRandom.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutput = new PrintWriter(new FileWriter(file));
            for (int count = 10000; count <= 50000; count += 4000) {
                Integer[] data = new Integer[count];
                long time = 0;
                for (int n = 0; n < 100; ++n) {
                    for (int i = 0; i < count; ++i) {
                        data[i] = rand.nextInt(1000000);
                    }
                    long startTime = System.nanoTime();
                    QuickSort.originalQuickSort(data);
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

    private static void writeFileAscending() {
        PrintWriter fileOutput = null;
        try {
            File file = new File("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\originalPerformanceAscending.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutput = new PrintWriter(new FileWriter(file));
            for (int count = 10000; count <= 50000; count += 4000) {
                Integer[] data = new Integer[count];
                long time = 0;
                for (int i = 0; i < count; ++i) {
                    data[i] = i;
                }
                for (int n = 0; n < 100; ++n) {
                    long startTime = System.nanoTime();
                    QuickSort.originalQuickSort(data);
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

    private static void writeFileDescending() {
        PrintWriter fileOutput = null;
        try {
            File file = new File("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\originalPerformanceDescending.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutput = new PrintWriter(new FileWriter(file));
            for (int count = 10000; count <= 50000; count += 4000) {
                Integer[] data = new Integer[count];
                long time = 0;
                for (int n = 0; n < 100; ++n) {
                    for (int i = 0; i < count; ++i) {
                        data[i] = 1000000 - i;
                    }
                    long startTime = System.nanoTime();
                    QuickSort.originalQuickSort(data);
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

    private static void writeFileSame() {
        PrintWriter fileOutput = null;
        try {
            File file = new File("D:\\IDEA\\IdeaProjects\\SoftwareDesign\\file\\originalPerformanceSame.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutput = new PrintWriter(new FileWriter(file));
            for (int count = 10000; count <= 50000; count += 4000) {
                Integer[] data = new Integer[count];
                long time = 0;
                for (int i = 0; i < count; ++i) {
                    data[i] = 10000;
                }
                for (int n = 0; n < 100; ++n) {
                    long startTime = System.nanoTime();
                    QuickSort.originalQuickSort(data);
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
