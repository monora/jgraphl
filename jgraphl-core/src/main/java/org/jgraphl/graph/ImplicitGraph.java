package org.jgraphl.graph;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jgraphl.Graph;

public class ImplicitGraph<V> implements Graph<V> {

	private final Supplier<Stream<V>> vertexStreamSupplier;
	private final Function<V, Stream<V>> neighborStreamSupplier;
	private final boolean isDirected;

	public ImplicitGraph(Supplier<Stream<V>> it,
			Function<V, Stream<V>> neighborIterator, boolean isDirected) {
		this.vertexStreamSupplier = it;
		this.neighborStreamSupplier = neighborIterator;
		this.isDirected = isDirected;
	}

	public boolean isDirected() {
		return isDirected;
	}

	@Override
	public Iterator<V> iterator() {
		return stream().iterator();
	}

	public Stream<V> stream() {
		return vertexStreamSupplier.get();
	}

	@Override
	public Stream<V> adjacentVertices(V v) {
		return neighborStreamSupplier.apply(v);
	}

	@Override
	public String toString() {
		return str();
	}

	public static final class Builder<W> {

		private Supplier<Stream<W>> it;
		private Function<W, Stream<W>> neighborIterator;
		private boolean isDirected = true;

		Builder() {
			it = () -> Stream.empty();
		}

		public Builder<W> vertexIterator(Supplier<Stream<W>> vertexIterator) {
			this.it = vertexIterator;
			return this;
		}

		public Builder<W> neighborIterator(
				Function<W, Stream<W>> neighborIterator) {
			this.neighborIterator = neighborIterator;
			return this;
		}

		public Graph<W> build() {
			return new ImplicitGraph<W>(it, neighborIterator, isDirected);
		}

		public Builder<W> directed(boolean b) {
			isDirected = b;
			return this;
		}

	}

	public static <T> Builder<T> builder() {
		return new Builder<>();
	}

}
