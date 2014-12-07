package org.jgraphl.edge;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class DefaultUndirectedEdge<V> extends DefaultDirectedEdge<V> implements UndirectedEdge<V> {

	public DefaultUndirectedEdge(V s, V t) {
		super(s, t);
	}

	public boolean equals(Object o) {
		return equalsEdge(o);
	}
	
	@Override
	public Supplier<BiFunction<V, V, Edge<V>>> newEdgeSupplier() {
		return () -> (u,v) -> new DefaultUndirectedEdge<>(u, v);
	}
}