package org.jgraphl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgraphl.Graph;
import org.jgraphl.edge.DefaultDirectedEdge;
import org.jgraphl.edge.DefaultUndirectedEdge;
import org.jgraphl.edge.DirectedEdge;
import org.jgraphl.edge.Edge;
import org.jgraphl.edge.UndirectedEdge;
import org.jgraphl.graph.Graphs;
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
		long n = graph.noOfVertices() - 1;
		for (Integer v : graph) {
			graph.forEachAdjacentVertex(v,
					u -> assertTrue("v must not be adjacent to itself", v != u));
			assertEquals("Each vertex has n-1 neighbors", n, graph
					.streamOfNeighbors(v).count());
		}
	}

	@Test
	public void testEdgeStream() {
		String edges = graph.edgeStream().map(Object::toString).sorted()
				.collect(Collectors.joining(","));
		assertEquals("(0=1),(0=2),(0=3),(1=2),(1=3),(2=3)", edges);
	}

	@Test
	public void testForEachEdge() {
		String edges = graph.edges().stream().map(Object::toString).sorted()
				.collect(Collectors.joining(","));
		assertEquals("(0=1),(0=2),(0=3),(1=2),(1=3),(2=3)", edges);
	}

	@Test
	public void testCompleteObjectGraph() {
		Object object1 = new Object();
		Object object2 = new Object();
		List<Object> objectlist = Arrays.asList(object1, object2, 1, "a");
		Graph<Object> undirected = Graphs.Examples.complete(objectlist);
		assertFalse("The complete graph should be undirected",
				undirected.isDirected());
		assertThat(undirected.noOfVertices(), is(4L));
		assertThat(undirected.noOfEdges(), is(6L));

		assertFalse(undirected.contains(0));
		assertTrue(undirected.contains(1));
		assertTrue(undirected.contains("a"));
		assertTrue(undirected.contains(object1));
		assertTrue(undirected.contains(object2));

		assertTrue(undirected.containsEdge(object1, object2));
		assertTrue(undirected.containsEdge(object2, object1));
		assertTrue(undirected.containsEdge(object1, 1));

		UndirectedEdge<Object> undirectedEdge = new DefaultUndirectedEdge<>(
				"a", 1);
		assertTrue(undirected.containsEdge(undirectedEdge));

		DirectedEdge<Object> directedEdge = new DefaultDirectedEdge<>(1, "a");
		assertThat("A directed edge is not contained in an undirected graph",
				not(undirected.containsEdge(directedEdge)));

		Edge<Object> makedEdge = undirected.getEdge(1, "a");
		assertEquals("An undirected Graph should make undirected edges",
				undirectedEdge, makedEdge);
		assertTrue(undirected.containsEdge(makedEdge));
		assertFalse(undirected.containsEdge(new DefaultDirectedEdge<>(1, 2)));
	}
}
