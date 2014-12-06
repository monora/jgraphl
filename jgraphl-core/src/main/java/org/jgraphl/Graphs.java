package org.jgraphl;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class Graphs {

	private Graphs() {
		throw new Error("no instances");
	}

	static public final class Examples {

		public static Graph<Integer> cycle(int n) {
			Supplier<Stream<Integer>> x = () -> Stream.iterate(0,
					i -> Integer.valueOf(i + 1)).limit(n);
			return new ImplicitGraph.Builder<Integer>()
					.neighborIterator(y -> Stream.of((y + 1) % n))
					.vertexIterator(x).build();
		}

		/**
		 * @param col used as vertex set of the resulting graph
		 * @return a complete undirected graph having col as vertex set
		 */
		public static <V> Graph<V> complete(Collection<V> col) {
			return new ImplicitGraph.Builder<V>()
					.vertexIterator(()-> col.stream())
					.neighborIterator(v -> col.stream().filter(u -> u != v))
					.directed(false)
					.build();
		}
	}
}
