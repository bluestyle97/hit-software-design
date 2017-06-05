package segmenttreedemo.test;

import segmenttreedemo.queryinterval.TwoDimensionalSegmentTree;

public class TwoDimensionalQueryIntervalTest {
    public static void main(String[] args) {
        int[][] data = {{1, 7, 4, 2, 11, 9, 8},
                        {6, 0, 7, 5, 3, 4, 13},
                        {5, 9, 2, 3, 7, 6, 2},
                        {10, 8, 3, 9, 0, 7, 5},
                        {8, 15, 10, 5, 6, 9, 7},
                        {13, 7, 5, 0, 9, 10, 8}};
        TwoDimensionalSegmentTree tree= new TwoDimensionalSegmentTree(data);
        System.out.println(tree.queryIntervalMin(1, 3, 2,5) + " " +
                tree.queryIntervalMax(1, 3, 2,5) + " " +
                tree.queryIntervalSum(1, 3, 2,5));
        System.out.println(tree.queryIntervalMin(0, 5, 0, 6) + " " +
                tree.queryIntervalMax(0, 5, 0, 6) + " " +
                tree.queryIntervalSum(0, 5, 0, 6));
        tree.update(1, 4,  2, 5, 6);
        System.out.println(tree.queryIntervalMin(1, 3, 2,5) + " " +
                tree.queryIntervalMax(1, 3, 2,5) + " " +
                tree.queryIntervalSum(1, 3, 2,5));
        System.out.println(tree.queryIntervalMin(0, 5, 0, 6) + " " +
                tree.queryIntervalMax(0, 5, 0, 6) + " " +
                tree.queryIntervalSum(0, 5, 0, 6));
    }
}
