package org.jgraphl.graph.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.jgraphl.Graph;
import org.jgraphl.MutableGraph;
import org.jgraphl.graph.Graphs;
import org.junit.Before;
import org.junit.Test;

public class MutableAdjacenyGraphTest {

	private Graph<Integer> cycle;
	private MutableGraph<Integer> graphUnderTest;

	@Before
	public void setUp() throws Exception {
		// ImplicitGraph[(0-1), (1-2), (2-3), (3-0)]
		cycle = Graphs.Examples.cycle(4);
		// MutableAdjacenyGraph[(0-1), (1-2), (2-3), (3-0)]
		graphUnderTest = Graphs.toMutableDirectedAdjacenyGraph(cycle);
	}

	@Test
	public void testIsDirected() {
		assertThat(graphUnderTest.isDirected(), is(true));
	}

	@Test
	public void testAddVertex() {
		assertThat(graphUnderTest.noOfVertices(), is(4L));
		graphUnderTest.addVertex(5).addVertex(5);
		assertThat("Adding 5 two times should only increase by one",
				graphUnderTest, hasItem(5));
		assertThat(graphUnderTest.noOfVertices(), is(5L));
		graphUnderTest.addVertex(0);
		assertThat("Vertex 0 is already in", graphUnderTest.noOfVertices(),
				is(5L));
	}

	@Test
	public void testAddEdge() {
		assertThat("Should not contain the new edge",
				graphUnderTest.containsEdge(5, 6), not(true));

		graphUnderTest.addEdge(5, 6);
		assertThat(
				"Adding an edge with two vertices not already in should increase size with two",
				graphUnderTest.noOfVertices(), is(6L));
		assertThat("Should contain the two new vertices", graphUnderTest,
				hasItems(5, 6));
		assertThat("Should have one more edge", graphUnderTest.noOfEdges(),
				is(5L));
		assertThat("Should contain the new edge",
				graphUnderTest.containsEdge(5, 6), is(true));

		graphUnderTest.addEdge(graphUnderTest.getEdge(5, 6));
		assertThat("Adding the edge again should do nothing",
				graphUnderTest.noOfEdges(), is(5L));

		graphUnderTest.addEdge(graphUnderTest.getEdge(6, 5));
		assertThat("Adding the opposite edge should increase edge size by one",
				graphUnderTest.noOfEdges(), is(6L));
		assertThat("Should contain the new oposite edge",
				graphUnderTest.containsEdge(6, 5), is(true));
	}

	@Test
	public void testRemoveVertex() {
		long noOfVertices = graphUnderTest.noOfVertices();
		long noOfEdges = graphUnderTest.noOfEdges();
		graphUnderTest.removeVertex(0);
		assertThat(graphUnderTest.noOfVertices(), is(noOfVertices-1));
		assertThat(graphUnderTest.noOfEdges(), is(noOfEdges-2));
		assertThat(graphUnderTest.containsEdge(0, 1), is(false));
		assertThat(graphUnderTest.containsEdge(3, 0), is(false));
	}
	
	@Test
	public void testRemoveEdge() {
		long noOfEdges = graphUnderTest.noOfEdges();
		graphUnderTest.removeEdge(0,5);
		assertThat("Removing not existent edge should not change size", graphUnderTest.noOfEdges(), is(noOfEdges));
		
		graphUnderTest.removeEdge(0,1);
		assertThat("Edge should have been removed", graphUnderTest.containsEdge(0, 1), is(false));
		assertThat("Vertices should not have been removed", graphUnderTest, hasItems(0, 1));
		
		graphUnderTest.removeEdge(0,1);
		assertThat("Removing again should do no harm", graphUnderTest.containsEdge(0, 1), is(false));
		assertThat("The edge leading to 0 should be in.", graphUnderTest.containsEdge(3, 0), is(true));
		
		graphUnderTest.removeEdge(graphUnderTest.getEdge(3, 0));
		assertThat("The edge leading to 0 should now be ou.", graphUnderTest.containsEdge(3, 0), is(false));
		assertThat("Vertex 0 should be an isolated vertex now", graphUnderTest.contains(0), is(true));
		assertThat("Vertex 0 should be an isolated vertex now", graphUnderTest.streamOfNeighbors(0).count(), is(0L));
	}
	
	@Test
	public void testPartite() {
		Graph<String> partite = Graphs.Examples.partite(2, 3);
		assertThat(partite.sortedEdgeStreamToString(), is("(a1-b1),(a1-b2),(a1-b3),(a2-b1),(a2-b2),(a2-b3)"));
	}
	
}
