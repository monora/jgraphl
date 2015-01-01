package org.jgraphl;

import java.util.stream.Stream;

import org.jgraphl.edge.Edge;


public interface DirectedGraph<V> extends Graph<V> {
	default boolean isDirected() {
		return true;
	}

	public abstract Stream<Edge<V>> outEdges(V u);
}
