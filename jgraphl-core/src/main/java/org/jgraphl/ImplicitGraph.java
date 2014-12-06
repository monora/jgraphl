package org.jgraphl;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jgraphl.ImplicitGraph.Builder;
import org.jgraphl.edge.Edge;

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
	public void forEachAdjacentVertex(V vertex, Consumer<? super V> action) {
		streamOfNeighbors(vertex).forEach(u -> action.accept(u));
	}

	@Override
	public Stream<V> streamOfNeighbors(V v) {
		return neighborStreamSupplier.apply(v);
	}

	@Override
	public void forEachAdjacentEdge(V u, Consumer<? super Edge<V>> action) {
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
