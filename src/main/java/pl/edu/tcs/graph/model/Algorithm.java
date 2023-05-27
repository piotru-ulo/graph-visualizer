package pl.edu.tcs.graph.model;

import java.util.*;

import javafx.util.Pair;
import pl.edu.tcs.graph.algo.AlgorithmException;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;

public interface Algorithm {
  public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
      throws AlgorithmException;
  public Set<AlgorithmProperties> getProperties();
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