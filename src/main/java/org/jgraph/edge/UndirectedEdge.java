package org.jgraph.edge;


public class UndirectedEdge<V> extends DirectedEdge<V> {

	public UndirectedEdge(V s, V t) {
		super(s, t);
	}

	public boolean equals(Object o) {
		if (super.equals(o))
			return true;

		@SuppressWarnings("unchecked")
		UndirectedEdge<V> otherEdge = (UndirectedEdge<V>) o;
		return source.equals(otherEdge.target)
				&& target.equals(otherEdge.source);
	}

	public String toString() {
		return "(" + source + "=" + target + ")";
	}
}