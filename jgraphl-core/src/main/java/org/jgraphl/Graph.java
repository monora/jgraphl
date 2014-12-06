/**
 *
 */
package org.jgraphl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jgraphl.edge.DirectedEdge;
import org.jgraphl.edge.Edge;
import org.jgraphl.edge.UndirectedEdge;

public interface Graph<V> extends Iterable<V> {

	default void forEachVertex(Consumer<? super V> action) {
		forEach(action);
	}

	void forEachAdjacentVertex(V vertex, Consumer<? super V> action);

	void forEachAdjacentEdge(V u, Consumer<? super Edge<V>> action);

	default void forEachEdge(BiConsumer<V, V> action) {
		if (isDirected()) {
			forEachVertex(v -> forEachAdjacentVertex(v,
					u -> action.accept(v, u)));
		} else {
			// TODO forEachAdjacentVertexForUndirected(u, action);
		}
	}

	default boolean isDirected() {
		return true;
	}

	default Stream<V> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	Stream<V> streamOfNeighbors(V v);
	
	default Set<V> vertices() {
		return stream().collect(Collectors.toSet());
	}

	default List<Edge<V>> edges() {
		ArrayList<Edge<V>> result = new ArrayList<Edge<V>>();
		forEachEdge((u, v) -> result.add(makeEdge(u, v)));
		return result;
	}

	default Edge<V> makeEdge(V u, V v) {
		return edgeSupplier().get().apply(u, v);
	}

	default Supplier<BiFunction<V,V,Edge<V>>> edgeSupplier() {
		if (isDirected())
			return () -> (u,v) -> new DirectedEdge<>(u, v);
		else
			return () -> (u,v) -> new UndirectedEdge<>(u, v);
	}

	default Stream<Edge<V>> edgeStream() {
		return stream().flatMap(u -> streamOfNeighbors(u).map(v -> makeEdge(u, v)));
	}

	default long size() {
		return stream().count();
	}

}
