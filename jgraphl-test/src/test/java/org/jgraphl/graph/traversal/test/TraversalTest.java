package org.jgraphl.graph.traversal.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jgraphl.Graph;
import org.jgraphl.graph.DirectedAdjacencyGraph;
import org.junit.Before;

public abstract class TraversalTest {

	protected Graph<Integer> digraph;
	protected int startVertex = 1;
	private Map<Integer, Collection<Integer>> adjMap;

	public TraversalTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		adjMap = new HashMap<Integer, Collection<Integer>>();
		adjMap.put(1, Arrays.asList(6, 2));
		adjMap.put(2, Arrays.asList(3, 4));
		adjMap.put(3, Collections.emptyList());
		adjMap.put(4, Arrays.asList(5));
		adjMap.put(5, Arrays.asList(3));
		adjMap.put(6, Arrays.asList(4));
	
		digraph = new DirectedAdjacencyGraph<Integer>(adjMap);
	}

}