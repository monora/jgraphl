package org.jgraphl.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.jgraphl.IncidenceGraph;
import org.jgraphl.edge.Edge;

public abstract class AbstractAdjacencyGraph<V> implements IncidenceGraph<V> {

	protected Map<V, Collection<V>> adjListMap;

	protected AbstractAdjacencyGraph(final Map<V, Collection<V>> adjListMap) {
		this.adjListMap = adjListMap;
	}

	public AbstractAdjacencyGraph() {
		this(new HashMap<V, Collection<V>>());
	}

	public long noOfVertices() {
		return adjListMap.size();
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
	public Stream<Edge<V>> outEdges(V u) {
		return adjacentVertices(u).map(v -> getEdge(u, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgraphl.IncidenceGraph#forEachAdjacentEdge(java.lang.Object,
	 * java.util.function.Consumer)
	 * 
	 * Do not use default implementation to spare the creation of the outEdges
	 * stream.
	 */
	@Override
	public void forEachAdjacentEdge(V u, Consumer<? super Edge<V>> action) {
		for (V v : getNeighborsOf(u)) {
			action.accept(getEdge(u, v));
		}
	}

	@Override
	public Stream<V> adjacentVertices(V v) {
		return getNeighborsOf(v).stream();
	}

	public boolean contains(V v) {
		return adjListMap.containsKey(v);
	}

	public boolean containsEdge(V u, V v) {
		return contains(u) && getNeighborsOf(u).contains(v);
	}

	public boolean containsEdge(Edge<V> edge) {
		return contains(edge.source())
				&& adjListMap.get(edge.source()).contains(edge.target());
	}

	@Override
	public String toString() {
		return str();
	}

	private Collection<V> getNeighborsOf(V u) {
		return adjListMap.get(u);
	}

}
