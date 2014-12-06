package org.jgraphl.edge;


public class DirectedEdge<V> implements Edge<V> {
	public final V source;

	public final V target;

	public DirectedEdge(V s, V t) {
		source = s;
		target = t;
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
}
