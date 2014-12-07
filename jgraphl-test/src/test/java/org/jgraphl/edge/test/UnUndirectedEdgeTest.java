package org.jgraphl.edge.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.jgraphl.edge.DefaultDirectedEdge;
import org.jgraphl.edge.DefaultUndirectedEdge;
import org.jgraphl.edge.UndirectedEdge;
import org.junit.Before;
import org.junit.Test;

public class UnUndirectedEdgeTest {

	private UndirectedEdge<Integer> edge;

	@Before
	public void createEdge(){
		edge = new DefaultUndirectedEdge<Integer>(1,2);
	}
	@Test
	public void testEqualsObject() {
		assertThat(edge, is(equalTo(new DefaultUndirectedEdge<>(1,2))));
		assertThat(edge, is(equalTo(new DefaultUndirectedEdge<>(2,1))));
		assertThat("An undirected edge can not be equal to a directed edge",
				edge, not(equalTo(new DefaultDirectedEdge<>(1,2))));
		assertThat("A directed edge can not be equal to a undirected edge",
				new DefaultDirectedEdge<>(1,2), not(equalTo(edge)));
	}

	@Test
	public void testHashCode() {
		assertThat(edge.hashCode(), is(3));
	}

	@Test
	public void testToString() {
		assertThat(edge.toString(), is("(1=2)"));
	}
}
