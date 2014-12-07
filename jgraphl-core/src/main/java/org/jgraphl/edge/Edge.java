package org.jgraphl.edge;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public interface Edge<V> {

	V target();

	V source();

	default boolean equalsEdge(Object o) {
		if (o instanceof Edge<?>) {
			@SuppressWarnings("unchecked")
			Edge<V> otherEdge = (Edge<V>) o;
			boolean isTupleEqual = source().equals(otherEdge.source())
					&& target().equals(otherEdge.target());
			if (isDirected() && otherEdge.isDirected()) {
				return isTupleEqual;
			} else if (isUnDirected() && otherEdge.isUnDirected()) {
				return isTupleEqual
						|| (source().equals(otherEdge.target()) && target()
								.equals(otherEdge.source()));
			}
		}
		return false;
	}

	default boolean isUnDirected() {
		return !isDirected();
	}

	default int hash() {
		return source().hashCode() ^ target().hashCode();
	}

	Supplier<BiFunction<V, V, Edge<V>>> newEdgeSupplier();

	default public String str() {
		return "(" + source() + toStringDelimiter() + target() + ")";
	}

	default String toStringDelimiter() {
		return "-";
	}

	default boolean isDirected() {
		return true;
	}
}
