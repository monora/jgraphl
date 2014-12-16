package org.jgraphl.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jgraphl.Graph;
import org.jgraphl.MutableGraph;

public final class Graphs {

	private Graphs() {
		throw new Error("no instances");
	}

	static public <V> DirectedAdjacencyGraph<V> toAdjacenyGraph(Graph<V> graph) {
		return new DirectedAdjacencyGraph<V>(createAdjacenyMap(graph));
	}

	static public <V> MutableDirectedAdjacencyGraph<V> toMutableAdjacenyGraph(
			Graph<V> graph) {
		return new MutableDirectedAdjacencyGraph<V>(createAdjacenyMap(graph),
				() -> new HashSet<V>());
	}

	private static <V> Map<V, Collection<V>> createAdjacenyMap(Graph<V> graph) {
		Map<V, Collection<V>> adjListMap = new HashMap<V, Collection<V>>();
		for (V u : graph) {
			HashSet<V> adjList = new HashSet<V>();
			adjListMap.put(u, adjList);
			graph.forEachAdjacentVertex(u, v -> adjList.add(v));
		}
		return adjListMap;
	}

	static public final class Examples {

		public static Graph<Integer> cycle(int n) {
			Supplier<Stream<Integer>> x = () -> Stream.iterate(0,
					i -> Integer.valueOf(i + 1)).limit(n);
			return new ImplicitGraph.Builder<Integer>()
					.neighborIterator(y -> Stream.of((y + 1) % n))
					.vertexIterator(x).build();
		}

		/**
		 * @param col
		 *            used as vertex set of the resulting graph
		 * @return a complete undirected graph having col as vertex set
		 */
		public static <V> Graph<V> complete(Collection<V> col) {
			return new ImplicitGraph.Builder<V>()
					.vertexIterator(() -> col.stream())
					.neighborIterator(v -> col.stream().filter(u -> u != v))
					.directed(false).build();
		}

		public static MutableGraph<String> partite(int n, int m) {

			Map<String, Collection<String>> adjListMap = new HashMap<String, Collection<String>>();
			MutableGraph<String> graph = new MutableDirectedAdjacencyGraph<String>(
					adjListMap);
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= m; j++) {
					graph.addEdge("a" + i, "b" + j);
				}
			return graph;
		}
	}
}
