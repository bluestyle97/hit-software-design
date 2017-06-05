package segmenttreedemo.queryinterval;

public class TwoDimensionalSegmentTree {
    private TwoDimensionalSegmentNode root;
    private static final int maxLimit = Integer.MAX_VALUE;
    private static final int minLimit = Integer.MIN_VALUE;

    public TwoDimensionalSegmentTree(int[][] array) {
        this.root = build(array, 0, array.length - 1, 0, array[0].length - 1);
    }

    public int queryIntervalMin(int rowBegin, int rowEnd, int columnBegin, int columnEnd) {
        return queryIntervalMin(this.root, rowBegin, rowEnd, columnBegin, columnEnd);
    }

    public int queryIntervalMax(int rowBegin, int rowEnd, int columnBegin, int columnEnd) {
        return queryIntervalMax(this.root, rowBegin, rowEnd, columnBegin, columnEnd);
    }

    public int queryIntervalSum(int rowBegin, int rowEnd, int columnBegin, int columnEnd) {
        return queryIntervalSum(this.root, rowBegin, rowEnd, columnBegin, columnEnd);
    }

    public void update(int rowBegin, int rowEnd, int columnBegin, int columnEnd, int modifyValue) {
        update(this.root, rowBegin, rowEnd, columnBegin, columnEnd, modifyValue);
    }

    private TwoDimensionalSegmentNode build(int[][] array, int rowLeft, int rowRight, int columnLeft, int columnRight) {
        if (rowLeft > rowRight || columnLeft > columnRight) {
            return null;
        }
        if (rowLeft == rowRight && columnLeft == columnRight) {
            return new TwoDimensionalSegmentNode(rowLeft, rowRight, columnLeft, columnRight, array[rowLeft][columnLeft]);
        }
        TwoDimensionalSegmentNode node = new TwoDimensionalSegmentNode(rowLeft, rowRight, columnLeft, columnRight);
        int rowMiddle = (rowLeft + rowRight) / 2;
        int columnMiddle = (columnLeft + columnRight) / 2;
        node.children[0] = build(array, rowLeft, rowMiddle, columnLeft, columnMiddle);
        node.children[1] = build(array, rowLeft, rowMiddle, columnMiddle + 1, columnRight);
        node.children[2] = build(array, rowMiddle + 1, rowRight, columnLeft, columnMiddle);
        node.children[3] = build(array, rowMiddle + 1, rowRight, columnMiddle + 1, columnRight);
        int[] minOfChildren = new int[4];
        int[] maxOfChildren = new int[4];
        int[] sumOfChildren = new int[4];
        for (int i = 0; i < 4; ++i) {
            if (node.children[i] != null) {
                minOfChildren[i] = node.children[i].minValue;
                maxOfChildren[i] = node.children[i].maxValue;
                sumOfChildren[i] = node.children[i].sumValue;
            } else {
                minOfChildren[i] = maxLimit;
                maxOfChildren[i] = minLimit;
                sumOfChildren[i] = 0;
            }
        }
        node.minValue = min(minOfChildren);
        node.maxValue = max(maxOfChildren);
        node.sumValue = sum(sumOfChildren);
        return node;
    }

    private int queryIntervalMin(TwoDimensionalSegmentNode node, int rowBegin, int rowEnd, int columnBegin, int columnEnd) {
        if (node == null) {
            return maxLimit;
        }
        if (node.rowLeft > rowEnd || node.rowRight < rowBegin ||
                node.columnLeft > columnEnd || node.columnRight < columnBegin) {
            return maxLimit;
        }
        if (node.rowLeft >= rowBegin && node.rowRight <= rowEnd &&
                node.columnLeft >= columnBegin && node.columnRight <= columnEnd) {
            return node.minValue;
        }
        pushDown(node);
        int[] minOfChildren = new int[4];
        for (int i = 0; i < 4; ++i) {
            minOfChildren[i] = queryIntervalMin(node.children[i], rowBegin, rowEnd, columnBegin, columnEnd);
        }
        return min(minOfChildren);
    }

