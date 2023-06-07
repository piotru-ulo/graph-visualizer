package pl.edu.tcs.graph.model;

import java.util.Collection;
import java.util.Map;

import pl.edu.tcs.graph.algo.AlgorithmException;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;

public interface Algorithm {
	public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
			throws AlgorithmException;

	public Collection<AlgorithmProperties> getProperties();
}