package org.jgraphl;

import org.jgraphl.edge.Edge;

public interface MutableGraph<V> extends Graph<V> {
	public MutableGraph<V> addVertex(V v);

	public MutableGraph<V> addEdge(V sourceVertex, V targetVertex);

	default MutableGraph<V> addEdge(Edge<V> edge) {
		return addEdge(edge.source(), edge.target());
	}

	public MutableGraph<V> removeVertex(V v);

	public MutableGraph<V> removeEdge(V sourceVertex, V targetVertex);

	default MutableGraph<V> removeEdge(Edge<V> edge) {
		return removeEdge(edge.source(), edge.target());
	}

}
