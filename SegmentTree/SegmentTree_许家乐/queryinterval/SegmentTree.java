package segmenttreedemo.queryinterval;

import java.lang.Math;

public class SegmentTree {
    private SegmentNode root;
    private static final int maxLimit = Integer.MAX_VALUE;
    private static final int minLimit = Integer.MIN_VALUE;

    public SegmentTree(int[] array) {
        this.root = build(array, 0, array.length - 1);
    }

    public int queryIntervalMin(int begin, int end) {
        return queryIntervalMin(this.root, begin, end);
    }

    public int queryIntervalMax(int begin, int end) {
        return queryIntervalMax(this.root, begin, end);
    }

    public int queryIntervalSum(int begin, int end) {
        return queryIntervalSum(this.root, begin, end);
    }

    public void update(int begin, int end, int modifyValue) {
        update(this.root, begin, end, modifyValue);
    }

    private SegmentNode build(int[] array, int left, int right) {
        if (left > right) {
            return null;
        }
        if (left == right) {
            return new SegmentNode(left, right, array[left]);
        }
        SegmentNode node = new SegmentNode(left, right);
        node.leftChild = build(array, left, (left + right) / 2);
        node.rightChild = build(array, (left + right) / 2 + 1, right);
        node.minValue = Math.min(node.leftChild.minValue, node.rightChild.minValue);
        node.maxValue = Math.max(node.leftChild.maxValue, node.rightChild.maxValue);
        node.sumValue = node.leftChild.sumValue + node.rightChild.sumValue;
        return node;
    }

    private int queryIntervalMin(SegmentNode node, int begin, int end) {
        if (node.left > end || node.right < begin) {
            return maxLimit;
        }
        if (node.left >= begin && node.right <= end) {
            return node.minValue;
        }
        pushDown(node);
        int valueLeft = queryIntervalMin(node.leftChild, begin, end);
        int valueRight = queryIntervalMin(node.rightChild, begin, end);
        return Math.min(valueLeft, valueRight);
    }

    private int queryIntervalMax(SegmentNode node, int begin, int end) {
        if (node.left > end || node.right < begin) {
            return minLimit;
        }
        if (node.left >= begin && node.right <= end) {
            return node.maxValue;
        }
        pushDown(node);
        int valueLeft = queryIntervalMax(node.leftChild, begin, end);
        int valueRight = queryIntervalMax(node.rightChild, begin, end);
        return Math.max(valueLeft, valueRight);
    }

    private int queryIntervalSum(SegmentNode node, int begin, int end) {
        if (node.left > end || node.right < begin) {
            return 0;
        }
        if (node.left >= begin && node.right <= end) {
            return node.sumValue;
        }
        pushDown(node);
        int valueLeft = queryIntervalSum(node.leftChild, begin, end);
        int valueRight = queryIntervalSum(node.rightChild, begin, end);
        return valueLeft + valueRight;
    }

    private void update(SegmentNode node, int begin, int end, int modifyValue) {
        if (node.left > end || node.right < begin) {
            return;
        }
        if (node.left >= begin && node.right <= end) {
            node.modifyMark += modifyValue;
            node.minValue += modifyValue;
            node.maxValue += modifyValue;
            node.sumValue += (node.right - node.left + 1) * modifyValue;
            return;
        }
        pushDown(node);
        update(node.leftChild, begin, end, modifyValue);
        update(node.rightChild, begin, end, modifyValue);
        node.minValue = Math.min(node.leftChild.minValue, node.rightChild.minValue);
        node.maxValue = Math.max(node.leftChild.maxValue, node.rightChild.maxValue);
        node.sumValue = node.leftChild.sumValue + node.rightChild.sumValue;
    }

    private void pushDown(SegmentNode node) {
        if (node.modifyMark != 0) {
            node.leftChild.modifyMark += node.modifyMark;
            node.rightChild.modifyMark += node.modifyMark;

            node.leftChild.minValue += node.modifyMark;
            node.leftChild.maxValue += node.modifyMark;
            node.leftChild.sumValue += (node.leftChild.right - node.leftChild.left + 1) * node.modifyMark;
            node.rightChild.minValue += node.modifyMark;
            node.rightChild.maxValue += node.modifyMark;
            node.rightChild.sumValue += (node.rightChild.right - node.rightChild.left + 1) * node.modifyMark;

            node.modifyMark = 0;
        }
    }
}
