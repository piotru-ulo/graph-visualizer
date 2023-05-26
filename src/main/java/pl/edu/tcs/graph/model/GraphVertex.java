package pl.edu.tcs.graph.model;

import pl.edu.tcs.graph.viewmodel.Vertex;

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