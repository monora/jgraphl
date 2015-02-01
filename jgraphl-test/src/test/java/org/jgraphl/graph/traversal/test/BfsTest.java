package org.jgraphl.graph.traversal.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jgraphl.graph.Graphs;
import org.jgraphl.graph.UndirectedAdjacencyGraph;
import org.jgraphl.graph.traversal.BfsIterator;
import org.junit.Test;

public class BfsTest extends TraversalTest {
	private BfsIterator<Integer> bfsIterator;

	@Override
	public void setUp() throws Exception {
		super.setUp();
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
		int expectedSequenceFrom1[] = { 1, 6, 2, 4, 3, 5 };
		assertThat(expectedSequenceFrom1, is(bfsIterator.stream().toArray()));
		
		int expectedSeqFrom6[] = { 6, 4, 5, 3 };
		bfsIterator = new BfsIterator<Integer>(digraph, 6);
		assertThat(expectedSeqFrom6, is(bfsIterator.stream().toArray()));
	}

	@Test
	public void testBfsStreamUndirected() {
		int expectedSequenceFrom1[] = { 1, 2, 6, 3, 4, 5 };
		UndirectedAdjacencyGraph<Integer> graph = Graphs.toUndirectedAdjacencyGraph(digraph);
		bfsIterator = new BfsIterator<Integer>(graph, startVertex);
		Object[] traversalPath = bfsIterator.stream().toArray();
		assertThat(expectedSequenceFrom1, is(traversalPath));
		
		int expectedSeqFrom6[] = { 6, 1, 4, 2, 5, 3 };
		bfsIterator = new BfsIterator<Integer>(graph, 6);
		Object[] traversalPathFrom6 = bfsIterator.stream().toArray();
		assertThat(expectedSeqFrom6, is(traversalPathFrom6));
	}

}
