/**
 * 
 */
package org.jgraphl;

import java.util.stream.Stream;

import org.jgraphl.edge.Edge;

/**
 * An IncidenceGraph provides efficient access to the out-edges of each vertex
 * in the graph.
 * 
 * <p>
 * <b>Complexity guarantees</b>
 * <p>
 * The outEdges() functions must all be constant time. The outDegree() function
 * must be linear in the number of out-edges.
 * 
 * @param <V>
 *
 */
public interface IncidenceGraph<V> extends Graph<V> {

	/**
	 * Returns an stream providing access to the out-edges (for directed graphs)
	 * or incident edges (for undirected graphs) of vertex <code>u</code> in
	 * this graph. The source vertex of an edge obtained via an out edge stream
	 * is guaranteed (for both directed and undirected graphs) to be the vertex
	 * u used in the call to <code>outEdges(u, g)</code> and the target vertex
	 * must be a vertex adjacent to <code>u</code>.
	 * 
	 * <p>
	 * <b>Note:</b> For undirected graphs, the edge (u,v) is the same as edge
	 * (v,u), so without some extra guarantee an implementation would be free
	 * use any ordering for the pair of vertices in an out-edge. For example, if
	 * you call <code>outEdges(u, g)</code>, and v is one of the vertices
	 * adjacent to u, then the implementation would be free to return (v,u) as
	 * an out-edge which would be non-intuitive and cause trouble for
	 * algorithms. Therefore, the extra requirement is added that the out-edge
	 * connecting u and v must be given as (u,v) and not (v,u).
	 * 
	 * @param u
	 * @return a stream of Edges adjacent to u
	 */
	Stream<Edge<V>> outEdges(V u);

	/**
	 * @param u
	 * @return the number of out-edges (for directed graphs) or the number of
	 *         incident edges (for undirected graphs) of vertex u in this graph.
	 */
	default long outDegree(V u) {
		return outEdges(u).count();
	}
}
