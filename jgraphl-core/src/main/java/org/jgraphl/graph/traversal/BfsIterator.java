/**
 *
 */
package org.jgraphl.graph.traversal;

import java.util.ArrayDeque;

import org.jgraphl.Graph;

/**
 * http://www.boost.org/libs/graph/doc/breadth_first_search.html
 * 
 */
public class BfsIterator<V> extends TraversalIterator<V> {

	private ArrayDeque<V> waiting;

	public BfsIterator(final Graph<V> g, final V v) {
		super(g, v);
		discoverVertex(v);
	}

	protected V popWaiting() {
		return waiting.removeLast();
	}
	
	protected void pushWaiting(final V v) {
		waiting.push(v);
	}

	@Override
	void initializeWaiting() {
		waiting = new ArrayDeque<V>();
	}

	@Override
	public boolean hasNext() {
		return !waiting.isEmpty();
	}
}
