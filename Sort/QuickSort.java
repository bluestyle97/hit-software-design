package sort;

import java.util.Scanner;

public class QuickSort {
    private static final int CUTOFF = 10;

    public static void main(String[] args) {
        long startTime;
        long endTime;
        Scanner in = new Scanner(System.in);
        int n;
        Integer[] data;

        System.out.print("请输入数据个数：");
        n = in.nextInt();
        data = new Integer[n];
        System.out.print("请输入数据：");
        for (int i = 0; i < n; ++i) {
            data[i] = in.nextInt();
        }
        startTime = System.nanoTime();
        quickSort(data);
        endTime = System.nanoTime();
        for (Integer element : data) {
            System.out.print(element + " ");
        }
        System.out.println("用时：" + (endTime - startTime) + "ns");
    }

    private static <AnyType extends Comparable<? super AnyType>>
    void insertionSort(AnyType[] data, int start, int end) {
        int j;

        for (int i = start + 1; i <= end; ++i) {
            AnyType tmp = data[i];
            for (j = i; j > start && tmp.compareTo(data[j - 1]) < 0; --j) {
                data[j] = data[j - 1];
            }
            data[j] = tmp;
        }
    }

    public static <AnyType extends Comparable<? super AnyType>>
    void quickSort(AnyType[] data) {
        quickSort(data, 0, data.length - 1);
    }

    public static <AnyType extends Comparable<? super AnyType>>
    void quickSort(AnyType[] data, int low, int high)
    {
        if (low + CUTOFF > high) {
            insertionSort(data, low, high);
            return;
        }
        AnyType pivot = findPivot(data, low, high);
        int first = low;
        int last = high;
        int left = low;
        int right = high;
        int leftLength = 0;
        int rightLength = 0;
        while (first < last) {
            while (first < last && data[last].compareTo(pivot) >= 0) {
                if (data[last].compareTo(pivot) == 0) {
                    swapReferences(data, last, right);
                    --right;
                    ++rightLength;
                }
                --last;
            }
            data[first] = data[last];
            while (first < last && data[first].compareTo(pivot) <= 0) {
                if (data[first].compareTo(pivot) == 0) {
                    swapReferences(data, first, left);
                    ++left;
                    ++leftLength;
                }
                ++first;
            }
            data[last] = data[first];
        }
        data[first] = pivot;

        int i = first - 1;
        int j = low;
        while (j < left && data[i].compareTo(pivot) != 0) {
            swapReferences(data, i, j);
            --i;
            ++j;
        }
        i = last + 1;
        j = high;
        while (j > right && data[i].compareTo(pivot) != 0) {
            swapReferences(data, i, j);
            ++i;
            --j;
        }
        quickSort(data, low, first - leftLength - 1);
        quickSort(data, first + rightLength + 1, high);
    }

    private static <AnyType extends Comparable<? super AnyType>>
    void swapReferences(AnyType[] data, int i, int j) {
        AnyType tmp;
        tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private static <AnyType extends Comparable<? super AnyType>>
    AnyType findPivot(AnyType[] data, int left, int right) {
        int middle = (left + right) / 2;

        if (data[middle].compareTo(data[right]) > 0) {
            swapReferences(data, left, middle);
        }
        if (data[left].compareTo(data[right]) > 0) {
            swapReferences(data, left, right);
        }
        if (data[middle].compareTo(data[left]) > 0) {
            swapReferences(data, middle, right);
        }
        return data[left];
    }

    public static <AnyType extends Comparable<? super AnyType>>
    void originalQuickSort(AnyType[] data) {
        originalQuickSort(data, 0, data.length - 1);
    }

    public static <AnyType extends Comparable<? super AnyType>>
    void originalQuickSort(AnyType[] data, int left, int right) {
        if (left + CUTOFF > right) {
            insertionSort(data, left, right);
            return;
        }
        AnyType pivot = originalFindPivot(data, left, right);
        int i = left;
        int j = right - 1;
        while (true) {
            while (data[++i].compareTo(pivot) < 0) {}
            while (data[--j].compareTo(pivot) > 0) {}
            if (i < j) {
                swapReferences(data, i, j);
            } else {
                break;
            }
        }
        swapReferences(data, i, right - 1);
        originalQuickSort(data, left, i - 1);
        originalQuickSort(data, i + 1, right);
    }

    private static <AnyType extends Comparable<? super AnyType>>
    AnyType originalFindPivot(AnyType[] data, int left, int right) {
        int middle = (left + right) / 2;
        if (data[left].compareTo(data[middle]) > 0) {
            swapReferences(data, left, middle);
        }
        if (data[left].compareTo(data[right]) > 0) {
            swapReferences(data, left, right);
        }
        if (data[middle].compareTo(data[right]) > 0) {
            swapReferences(data, middle, right);
        }

        swapReferences(data, middle, right - 1);
        return data[right - 1];
    }
}
