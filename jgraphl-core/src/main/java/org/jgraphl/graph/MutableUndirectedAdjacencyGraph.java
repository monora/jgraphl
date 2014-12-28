package org.jgraphl.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

import org.jgraphl.MutableGraph;
import org.jgraphl.UndirectedGraph;

public class MutableUndirectedAdjacencyGraph<V> extends UndirectedAdjacencyGraph<V>
		implements MutableGraph<V>, UndirectedGraph<V> {

	private Supplier<Collection<V>> adjListSupplier;

	public MutableUndirectedAdjacencyGraph(Map<V, Collection<V>> adjListMap,
			Supplier<Collection<V>> adjListSupplier) {
		super(adjListMap);
		this.adjListSupplier = adjListSupplier;
	}

	public MutableUndirectedAdjacencyGraph(Map<V, Collection<V>> adjListMap) {
		this(adjListMap, () -> new HashSet<V>());
	}

	public MutableUndirectedAdjacencyGraph() {
		this(new HashMap<V,Collection<V>>());
	}

	@Override
	public MutableUndirectedAdjacencyGraph<V> addVertex(V v) {
		adjListMap.computeIfAbsent(v, u -> adjListSupplier.get());
		return this;
	}

	@Override
	public MutableUndirectedAdjacencyGraph<V> addEdge(V sourceVertex,
			V targetVertex) {
		addVertex(sourceVertex).addVertex(targetVertex);
		adjListMap.get(sourceVertex).add(targetVertex);
		adjListMap.get(targetVertex).add(sourceVertex);
		return this;
	}

	@Override
	public MutableUndirectedAdjacencyGraph<V> removeVertex(V v) {
		// remove vertex and all edges starting from v by removing the key
		adjListMap.remove(v);

		// Remove v from all adjacency lists
		for (Collection<V> neigborsOfV : adjListMap.values()) {
			neigborsOfV.remove(v);
		}
		return this;
	}

	@Override
	public MutableUndirectedAdjacencyGraph<V> removeEdge(V sourceVertex,
			V targetVertex) {
		adjListMap.getOrDefault(sourceVertex, Collections.emptyList()).remove(
				targetVertex);
		adjListMap.getOrDefault(targetVertex, Collections.emptyList()).remove(
				sourceVertex);
		return this;
	}

}
