package org.jgraphl;


public interface DirectedGraph<V> extends Graph<V> {
	default boolean isDirected() {
		return true;
	}
}
