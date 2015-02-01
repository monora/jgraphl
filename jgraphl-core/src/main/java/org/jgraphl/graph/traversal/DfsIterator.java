/**
 *
 */
package org.jgraphl.graph.traversal;

import java.util.ArrayList;

import org.jgraphl.Graph;

public class DfsIterator<V> extends TraversalIterator<V> {

	private ArrayList<V> waiting;

	public DfsIterator(final Graph<V> g, final V v) {
		super(g, v);
		discoverVertex(v);
	}

	@Override
	void initializeWaiting() {
		waiting = new ArrayList<>();
	}

	@Override
	void pushWaiting(V v) {
		waiting.add(v);
	}

	@Override
	V popWaiting() {
		int last = waiting.size() - 1;
		V v = waiting.get(last);
		waiting.remove(last);
		return v;
	}

	@Override
	public boolean hasNext() {
		return !waiting.isEmpty();
	}

}
