package org.jgraphl;

import org.jgraphl.edge.Edge;

/**
 * A MutableGraph can be changed via the addition or removal of edges and
 * vertices.
 *
 * @param <V>
 */
public interface MutableGraph<V> extends Graph<V> {
	public MutableGraph<V> addVertex(V v);

	/**
	 * Inserts the edge (u,v) into the graph, and returns an edge descriptor
	 * pointing to the new edge. If the graph disallows parallel edges, and the
	 * edge (u,v) is already in the graph, then the bool flag returned is false
	 * and the returned edge descriptor points to the already existing edge.
	 * Note that for undirected graphs, (u,v) is the same edge as (v,u), so
	 * after a call to the function add_edge(), this implies that edge (u,v)
	 * will appear in the out-edges of u and (u,v) (or equivalently (v,u)) will
	 * appear in the out-edges of v. Put another way, v will be adjacent to u
	 * and u will be adjacent to v.
	 *
	 * @param u
	 * @param v
	 * @return this graph
	 */
	public MutableGraph<V> addEdge(V u, V v);

	/**
	 * Insert edge e into the graph.
	 *
	 * @param e
	 * @return this graph
	 */
	default MutableGraph<V> addEdge(Edge<V> e) {
		return addEdge(e.source(), e.target());
	}

	/**
	 * Remove u from the vertex set of the graph.
	 *
	 * @Pre u is a valid vertex of g.
	 * @Post num_vertices(g) is one less, u no longer appears in the vertex set
	 *       of the graph.
	 *
	 * @param u
	 * @return this graph
	 */
	public MutableGraph<V> removeVertex(V u);

	/**
	 * Remove the edge(u,v) from the graph. If the graph allows parallel edges
	 * this remove all occurrences of (u,v).
	 *
	 * @Post (u,v) is no longer in the edge set for the graph.
	 *
	 * @param u
	 * @param v
	 * @return this graph
	 */
	public MutableGraph<V> removeEdge(V u, V v);

	/**
	 * Remove the edge e from the graph.
	 *
	 * @Post e is no longer in the edge set for the graph.
	 * @param e
	 * @return this graph
	 */
	default MutableGraph<V> removeEdge(Edge<V> e) {
		return removeEdge(e.source(), e.target());
	}
}
