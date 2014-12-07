package org.jgraphl.edge;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class DefaultDirectedEdge<V> implements DirectedEdge<V> {
	protected final V source;

	protected final V target;

	public DefaultDirectedEdge(V s, V t) {
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
		return equalsEdge(o);
	}

	public int hashCode() {
		return hash();
	}

	public String toString() {
		return str();
	}

	public UndirectedEdge<V> toUndirected() {
		return new DefaultUndirectedEdge<V>(target, source);
	}

	@Override
	public Supplier<BiFunction<V, V, Edge<V>>> newEdgeSupplier() {
		return () -> (u,v) -> new DefaultDirectedEdge<>(u, v);
	}
}
