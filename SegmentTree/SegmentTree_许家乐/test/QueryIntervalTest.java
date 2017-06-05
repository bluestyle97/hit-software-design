package segmenttreedemo.test;

import segmenttreedemo.queryinterval.SegmentTree;
public class QueryIntervalTest {
    public static void main(String[] args) {
        int[] data = {1, 4, 2, 9, 7, 3, 5, 8, 6, 0};
        SegmentTree tree= new SegmentTree(data);
        System.out.println(tree.queryIntervalMin(2, 7) + " " + tree.queryIntervalMax(2, 7) + " " + tree.queryIntervalSum(2, 7));
        tree.update(1, 5, 2);
        System.out.println(tree.queryIntervalMin(2, 7) + " " + tree.queryIntervalMax(2, 7) + " " + tree.queryIntervalSum(2, 7));
        tree.update(5, 8, -1);
        System.out.println(tree.queryIntervalMin(0, 9) + " " + tree.queryIntervalMax(0, 9) + " " + tree.queryIntervalSum(0, 9));
        tree.update(3, 6, -3);
        System.out.println(tree.queryIntervalMin(0, 9) + " " + tree.queryIntervalMax(0, 9) + " " + tree.queryIntervalSum(0, 9));
    }
}
