package segmenttreedemo.queryrectangle;

public class RectangleEdge implements Comparable<RectangleEdge> {
    public double position;
    public double begin;
    public double end;
    public int weight;

    public RectangleEdge(double position, double begin, double end, int weight) {
        this.position = position;
        this.begin = Math.min(begin, end);
        this.end = Math.max(begin, end);
        this.weight = weight;
    }

    @Override
    public int compareTo(RectangleEdge edge) {
        if (this.position < edge.position ) {
            return -1;
        } else if (this.position > edge.position) {
            return 1;
        }
        return 0;
    }
}
