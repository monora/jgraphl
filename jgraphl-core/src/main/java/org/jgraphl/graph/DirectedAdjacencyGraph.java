package org.jgraphl.graph;

import java.util.Collection;
import java.util.Map;

import org.jgraphl.DirectedGraph;

public class DirectedAdjacencyGraph<V> extends AbstractAdjacencyGraph<V> implements DirectedGraph<V> {

	public DirectedAdjacencyGraph(Map<V, Collection<V>> adjListMap) {
		super(adjListMap);
	}
}
