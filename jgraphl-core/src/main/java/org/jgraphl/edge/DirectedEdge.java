package org.jgraphl.edge;


public class DirectedEdge<V> implements Edge<V> {
	protected final V source;

	protected final V target;

	public DirectedEdge(V s, V t) {
		source = s;
		target = t;
	}

	@Override
	public V source() {
		return source;
	}

	@Override
	public V target() {
		return target;
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof DirectedEdge<?>))
			return false;

		@SuppressWarnings("unchecked")
		DirectedEdge<V> otherEdge = (DirectedEdge<V>)o;
		return source.equals(otherEdge.source) && target.equals(otherEdge.target);
	}

	public int hashCode() {
		return source.hashCode() ^ target.hashCode();
	}

	public String toString() {
		return "(" + source + "-" + target + ")";
	}

	public DirectedEdge<V> reverse() {
		return new DirectedEdge<V>(target, source);
	}

	public UndirectedEdge<V> toUndirected() {
		return new UndirectedEdge<V>(target, source);
	}
}
