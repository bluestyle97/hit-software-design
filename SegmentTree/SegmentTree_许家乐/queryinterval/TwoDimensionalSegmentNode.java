package segmenttreedemo.queryinterval;

class TwoDimensionalSegmentNode {
    int rowLeft;
    int rowRight;
    int columnLeft;
    int columnRight;
    int minValue;
    int maxValue;
    int sumValue;
    TwoDimensionalSegmentNode[] children;
    int modifyMark;
    
    TwoDimensionalSegmentNode(int rowLeft, int rowRight, int columnLeft, int columnRight) {
        this.rowLeft = rowLeft;
        this.rowRight = rowRight;
        this.columnLeft = columnLeft;
        this.columnRight = columnRight;
        this.minValue = 0;
        this.maxValue = 0;
        this.sumValue = 0;
        this.children = new TwoDimensionalSegmentNode[4];
        this.modifyMark = 0;
    }
    
    TwoDimensionalSegmentNode(int rowLeft, int rowRight, int columnLeft, int columnRight, int value) {
        this.rowLeft = rowLeft;
        this.rowRight = rowRight;
        this.columnLeft = columnLeft;
        this.columnRight = columnRight;
        this.minValue = value;
        this.maxValue = value;
        this.sumValue = value;
        this.children = new TwoDimensionalSegmentNode[4];
        this.modifyMark = 0;
    }
}
