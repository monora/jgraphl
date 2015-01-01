/**
 * 
 */
package org.jgraphl;

import java.util.stream.Stream;

/**
 * The AdjacencyGraph provides interface for efficient access of the adjacent
 * vertices to a vertex in a graph. This is quite similar to the
 * {@link IncidenceGraph} interface (the target of an out-edge is an adjacent
 * vertex). Both concepts are provided because in some contexts there is only
 * concern for the vertices, whereas in other contexts the edges are also
 * important.
 * 
 * @Design The AdjacencyGraph interface is somewhat frivolous since
 *         {@link IncidenceGraph} really covers the same functionality (and
 *         more). The AdjacencyGraph concept exists because there are situations
 *         when adjacentVertices() is more convenient to use than outEdges().
 * 
 * @Notes The case of a multigraph (where multiple edges can connect the
 *        same two vertices) brings up an issue as to whether the iterators
 *        returned by the adjacentVertices() function access a range that
 *        includes each adjacent vertex once, or whether it should match the
 *        behavior of the outEdges() function, and access a range that may
 *        include an adjacent vertex more than once.
 */
public interface AdjacencyGraph<V> extends Graph<V> {

	Stream<V> adjacentVertices(V v);
}
