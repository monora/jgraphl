package org.jgraphl.graph.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.jgraphl.Graph;
import org.jgraphl.edge.Edge;
import org.jgraphl.graph.Graphs;
import org.junit.Before;
import org.junit.Test;

public class DirectedAdjacencyGraphTest {

	private Graph<Integer> graphUnderTest;
	private Graph<Integer> cycle;

	@Before
	public void setUp() {
		cycle = Graphs.Examples.cycle(4);
		graphUnderTest = Graphs.toDirectedAdjacenyGraph(cycle);
	}

	@Test
	public void testContainment() {
		assertTrue(graphUnderTest.contains(1));
		assertTrue(graphUnderTest.containsEdge(0, 1));
		assertTrue(graphUnderTest.containsEdge(1, 2));
		assertTrue(graphUnderTest.containsEdge(2, 3));
		assertTrue(graphUnderTest.containsEdge(3, 0));
		assertTrue(graphUnderTest.containsEdge(graphUnderTest.getEdge(3, 0)));
	
		assertFalse(graphUnderTest.contains(10));
		assertFalse(graphUnderTest.containsEdge(1,0));
		assertFalse(graphUnderTest.containsEdge(0,5));
		assertFalse(graphUnderTest.containsEdge(graphUnderTest.getEdge(0, 3)));
	}

	@Test
	public void testVertices() {
		assertThat(graphUnderTest.isDirected(), is(true));
		assertThat(graphUnderTest.noOfVertices(), is(4L));
		assertThat(graphUnderTest.vertices(), is(cycle.vertices()));
		assertThat(graphUnderTest.edges(), is(cycle.edges()));
	}

	@Test
	public void testStreamOfNeighbors() {
		assertThat(graphUnderTest.adjacentVertices(1).count(), is(1L));
		assertThat(graphUnderTest.adjacentVertices(1).findFirst().get(), is(2));
	}
	
	@Test
	public void testEdgeStream() {
		assertThat(graphUnderTest.edgeStream().count(), is(4L));
	}
	
	@Test
	public void testforEachAdjacentEdge() {
		ArrayList<Edge<Integer>> edges = new ArrayList<>();
		graphUnderTest.forEachAdjacentEdge(2, edge -> {
			edges.add(edge);
		});
		assertThat(edges.toString(), is("[(2-3)]"));
	}
}
