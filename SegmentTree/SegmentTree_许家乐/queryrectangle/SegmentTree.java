package segmenttreedemo.queryrectangle;

public class SegmentTree {
    private SegmentNode root;

    public SegmentTree(double[] coordinates) {
        this.root = build(coordinates, 0, coordinates.length - 1);
    }

    public void update(double begin, double end, int edgeWeight) {
        update(this.root, begin, end, edgeWeight);
    }

    public double getLength() {
        return this.root.length;
    }

    private SegmentNode build(double[] coordinates, int left, int right) {
        if (left + 1 > right) {
            return null;
        }
        if (left + 1 == right) {
            return new SegmentNode(coordinates[left], coordinates[right]);
        }
        SegmentNode node = new SegmentNode(coordinates[left], coordinates[right]);
        node.leftChild = build(coordinates, left, (left + right) / 2);
        node.rightChild = build(coordinates, (left + right) / 2, right);
        return node;
    }

    private void update(SegmentNode node, double begin, double end, int edgeWeight) {
        if (node.left >= end || node.right <= begin) {
            return;
        }
        if (node.left >= begin && node.right <= end) {
            node.cover += edgeWeight;
            if (node.cover == 0) {
                if (node.leftChild == null) {
                    node.length = 0;
                } else {
                    node.length = node.leftChild.length + node.rightChild.length;
                }
            } else {
                node.length = node.right - node.left;
            }
            return;
        }
        update(node.leftChild, begin, end, edgeWeight);
        update(node.rightChild, begin, end, edgeWeight);
        if (node.cover == 0) {
            if (node.leftChild == null) {
                node.length = 0;
            } else {
                node.length = node.leftChild.length + node.rightChild.length;
            }
        } else {
            node.length = node.right - node.left;
        }
    }
}
