package org.jgraphl.export.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jgraphl.Graph;
import org.jgraphl.export.ExportFormat;
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

	@Test
	public void testCycle() throws IOException {
		Graph<Integer> cycle = Graphs.Examples.cycle(4);
		StringWriter writer = new StringWriter();
		ExportFormat.writeOn(writer, cycle, false, Dot.factory());
		assertThat(writer.toString(), equalTo("digraph {\n\tn0 -> n1\n\tn1 -> n2\n\tn2 -> n3\n\tn3 -> n0\n}"));
	}

	@Test
	public void testPartite() throws IOException {
		Graph<String> partite = Graphs.Examples.partite(2, 3);
		StringWriter writer = new StringWriter();
		ExportFormat.writeOn(writer, partite, true, Dot.factory());
		assertThat(writer.toString(),
				equalTo("digraph {\n\tn0 -> n1\n\tn0 -> n2\n\tn0 -> n3\n\tn4 -> n1\n\tn4 -> n2\n\tn4 -> n3\n}"));
	}

	/**
	 * Creates files for several example graphs in DOT format in the directory
	 * export/dot/.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Files.createDirectories(Paths.get("export/dot"));
		ExportFormat.writeFile("export/dot/cycle.dot", Graphs.Examples.cycle(10), false, Dot.factory());
		ExportFormat.writeFile("export/dot/partite.dot", Graphs.Examples.partite(3, 5), true, Dot.factory());
	}

}
