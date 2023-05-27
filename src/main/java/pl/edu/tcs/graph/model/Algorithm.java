package pl.edu.tcs.graph.model;

import java.util.Collection;

import javafx.util.Pair;
import pl.edu.tcs.graph.viewmodel.EdgeMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.VertexMiddleman;

public interface Algorithm {
  public void run(Graph g, VertexMiddleman vM, EdgeMiddleman eM, int sleepDelay,
      Collection<Pair<String, Integer>> requirements);
}

// .setColor(5, BLACK); ENUM?

// [source, 5]
// [target, 7]
// .
// .
// .
// .
// .
// .