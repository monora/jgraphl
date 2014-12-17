package org.jgraphl.graph.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.jgraphl.Graph;
import org.jgraphl.edge.Edge;
import org.jgraphl.graph.Graphs;
import org.junit.Before;
import org.junit.Test;

public class UndirectedAdjacencyGraphTest {

	private Graph<Integer> graphUnderTest;
	private Graph<Integer> cycle;

	@Before
	public void setUp() {
		cycle = Graphs.Examples.cycle(4);
		graphUnderTest = Graphs.toUndirectedAdjacenyGraph(cycle);
	}

	@Test
	public void testContainment() {
		assertTrue(graphUnderTest.contains(1));
		assertTrue(graphUnderTest.containsEdge(0, 1));
		assertTrue(graphUnderTest.containsEdge(1, 2));
		assertTrue(graphUnderTest.containsEdge(2, 3));
		assertTrue(graphUnderTest.containsEdge(3, 0));
		assertTrue(graphUnderTest.containsEdge(graphUnderTest.getEdge(3, 0)));
		assertTrue(graphUnderTest.containsEdge(1, 0));
		assertTrue(graphUnderTest.containsEdge(graphUnderTest.getEdge(0, 3)));

		assertFalse(graphUnderTest.contains(10));
		assertFalse(graphUnderTest.containsEdge(0, 5));
	}

	@Test
	public void testVertices() {
		assertThat(graphUnderTest.isDirected(), is(false));
		assertThat(graphUnderTest.noOfVertices(), is(4L));
		assertThat(graphUnderTest.vertices(), is(cycle.vertices()));
		assertThat(graphUnderTest.edges(), not(cycle.edges()));
	}

	@Test
	public void testStreamOfNeighbors() {
		assertThat(graphUnderTest.streamOfNeighbors(1).count(), is(2L));
		int[] neighborsOf1 = {0,2};
		assertThat(graphUnderTest.streamOfNeighbors(1).sorted().toArray(), is(neighborsOf1));
	}

	@Test
	public void testEdgeStream() {
		assertThat(graphUnderTest.edgeStream().count(), is(4L));
	}

	@Test
	public void testforEachAdjacentEdge() {
		Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
		
		graphUnderTest.forEachAdjacentEdge(2, edge -> edges.add(edge));
		assertThat(edges.size(), is(2));
		assertThat(edges.contains(graphUnderTest.getEdge(1, 2)), is(true));
		assertThat(edges.contains(graphUnderTest.getEdge(3, 2)), is(true));
	}
}
