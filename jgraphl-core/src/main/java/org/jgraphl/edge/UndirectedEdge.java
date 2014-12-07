package org.jgraphl.edge;

import org.jgraphl.edge.Edge;

public interface UndirectedEdge<V> extends Edge<V> {

	default String toStringDelimiter() {
		return "=";
	}
	
	default boolean isDirected() {
		return false;
	}
}
