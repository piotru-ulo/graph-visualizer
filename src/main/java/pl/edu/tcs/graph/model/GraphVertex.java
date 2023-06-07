package pl.edu.tcs.graph.model;

import pl.edu.tcs.graph.viewmodel.Vertex;

class GraphVertex implements Vertex {
    int id;
    boolean active = true;

    public GraphVertex(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean a) {
        active = a;
    }
}