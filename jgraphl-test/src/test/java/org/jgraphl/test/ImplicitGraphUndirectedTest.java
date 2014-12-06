package org.jgraphl.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import junit.framework.AssertionFailedError;

import org.jgraphl.Graph;
import org.jgraphl.Graphs;
import org.jgraphl.edge.DirectedEdge;
import org.jgraphl.edge.Edge;
import org.jgraphl.edge.UndirectedEdge;
import org.junit.Before;
import org.junit.Test;

public class ImplicitGraphUndirectedTest {

	private Graph<Integer> graph;
	private final List<Integer> vertexlist = Arrays.asList(0, 1, 2, 3);

	@Before
	public void setUp() {
		graph = Graphs.Examples.complete(vertexlist);
	}

	@Test
	public void testForEachVertex() {
		Set<Integer> vertices = graph.vertices();
		assertEquals(vertexlist.size(), vertices.size());
		assertTrue(vertices.containsAll(vertexlist));
	}

	@Test
	public void testNeighbors() {
		long n = graph.size() - 1;
		for (Integer v : graph) {
			graph.forEachAdjacentVertex(v, u -> assertTrue("v must not be adjacent to itself", v != u));
			assertEquals("Each vertex has n-1 neighbors", n, graph.streamOfNeighbors(v).count());
		}
	}

	@Test
	public void testEdgeStream() {
		String edges = graph.edgeStream().map(Object::toString).sorted().collect(Collectors.joining(","));
		assertEquals("(0=1),(0=2),(0=3),(1=2),(1=3),(2=3)", edges);
	}

	@Test
	public void testForEachEdge() {
		String edges = graph.edges().stream().map(Object::toString).sorted().collect(Collectors.joining(","));
		assertEquals("(0=1),(0=2),(0=3),(1=2),(1=3),(2=3)", edges);
	}
	
	@Test
	public void testCompleteObjectGraph() {
		Object object1 = new Object();
		Object object2 = new Object();
		List<Object> objectlist = Arrays.asList(object1, object2, 1, "a");
		Graph<Object> g = Graphs.Examples.complete(objectlist);
		assertTrue(g.size() == 4);
		
		assertFalse(g.contains(0));
		assertTrue(g.contains(1));
		assertTrue(g.contains("a"));
		assertTrue(g.contains(object1));
		assertTrue(g.contains(object2));

		assertTrue(g.containsEdge(object1,object2));
		assertTrue(g.containsEdge(object2,object1));
		assertTrue(g.containsEdge(object1,1));
		
		assertTrue(g.containsEdge(new UndirectedEdge<>("a",1)));
		assertTrue(g.containsEdge(new DirectedEdge<>(1, "a")));
		assertFalse(g.containsEdge(new DirectedEdge<>(1, 2)));
	}
	
	
}
