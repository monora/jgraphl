package org.jgraphl;

import java.util.function.Supplier;
import java.util.stream.Stream;

public final class Graphs {

	private Graphs() {
		throw new Error("no instances");
	}

	static public final class Examples {

		public static ImplicitGraph<Integer> cycle(int n) {
			Supplier<Stream<Integer>> x = () -> Stream.iterate(0,
					i -> Integer.valueOf(i + 1)).limit(n);
			return new ImplicitGraph.Builder<Integer>()
					.neighborIterator(y -> Stream.of((y + 1) % n))
					.vertexIterator(x).build();
		}
	}
}
