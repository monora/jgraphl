package org.jgraphl.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jgraphl.DirectedGraph;
import org.jgraphl.Graph;
import org.jgraphl.MutableGraph;

public final class Graphs {

	private Graphs() {
		throw new Error("no instances");
	}

	static public <V> AbstractAdjacencyGraph<V> toDirectedAdjacenyGraph(
			Graph<V> graph) {
		return new DirectedAdjacencyGraph<V>(createAdjacencyMap(graph));
	}

	public static <V> UndirectedAdjacencyGraph<V> toUndirectedAdjacencyGraph(
			Graph<V> other) {
		Map<V, Collection<V>> adjacencyMap = createAdjacencyMap(other);

		// add also opposite edge
		other.forEachEdge((u, v) -> adjacencyMap.get(v).add(u));
		return new UndirectedAdjacencyGraph<V>(adjacencyMap);
	}

	static public <V> MutableDirectedAdjacencyGraph<V> toMutableDirectedAdjacencyGraph(
			Graph<V> graph) {
		return new MutableDirectedAdjacencyGraph<V>(createAdjacencyMap(graph),
				() -> new HashSet<V>());
	}

	static public <V> MutableUndirectedAdjacencyGraph<V> toMutableUndirectedAdjacencyGraph(
			Graph<V> other) {
		Map<V, Collection<V>> adjacencyMap = createAdjacencyMap(other);

		// add also opposite edge
		other.forEachEdge((u, v) -> adjacencyMap.get(v).add(u));
		return new MutableUndirectedAdjacencyGraph<V>(adjacencyMap,
				() -> new HashSet<V>());
	}

	@SafeVarargs
	public static <V> DirectedGraph<V> asDirectedAdjacencyGraph(V... a) {
		MutableDirectedAdjacencyGraph<V> result = new MutableDirectedAdjacencyGraph<V>();
		for (int i = 0; i < a.length - 1; i += 2) {
			result.addEdge(a[i], a[i + 1]);
		}
		return result;
	}

	@SafeVarargs
	public static <T> UndirectedAdjacencyGraph<T> asUndirectedAdjacencyGraph(
			T... a) {
		MutableUndirectedAdjacencyGraph<T> result = new MutableUndirectedAdjacencyGraph<T>();
		for (int i = 0; i < a.length - 1; i += 2) {
			result.addEdge(a[i], a[i + 1]);
		}
		return result;
	}

	private static <V> Map<V, Collection<V>> createAdjacencyMap(Graph<V> graph) {
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
			MutableGraph<String> graph = new MutableDirectedAdjacencyGraph<String>();
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= m; j++) {
					graph.addEdge("a" + i, "b" + j);
				}
			return graph;
		}
	}
}
