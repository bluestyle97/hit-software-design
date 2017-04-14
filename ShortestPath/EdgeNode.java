/**
 * Created by 许家乐 on 2017/4/12.
 */
public class EdgeNode {
    public Vertex end;
    public int weight;

    public EdgeNode() {
        this.end = null;
        this.weight = 0;
    }

    public EdgeNode(Vertex v, int w) {
        this.end = v;
        this.weight = w;
    }
}
