package pl.edu.tcs.graph;

class GraphVertex implements Vertex {
    int id;

    public GraphVertex(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}