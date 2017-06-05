package segmenttreedemo.queryinterval;

class SegmentNode {
    int left;
    int right;
    int minValue;
    int maxValue;
    int sumValue;
    SegmentNode leftChild;
    SegmentNode rightChild;
    int modifyMark;

    SegmentNode(int left, int right) {
        this.left = left;
        this.right = right;
        this.minValue = 0;
        this.maxValue = 0;
        this.sumValue = 0;
        this.leftChild = null;
        this.rightChild = null;
        this.modifyMark = 0;
    }

    SegmentNode(int left, int right, int value) {
        this.left = left;
        this.right = right;
        this.minValue = value;
        this.maxValue = value;
        this.sumValue = value;
        this.leftChild = null;
        this.rightChild = null;
        this.modifyMark = 0;
    }
}
