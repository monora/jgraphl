package org.jgraphl.graph.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgraphl.Graph;
import org.jgraphl.edge.Edge;
import org.jgraphl.graph.Graphs;
import org.junit.Before;
import org.junit.Test;

public class ImplicitGraphTest {

	private Graph<Integer> graph;
	private final List<Integer> vertexlist = Arrays.asList(0, 1, 2, 3);

	@Before
	public void setUp() {
		graph = Graphs.Examples.cycle(4);
	}

	@Test
	public void testForEachVertex() {
		Set<Integer> vertices = graph.vertices();
		assertEquals(vertexlist.size(), vertices.size());
		assertTrue(vertices.containsAll(vertexlist));
	}

	@Test
	public void testNeighbors() {
		long n = graph.noOfVertices();
		for (Integer v : graph) {
			graph.forEachAdjacentVertex(v, u -> assertTrue((v+1)%n == u));
		}
	}

	@Test
	public void testEdgeStream() {
		Stream<Edge<Integer>> edgeStream = graph.edgeStream();
		assertEquals("(0-1),(1-2),(2-3),(3-0)", streamToString(edgeStream));
	}

	@Test
	public void testForEachEdge() {
		Stream<Edge<Integer>> streamOfEdges = graph.edges().stream();
		assertEquals("(0-1),(1-2),(2-3),(3-0)", streamToString(streamOfEdges));
	}

	private String streamToString(Stream<Edge<Integer>> streamOfEdges) {
		return streamOfEdges.map(Object::toString).sorted().collect(Collectors.joining(","));
	}
}
