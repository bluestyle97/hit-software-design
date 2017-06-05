package segmenttreedemo.queryrectangle;

class SegmentNode {
    double left;
    double right;
    double length;
    int cover;
    SegmentNode leftChild;
    SegmentNode rightChild;

    SegmentNode(double left, double right) {
        this.left = left;
        this.right = right;
        this.length = 0;
        this.cover = 0;
        this.leftChild = null;
        this.rightChild = null;
    }
}