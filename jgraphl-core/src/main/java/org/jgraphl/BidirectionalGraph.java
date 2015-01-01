/**
 * 
 */
package org.jgraphl;

import java.util.stream.Stream;

import org.jgraphl.edge.Edge;

/**
 * The BidirectionalGraph concept refines {@link IncidenceGraph} and adds the
 * requirement for efficient access to the in-edges of each vertex. This concept
 * is separated from IncidenceGraph because for directed graphs efficient access
 * to in-edges typically requires more storage space, and many algorithms do not
 * require access to in-edges. For undirected graphs this is not an issue, since
 * the inEdges() and outEdges() functions are the same, they both return the
 * edges incident to the vertex.
 * 
 * @Complexity The inEdges() function is required to be constant time. The
 *             inDegree() and degree() functions must be linear in the number of
 *             in-edges (for directed graphs) or incident edges (for undirected
 *             graphs).
 * 
 * @param <V>
 *
 */
public interface BidirectionalGraph<V> extends IncidenceGraph<V> {
	/**
	 * Returns a stream providing access to the in-edges (for directed graphs)
	 * or incident edges (for undirected graphs) of vertex u in graph g. For
	 * both directed and undirected graphs, the target of an out-edge is
	 * required to be vertex u and the source is required to be a vertex that is
	 * adjacent to u.
	 * 
	 * @param u
	 * @return a stream providing access to the in-edges to u
	 */
	Stream<Edge<V>> inEdges(V u);

	/**
	 * @param u
	 * @return Returns the number of in-edges (for directed graphs) or the
	 *         number of incident edges (for undirected graphs) of vertex u in
	 *         graph g.
	 */
	default long inDegree(V u) {
		return isDirected() ? inEdges(u).count() : outDegree(u);
	}

	/**
	 * @param u
	 * @return Returns the number of in-edges plus out-edges (for directed
	 *         graphs) or the number of incident edges (for undirected graphs)
	 *         of vertex u in graph g.
	 */
	default long degree(V u) {
		return isDirected() ? inDegree(u) + outDegree(u) : outDegree(u);
	}
}
