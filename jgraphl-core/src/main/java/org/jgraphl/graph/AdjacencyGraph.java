package org.jgraphl.graph;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.jgraphl.Graph;
import org.jgraphl.edge.Edge;

public class AdjacencyGraph<V> implements Graph<V> {

	private final Map<V, Set<V>> adjListMap;

	public AdjacencyGraph(final Map<V, Set<V>> adjListMap) {
		this.adjListMap = adjListMap;
	}

	@Override
	public Iterator<V> iterator() {
		return adjListMap.keySet().iterator();
	}

	@Override
	public void forEachAdjacentVertex(V v, Consumer<? super V> action) {
		for (V neighbor : getNeighborsOf(v)) {
			action.accept(neighbor);
		}
	}

	@Override
	public void forEachAdjacentEdge(V u, Consumer<? super Edge<V>> action) {
		for (V neighbor : getNeighborsOf(u)) {
			action.accept(getEdge(u, neighbor));
		}
	}

	@Override
	public Stream<V> streamOfNeighbors(V v) {
		return getNeighborsOf(v).stream();
	}

	public long noOfVertices() {
		return adjListMap.size();
	}

	public boolean contains(V v) {
		return adjListMap.containsKey(v);
	}

	public boolean containsEdge(V u, V v) {
		return contains(u) && getNeighborsOf(u).contains(v);
	}

	public boolean containsEdge(Edge<V> edge) {
		return contains(edge.source()) && adjListMap.get(edge.source()).contains(edge.target());
	}

	private Set<V> getNeighborsOf(V u) {
		return adjListMap.get(u);
	}
}
