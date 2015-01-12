package org.jgraphl.graph.traversal.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.hamcrest.core.Is;
import org.jgraphl.Graph;
import org.jgraphl.graph.DirectedAdjacencyGraph;
import org.jgraphl.graph.traversal.BfsIterator;
import org.junit.Before;
import org.junit.Test;

public class BfsTest {

	// TODO: Is AdjacencyGraph here enough?
	private Graph<Integer> digraph;
	private BfsIterator<Integer> bfsIterator;
	private int startVertex = 1;

	@Before
	public void setUp() throws Exception {
		Map<Integer, Collection<Integer>> map = new HashMap<Integer, Collection<Integer>>();
		map.put(1, Arrays.asList(6, 2));
		map.put(2, Arrays.asList(3, 4));
		map.put(3, Collections.emptyList());
		map.put(4, Arrays.asList(5));
		map.put(5, Arrays.asList(3));
		map.put(6, Arrays.asList(4));

		digraph = new DirectedAdjacencyGraph<Integer>(map);
		bfsIterator = new BfsIterator<Integer>(digraph, startVertex);
	}

	@Test
	public void testBfsCreation() {
		assertThat(bfsIterator.getGraph(), is(digraph));
		assertThat(bfsIterator.getStartvertex(), is(startVertex));
		assertThat("Should not be at end when created", bfsIterator.hasNext(), is(true));
	}

	@Test
	public void testBfsEventhandlers() {
		String expected = "" //
				+ " examine_vertex : 1" //
				+ " examine_edge   : 1-6" //
				+ " tree_edge      : 1-6" //
				+ " examine_edge   : 1-2" //
				+ " tree_edge      : 1-2" //
				+ " finished_vertex: 1" //
				+ " examine_vertex : 6" //
				+ " examine_edge   : 6-4" //
				+ " tree_edge      : 6-4" //
				+ " finished_vertex: 6" //
				+ " examine_vertex : 2" //
				+ " examine_edge   : 2-3" //
				+ " tree_edge      : 2-3" //
				+ " examine_edge   : 2-4" //
				+ " back_edge      : 2-4" //
				+ " finished_vertex: 2" //
				+ " examine_vertex : 4" //
				+ " examine_edge   : 4-5" //
				+ " tree_edge      : 4-5" //
				+ " finished_vertex: 4" //
				+ " examine_vertex : 3" //
				+ " finished_vertex: 3" //
				+ " examine_vertex : 5" //
				+ " examine_edge   : 5-3" //
				+ " forward_edge   : 5-3" //
				+ " finished_vertex: 5";

		StringBuffer sb = new StringBuffer();
		bfsIterator.setExamineVertexListener(v -> sb.append(" examine_vertex : " + v));

		bfsIterator.setExamineEdgeListener((u, v) -> sb.append(" examine_edge   : " + u + "-" + v));
		bfsIterator.setTreeEdgeListener((u, v) -> sb.append(" tree_edge      : " + u + "-" + v));
		bfsIterator.setForwardEdgeListener((u, v) -> sb.append(" forward_edge   : " + u + "-" + v));
		bfsIterator.setBackEdgeListener((u, v) -> sb.append(" back_edge      : " + u + "-" + v));

		while (bfsIterator.hasNext()) {
			Integer v = bfsIterator.next();
			sb.append(" finished_vertex: " + v);
		}
		assertThat(sb.toString(), is(expected));
	}

	@Test
	public void testBfsStream() {
		int expected[] = { 1, 6, 2, 4, 3, 5 };
		assertThat(expected, is(bfsIterator.stream().toArray()));
	}

}
