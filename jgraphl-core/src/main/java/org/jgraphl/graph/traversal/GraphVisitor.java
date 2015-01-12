package org.jgraphl.graph.traversal;

import static org.jgraphl.graph.traversal.GraphVisitor.VisitColor.GRAY;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jgraphl.Graph;

public abstract class GraphVisitor<V> implements Iterator<V> {

	/**
	 * Standard vertex visit state enumeration.
	 */
	protected static enum VisitColor {
		/**
		 * Vertex has not been returned via iterator yet.
		 */
		WHITE,

		/**
		 * Vertex has been returned via iterator, but we're not done with all of
		 * its out-edges yet.
		 */
		GRAY,

		/**
		 * Vertex has been returned via iterator, and we're done with all of its
		 * out-edges.
		 */
		BLACK
	}

	protected final Graph<V> graph;
	protected final HashMap<V, VisitColor> colorMap = new HashMap<>();

	protected final Deque<V> queue = new ArrayDeque<V>();

	// Initialize listeners with no ops
	private Consumer<V> examineVertexListener = v -> {
	};

	private BiConsumer<V, V> examineEdgeListener = (u, v) -> {
	};
	private BiConsumer<V, V> treeEdgeListener = (u, v) -> {
	};
	private BiConsumer<V, V> backEdgeListener = (u, v) -> {
	};
	private BiConsumer<V, V> forwardEdgeListener = (u, v) -> {
	};

	public GraphVisitor(Graph<V> g) {
		graph = g;
	}

	public Graph<V> getGraph() {
		return graph;
	}

	public Stream<V> stream() {
		Iterable<V> iterable = () -> this;
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	protected boolean shouldFollowEdgeTo(V v) {
		return colorMap.getOrDefault(v, VisitColor.WHITE) == VisitColor.WHITE;
	}

	// Set listeners
	public void setExamineVertexListener(Consumer<V> examineVertexListener) {
		this.examineVertexListener = examineVertexListener;
	}

	public void setExamineEdgeListener(BiConsumer<V, V> examineEdgeListener) {
		this.examineEdgeListener = examineEdgeListener;
	}

	public void setTreeEdgeListener(BiConsumer<V, V> examineEdgeListener) {
		this.treeEdgeListener = examineEdgeListener;
	}

	public void setForwardEdgeListener(BiConsumer<V, V> forwardEdgeListener) {
		this.forwardEdgeListener = forwardEdgeListener;
	}

	public void setBackEdgeListener(BiConsumer<V, V> backEdgeListener) {
		this.backEdgeListener = backEdgeListener;
	}

	// Call listeners
	protected void callExamineVertexListener(V v) {
		examineVertexListener.accept(v);
	}

	protected void callExamineEdgeListener(V u, V v) {
		examineEdgeListener.accept(u, v);
	}

	protected void callTreeEdgeListener(V u, V v) {
		treeEdgeListener.accept(u, v);
	}

	protected void callForwardEdgeListener(V u, V v) {
		forwardEdgeListener.accept(u, v);
	}

	protected void callBackEdgeListener(V u, V v) {
		backEdgeListener.accept(u, v);
	}

	// coloring
	protected void setVisitColor(V v, VisitColor color) {
		colorMap.put(v, color);
	}

	protected void discoverVertex(final V v) {
		setVisitColor(v, GRAY);
		queue.push(v);
	}
}
