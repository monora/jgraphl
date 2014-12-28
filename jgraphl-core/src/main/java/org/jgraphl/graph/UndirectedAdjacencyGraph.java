package org.jgraphl.graph;

import java.util.Collection;
import java.util.Map;

import org.jgraphl.UndirectedGraph;
import org.jgraphl.edge.Edge;

public class UndirectedAdjacencyGraph<V> extends AbstractAdjacencyGraph<V> implements UndirectedGraph<V> {

	public UndirectedAdjacencyGraph(Map<V, Collection<V>> adjListMap) {
		super(adjListMap);
	}
	
	@Override
	public boolean containsEdge(V u, V v) {
		return super.containsEdge(u, v) || super.containsEdge(v, u);
	}

	@Override
	public boolean containsEdge(Edge<V> edge) {
		return containsEdge(edge.source(), edge.target());
	}
}
