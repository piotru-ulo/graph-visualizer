package pl.edu.tcs.graph.model;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.edu.tcs.graph.algo.AlgorithmException;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

public interface Algorithm {
	@AllArgsConstructor
	class VertexAction implements Function<Vertex, Object> {
		@Getter @Setter
		private String name;

		@Override
		public Object apply(Vertex v) {
			return f.apply(v);
		}

		@Getter @Setter
		private Function<? super Vertex, Object> f;

	}

	void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
			throws AlgorithmException;

	Collection<AlgorithmProperties> getProperties();

	Collection<VertexAction> getVertexActions();
}