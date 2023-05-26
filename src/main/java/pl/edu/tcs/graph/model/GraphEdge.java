package pl.edu.tcs.graph.model;

import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GraphEdge implements Edge {
    int id;
    int weight;
    Vertex from, to;

    public GraphEdge(Vertex from, Vertex to, int edgeWeight, int id) {
        this.from = from;
        this.to = to;
        this.weight = edgeWeight;
        this.id = id;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Collection<Vertex> getEndpoints() {
        if (from == null || to == null)
            throw new RuntimeException();
        List<Vertex> returnList = new ArrayList<>();
        returnList.add(from);
        returnList.add(to);
        return returnList;
    }

}