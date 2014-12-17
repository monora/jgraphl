package org.jgraphl.graph;

import java.util.Collection;
import java.util.Map;

public class DirectedAdjacencyGraph<V> extends AbstractAdjacencyGraph<V> {

	public DirectedAdjacencyGraph(final Map<V, Collection<V>> adjListMap) {
		super(adjListMap);
	}
}