    private int queryIntervalMax(TwoDimensionalSegmentNode node, int rowBegin, int rowEnd, int columnBegin, int columnEnd) {
        if (node == null) {
            return minLimit;
        }
        if (node.rowLeft > rowEnd || node.rowRight < rowBegin ||
                node.columnLeft > columnEnd || node.columnRight < columnBegin) {
            return minLimit;
        }
        if (node.rowLeft >= rowBegin && node.rowRight <= rowEnd &&
                node.columnLeft >= columnBegin && node.columnRight <= columnEnd) {
            return node.maxValue;
        }
        pushDown(node);
        int[] maxOfChildren = new int[4];
        for (int i = 0; i < 4; ++i) {
            maxOfChildren[i] = queryIntervalMax(node.children[i], rowBegin, rowEnd, columnBegin, columnEnd);
        }
        return max(maxOfChildren);
    }

    private int queryIntervalSum(TwoDimensionalSegmentNode node, int rowBegin, int rowEnd, int columnBegin, int columnEnd) {
        if (node == null) {
            return 0;
        }
        if (node.rowLeft > rowEnd || node.rowRight < rowBegin ||
                node.columnLeft > columnEnd || node.columnRight < columnBegin) {
            return 0;
        }
        if (node.rowLeft >= rowBegin && node.rowRight <= rowEnd &&
                node.columnLeft >= columnBegin && node.columnRight <= columnEnd) {
            return node.sumValue;
        }
        pushDown(node);
        int[] sumOfChildren = new int[4];
        for (int i = 0; i < 4; ++i) {
            sumOfChildren[i] = queryIntervalSum(node.children[i], rowBegin, rowEnd, columnBegin, columnEnd);
        }
        return sum(sumOfChildren);
    }

    private void update(TwoDimensionalSegmentNode node, int rowBegin, int rowEnd, int columnBegin, int columnEnd, int modifyValue) {
        if (node == null) {
            return;
        }
        if (node.rowLeft > rowEnd || node.rowRight < rowBegin ||
                node.columnLeft > columnEnd || node.columnRight < columnBegin) {
            return;
        }
        if (node.rowLeft >= rowBegin && node.rowRight <= rowEnd &&
                node.columnLeft >= columnBegin && node.columnRight <= columnEnd) {
            node.modifyMark += modifyValue;
            node.minValue += modifyValue;
            node.maxValue += modifyValue;
            node.sumValue += (node.rowRight - node.rowLeft + 1) * (node.columnRight - node.columnLeft + 1) * modifyValue;
            return;
        }
        pushDown(node);
        for (int i = 0; i < 4; ++i) {
            update(node.children[i], rowBegin, rowEnd, columnBegin, columnEnd, modifyValue);
        }
        int[] minOfChildren = new int[4];
        int[] maxOfChildren = new int[4];
        int[] sumOfChildren = new int[4];
        for (int i = 0; i < 4; ++i) {
            if (node.children[i] != null) {
                minOfChildren[i] = node.children[i].minValue;
                maxOfChildren[i] = node.children[i].maxValue;
                sumOfChildren[i] = node.children[i].sumValue;
            } else {
                minOfChildren[i] = maxLimit;
                maxOfChildren[i] = minLimit;
                sumOfChildren[i] = 0;
            }
        }
        node.minValue = min(minOfChildren);
        node.maxValue = max(maxOfChildren);
        node.sumValue = sum(sumOfChildren);
    }

    private void pushDown(TwoDimensionalSegmentNode node) {
        if (node.modifyMark != 0) {
            for (TwoDimensionalSegmentNode childNode : node.children) {
                childNode.modifyMark += node.modifyMark;
                childNode.minValue += node.modifyMark;
                childNode.maxValue += node.modifyMark;
                childNode.sumValue = (childNode.rowRight - childNode.rowLeft + 1) *
                        (childNode.columnRight - childNode.columnLeft + 1) * node.modifyMark;
            }

            node.modifyMark = 0;
        }
    }

    private static int min(int[] array) {
        int min = array[0];
        for (int i : array) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    private static int max(int[] array) {
        int max = array[0];
        for (int i : array) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    private static int sum(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }
}
