package org.jgraphl.edge.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.jgraphl.edge.DefaultDirectedEdge;
import org.jgraphl.edge.DirectedEdge;
import org.junit.Before;
import org.junit.Test;

public class DirectedEdgeTest {

	private DirectedEdge<Integer> edge;
	private DirectedEdge<Integer> reversedEdge;

	@Before
	public void createEdge(){
		edge = new DefaultDirectedEdge<Integer>(1,2);
		reversedEdge = edge.reverse();
	}
	@Test
	public void testEqualsObject() {
		assertThat(edge, is(equalTo(new DefaultDirectedEdge<>(1,2))));
		assertThat(edge, not(equalTo(reversedEdge)));
		assertThat(edge, not(equalTo(edge.toUndirected())));
		assertThat(edge.toUndirected(), not(equalTo(edge)));
	}

	@Test
	public void testHashCode() {
		assertThat(edge.hashCode(), is(3));
		assertThat(reversedEdge.hashCode(), is(3));
	}

	@Test
	public void testToString() {
		assertThat(edge.toString(), is("(1-2)"));
		assertThat(reversedEdge.toString(), is("(2-1)"));
	}

}
