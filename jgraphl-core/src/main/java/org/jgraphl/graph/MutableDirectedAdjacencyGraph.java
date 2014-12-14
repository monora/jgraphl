package org.jgraphl.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.jgraphl.MutableGraph;

public class MutableDirectedAdjacencyGraph<V> extends DirectedAdjacencyGraph<V> implements
		MutableGraph<V> {

	public MutableDirectedAdjacencyGraph(Map<V, Collection<V>> adjListMap) {
		super(adjListMap);
	}

	@Override
	public MutableDirectedAdjacencyGraph<V> addVertex(V v) {
		adjListMap.computeIfAbsent(v, u -> new HashSet<V>());
		return this;
	}

	@Override
	public MutableDirectedAdjacencyGraph<V> addEdge(V sourceVertex, V targetVertex) {
		addVertex(sourceVertex).addVertex(targetVertex);
		adjListMap.get(sourceVertex).add(targetVertex);
		return this;
	}

	@Override
	public MutableDirectedAdjacencyGraph<V> removeVertex(V v) {
		// remove vertex and all edges starting from v by removing the key
		adjListMap.remove(v);

		// Remove v from all adjacency lists
		for (Collection<V> neigborsOfV : adjListMap.values()) {
			neigborsOfV.remove(v);
		}
		return this;
	}

	@Override
	public MutableDirectedAdjacencyGraph<V> removeEdge(V sourceVertex, V targetVertex) {
		adjListMap.getOrDefault(sourceVertex, Collections.emptyList()).remove(
				targetVertex);
		return this;
	}

}
