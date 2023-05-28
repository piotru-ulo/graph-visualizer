package pl.edu.tcs.graph.model;

public class GridVertex extends GraphVertex{

    int x = 0;
    int y = 0;
    int width = 1;
    public GridVertex(int id) {
        super(id);
    }
    public GridVertex(int x, int y, int width) {
        super(x+y*width);
        this.x = x;
        this.y = y;
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
