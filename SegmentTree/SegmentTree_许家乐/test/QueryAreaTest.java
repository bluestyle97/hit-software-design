package segmenttreedemo.test;

import segmenttreedemo.queryrectangle.RectangleEdge;
import segmenttreedemo.queryrectangle.SegmentTree;

import java.util.ArrayList;
import java.util.Collections;

public class QueryAreaTest {
    public static void main(String[] args) {
        RectangleEdge edge1 = new RectangleEdge(1, 2, 5, 1);
        RectangleEdge edge2 = new RectangleEdge(3, 1, 3, 1);
        RectangleEdge edge3 = new RectangleEdge(3, 4, 7, 1);
        RectangleEdge edge4 = new RectangleEdge(4, 2, 5, 1);
        RectangleEdge edge5 = new RectangleEdge(5, 2, 5, -1);
        RectangleEdge edge6 = new RectangleEdge(6, 1, 3, -1);
        RectangleEdge edge7 = new RectangleEdge(6, 4, 7, -1);
        RectangleEdge edge8 = new RectangleEdge(8, 2, 5, -1);
        ArrayList<RectangleEdge> edgeList = new ArrayList<>();
        edgeList.add(edge1);
        edgeList.add(edge2);
        edgeList.add(edge3);
        edgeList.add(edge4);
        edgeList.add(edge5);
        edgeList.add(edge6);
        edgeList.add(edge7);
        edgeList.add(edge8);
        Collections.sort(edgeList);
        double[] coordinates = new double[edgeList.size()];
        int index = 0;
        for (RectangleEdge e : edgeList) {
            if (e.weight == 1) {
                coordinates[index++] = e.begin;
                coordinates[index++] = e.end;
            }
        }
        insertionSort(coordinates);
        SegmentTree tree = new SegmentTree(coordinates);
        double area = 0;
        for (int i = 0; i < edgeList.size() - 1; ++i) {
            RectangleEdge e = edgeList.get(i);
            tree.update(e.begin, e.end, e.weight);
            area += tree.getLength() * (edgeList.get(i + 1).position - e.position);
        }
        System.out.println("Area: " + area);
    }

    private static void insertionSort(double[] array) {
        for (int i = 1; i < array.length; ++i) {
            double tmp = array[i];
            int j;
            for (j = i; j > 0 && tmp < array[j - 1]; --j)
                array[j] = array[j - 1];
            array[j] = tmp;
        }
    }
}
