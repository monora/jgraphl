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

import org.jgraphl.edge.DefaultDirectedEdge;
import org.jgraphl.edge.DefaultUndirectedEdge;
import org.jgraphl.edge.Edge;

/**
 * 
 * @param <V>
 *            type of vertices
 */
public interface Graph<V> extends Iterable<V>, AdjacencyGraph<V> {

	default void forEachVertex(Consumer<? super V> action) {
		forEach(action);
	}

	default Stream<V> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	default Set<V> vertices() {
		return stream().collect(Collectors.toSet());
	}

	default long noOfVertices() {
		return stream().count();
	}

	default void forEachEdge(BiConsumer<V, V> action) {
		if (isDirected()) {
			forEachVertex(v -> forEachAdjacentVertex(v,
					u -> action.accept(v, u)));
		} else {
			edgeStream().forEach(e -> action.accept(e.source(), e.target()));
		}
	}

	default Stream<Edge<V>> edgeStream() {
		Stream<Edge<V>> result = stream().flatMap(
				u -> adjacentVertices(u).map(v -> getEdge(u, v)));
		return isDirected() ? result : result.distinct();
	}

	default List<Edge<V>> edges() {
		ArrayList<Edge<V>> result = new ArrayList<Edge<V>>();
		forEachEdge((u, v) -> result.add(getEdge(u, v)));
		return result;
	}

	default long noOfEdges() {
		return edgeStream().count();
	}

	boolean isDirected();

	default Edge<V> getEdge(V u, V v) {
		return edgeSupplier().get().apply(u, v);
	}

	default Supplier<BiFunction<V, V, Edge<V>>> edgeSupplier() {
		if (isDirected())
			return () -> (u, v) -> new DefaultDirectedEdge<>(u, v);
		else
			return () -> (u, v) -> new DefaultUndirectedEdge<>(u, v);
	}

	default boolean contains(V v) {
		return stream().anyMatch(u -> u.equals(v));
	}

	default boolean containsEdge(V u, V v) {
		return containsEdge(getEdge(u, v));
	}

	default boolean containsEdge(Edge<V> edge) {
		return edgeStream().anyMatch(e -> e.equals(edge));
	}

	default String str() {
		return this.getClass().getSimpleName()
				+ "[V=["
				+ stream().limit(100).map(v -> v.toString())
						.collect(Collectors.joining(", "))
				+ "] E=["
				+ edgeStream().limit(100).map(v -> v.toString())
						.collect(Collectors.joining(", ")) + "]]";
	}

	default String sortedEdgeStreamToString() {
		return edgeStream().map(Object::toString).sorted()
				.collect(Collectors.joining(","));
	}
}
