package org.jgraphl;


public interface UndirectedGraph<V> extends Graph<V> {
	default boolean isDirected() {
		return false;
	}
}
