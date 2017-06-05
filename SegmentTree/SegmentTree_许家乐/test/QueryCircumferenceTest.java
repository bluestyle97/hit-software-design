package segmenttreedemo.test;

import segmenttreedemo.queryrectangle.RectangleEdge;
import segmenttreedemo.queryrectangle.SegmentTree;
import java.util.ArrayList;
import java.util.Collections;

public class QueryCircumferenceTest {
    public static void main(String[] args) {
        RectangleForTest rectangle1 = new RectangleForTest(1, 2, 3,3);
        RectangleForTest rectangle2 = new RectangleForTest(3,3,2,3);
        RectangleForTest rectangle3 = new RectangleForTest(6,1,2,2);
        ArrayList<RectangleForTest> rectangles = new ArrayList<>();
        rectangles.add(rectangle1);
        rectangles.add(rectangle2);
        rectangles.add(rectangle3);
        ArrayList<RectangleEdge> vEdges = new ArrayList<>();
        ArrayList<RectangleEdge> hEdges = new ArrayList<>();
        for (RectangleForTest r : rectangles) {
            vEdges.add(new RectangleEdge(r.x, r.y, r.y + r.height, 1));
            vEdges.add(new RectangleEdge(r.x + r.width, r.y, r.y + r.height, -1));
            hEdges.add(new RectangleEdge(r.y, r.x, r.x + r.width, 1));
            hEdges.add(new RectangleEdge(r.y + r.height, r.x, r.x + r.width, -1));
        }
        Collections.sort(vEdges);
        Collections.sort(hEdges);
        double[] vCoordinates = new double[vEdges.size()];
        double[] hCoordinates = new double[hEdges.size()];
        int index = 0;
        for (RectangleEdge e : vEdges) {
            if (e.weight == 1) {
                vCoordinates[index++] = e.begin;
                vCoordinates[index++] = e.end;
            }
        }
        index = 0;
        for (RectangleEdge e : hEdges) {
            if (e.weight == 1) {
                hCoordinates[index++] = e.begin;
                hCoordinates[index++] = e.end;
            }
        }
        insertionSort(vCoordinates);
        insertionSort(hCoordinates);
        SegmentTree treeScanV = new SegmentTree(vCoordinates);
        SegmentTree treeScanH = new SegmentTree(hCoordinates);
        double unionHeight = 0;
        double previousHeight = 0;
        for (RectangleEdge e : vEdges) {
            treeScanV.update(e.begin, e.end, e.weight);
            if (e.weight == 1) {
                unionHeight = unionHeight + treeScanV.getLength() - previousHeight;
            }
            previousHeight = treeScanV.getLength();
        }
        double unionWidth = 0;
        double previousWidth = 0;
        for (RectangleEdge e : hEdges) {
            treeScanH.update(e.begin, e.end, e.weight);
            if (e.weight == 1) {
                unionWidth = unionWidth + treeScanH.getLength() - previousWidth;
            }
            previousWidth = treeScanH.getLength();
        }
        double circumference = 2 * (unionHeight + unionWidth);
        System.out.println(circumference);
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
