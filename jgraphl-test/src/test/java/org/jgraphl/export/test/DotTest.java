package org.jgraphl.export.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jgraphl.Graph;
import org.jgraphl.MutableGraph;
import org.jgraphl.export.ExportFormat;
import org.jgraphl.export.ExportFormat.Factory;
import org.jgraphl.export.dot.Dot;
import org.jgraphl.graph.Graphs;
import org.junit.Test;

/**
 * Test whether creation of DOT format for several example graphs works.
 * 
 * @author ThorstenSeitz
 *
 */
public class DotTest {

	protected static ExportFormat.Factory<String> partiteDotFactory() {
		return Dot.factory((String vertex) -> {
			return vertex;
		});
	}

	@Test
	public void testCycle() throws IOException {
		Graph<Integer> cycle = Graphs.Examples.cycle(4);
		StringWriter writer = new StringWriter();
		ExportFormat.writeOn(writer, cycle, false, Dot.factory());
		assertThat(
				writer.toString(),
				equalTo("digraph {\n\tn0 -> n1\n\tn1 -> n2\n\tn2 -> n3\n\tn3 -> n0\n}"));
	}

	@Test
	public void testPartite() throws IOException {
		Graph<String> partite = Graphs.Examples.partite(2, 3);
		StringWriter writer = new StringWriter();
		ExportFormat.writeOn(writer, partite, true, partiteDotFactory());
		assertThat(
				writer.toString(),
				equalTo("digraph {\n\ta1 -> b2\n\ta1 -> b3\n\ta1 -> b1\n\ta2 -> b2\n\ta2 -> b3\n\ta2 -> b1\n}"));
	}

	/**
	 * Creates files for several example graphs in DOT format in the directory
	 * target/export/dot/.
	 * <p>
	 * Run <code>dot -Tsvg file.dot > file.svg</code> to convert a dot file to
	 * SVG.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String exportDir = "target/export/dot/";
		Files.createDirectories(Paths.get(exportDir));

		exportAndCallDotty(exportDir + "cycle.dot", Graphs.Examples.cycle(10),
				Dot.factory(), false);

		exportAndCallDotty(exportDir + "partite.dot", Graphs.Examples.partite(3, 5),
				partiteDotFactory(), true);
	}

	private static <V> void exportAndCallDotty(String exportFileName, Graph<V> g,
			Factory<V> factory, boolean needsVertexEquality)
			throws IOException {
		ExportFormat.writeFile(exportFileName, g, needsVertexEquality, factory);
		try {
			Runtime.getRuntime().exec("dotty " + exportFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
