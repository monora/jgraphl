/**
 *
 */
package org.jgraphl.graph.traversal;

import static org.jgraphl.graph.traversal.GraphVisitor.VisitColor.BLACK;
import static org.jgraphl.graph.traversal.GraphVisitor.VisitColor.GRAY;

import org.jgraphl.Graph;

/**
 * http://www.boost.org/libs/graph/doc/breadth_first_search.html
 * 
 */
public class BfsIterator<V> extends GraphVisitor<V> {

	private final V startvertex;
	public BfsIterator(final Graph<V> g, final V v) {
		super(g);
		startvertex = v;
		discoverVertex(v);
	}

	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public V next() {
		V u = queue.removeLast();
		callExamineVertexListener(u);
		graph.forEachAdjacentVertex(u, v -> {
			callExamineEdgeListener(u, v);
			if (shouldFollowEdgeTo(v)) { // that is v is WHITE
				callTreeEdgeListener(u,v);
				discoverVertex(v);
			} else { // (u,v) is a non tree edge
				if (colorMap.get(v) == GRAY) {
					// v is GRAY
					callBackEdgeListener(u,v);
				} else {
					// v is BLACK
					callForwardEdgeListener(u,v);
				}
			}
		});
		setVisitColor(u, BLACK);
		return u;
	}

	public V getStartvertex() {
		return startvertex;
	}
	

}
