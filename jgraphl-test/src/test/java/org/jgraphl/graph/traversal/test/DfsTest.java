package org.jgraphl.graph.traversal.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jgraphl.graph.Graphs;
import org.jgraphl.graph.UndirectedAdjacencyGraph;
import org.jgraphl.graph.traversal.DfsIterator;
import org.junit.Test;

public class DfsTest extends TraversalTest {
	private DfsIterator<Integer> dfsIterator;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		dfsIterator = new DfsIterator<Integer>(digraph, startVertex);
	}


	@Test
	public void testDfsCreation() {
		assertThat(dfsIterator.getGraph(), is(digraph));
		assertThat(dfsIterator.getStartvertex(), is(startVertex));
		assertThat("Should not be at end when created", dfsIterator.hasNext(), is(true));
	}

	@Test
	public void testDfsEventhandlers() {
		String expected = "" //
				+ " examine_vertex : 1" //
				+ " examine_edge   : 1-6" //
				+ " tree_edge      : 1-6" //
				+ " examine_edge   : 1-2" //
				+ " tree_edge      : 1-2" //
				+ " finished_vertex: 1" //

				+ " examine_vertex : 2" //
				+ " examine_edge   : 2-3" //
				+ " tree_edge      : 2-3" //
				+ " examine_edge   : 2-4" //
				+ " tree_edge      : 2-4" //
				+ " finished_vertex: 2" //

				+ " examine_vertex : 4" //
				+ " examine_edge   : 4-5" //
				+ " tree_edge      : 4-5" //
				+ " finished_vertex: 4" //

				+ " examine_vertex : 5" //
				+ " examine_edge   : 5-3" //
				+ " back_edge      : 5-3" //
				+ " finished_vertex: 5" //

				+ " examine_vertex : 3" //
				+ " finished_vertex: 3" //

				+ " examine_vertex : 6" //
				+ " examine_edge   : 6-4" //
				+ " forward_edge   : 6-4" //
				+ " finished_vertex: 6" //
				;

		StringBuffer sb = new StringBuffer();
		dfsIterator.setExamineVertexListener(v -> sb.append(" examine_vertex : " + v));

		dfsIterator.setExamineEdgeListener((u, v) -> sb.append(" examine_edge   : " + u + "-" + v));
		dfsIterator.setTreeEdgeListener((u, v) -> sb.append(" tree_edge      : " + u + "-" + v));
		dfsIterator.setForwardEdgeListener((u, v) -> sb.append(" forward_edge   : " + u + "-" + v));
		dfsIterator.setBackEdgeListener((u, v) -> sb.append(" back_edge      : " + u + "-" + v));

		while (dfsIterator.hasNext()) {
			Integer v = dfsIterator.next();
			sb.append(" finished_vertex: " + v);
		}
		assertThat(sb.toString(), is(expected));
	}

	@Test
	public void testDfsStream() {
		int expectedSequenceFrom1[] = { 1, 2, 4, 5, 3, 6 };
		assertThat(expectedSequenceFrom1, is(dfsIterator.stream().toArray()));
		
		int expectedSeqFrom6[] = { 6, 4, 5, 3 };
		dfsIterator = new DfsIterator<Integer>(digraph, 6);
		assertThat(expectedSeqFrom6, is(dfsIterator.stream().toArray()));
	}

	@Test
	public void testDfsStreamUndirected() {
		int expectedSequenceFrom1[] = { 1, 6, 4, 5, 3, 2 };
		UndirectedAdjacencyGraph<Integer> graph = Graphs.toUndirectedAdjacencyGraph(digraph);
		dfsIterator = new DfsIterator<Integer>(graph, startVertex);
		Object[] traversalPath = dfsIterator.stream().toArray();
		assertThat(expectedSequenceFrom1, is(traversalPath));
		
		int expectedSeqFrom6[] = { 6, 4, 5, 3, 2, 1 };
		dfsIterator = new DfsIterator<Integer>(graph, 6);
		Object[] traversalPathFrom6 = dfsIterator.stream().toArray();
		assertThat(expectedSeqFrom6, is(traversalPathFrom6));
	}

}
