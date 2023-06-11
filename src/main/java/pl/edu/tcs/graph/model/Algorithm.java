package pl.edu.tcs.graph.model;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.edu.tcs.graph.algo.AlgorithmException;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

public interface Algorithm<V extends Vertex, E extends Edge> {
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

	void run(Graph<? extends V, ? extends E> g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
			throws AlgorithmException;

	Collection<AlgorithmProperties> getProperties();

	Collection<VertexAction> getVertexActions();
}