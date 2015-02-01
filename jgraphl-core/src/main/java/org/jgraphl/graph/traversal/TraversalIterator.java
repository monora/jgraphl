package org.jgraphl.graph.traversal;

import static org.jgraphl.graph.traversal.GraphVisitor.VisitColor.BLACK;
import static org.jgraphl.graph.traversal.GraphVisitor.VisitColor.GRAY;

import org.jgraphl.Graph;

public abstract class TraversalIterator<V> extends GraphVisitor<V> {

	private final V startvertex;

	public TraversalIterator(Graph<V> g, V v) {
		super(g);
		startvertex = v;
		initializeWaiting();
	}

	@Override
	abstract public boolean hasNext();

	public V getStartvertex() {
		return startvertex;
	}

	protected void discoverVertex(final V v) {
		setVisitColor(v, GRAY);
		pushWaiting(v);
	}

	@Override
	public V next() {
		V u = popWaiting();
		callExamineVertexListener(u);
		graph.forEachAdjacentVertex(u, v -> {
			callExamineEdgeListener(u, v);
			if (shouldFollowEdgeTo(v)) { // that is v is WHITE
					callTreeEdgeListener(u, v);
					discoverVertex(v);
				} else { // (u,v) is a non tree edge
					if (colorMap.get(v) == GRAY) {
						// v is GRAY
						callBackEdgeListener(u, v);
					} else {
						// v is BLACK
						callForwardEdgeListener(u, v);
					}
				}
			});
		setVisitColor(u, BLACK);
		return u;
	}

	abstract void initializeWaiting();

	abstract void pushWaiting(V v);

	abstract V popWaiting();
}