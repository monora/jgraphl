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
import org.jgraphl.export.graphml.GraphML;
import org.jgraphl.graph.Graphs;
import org.junit.Test;

/**
 * Test whether creation of GraphML format for several example graphs works.
 * 
 * @author ThorstenSeitz
 *
 */
public class GraphMLTest {

	@Test
	public void testCycle() throws IOException {
		Graph<Integer> cycle = Graphs.Examples.cycle(4);
		StringWriter writer = new StringWriter();
		ExportFormat.writeOn(writer, cycle, false, GraphML.factory());
		assertThat(
				writer.toString(),
				equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n\t<graph edgedefault=\"directed\">\n\t\t<node id=\"n0\" text=\"n0\"/>\n\t\t<node id=\"n1\" text=\"n1\"/>\n\t\t<node id=\"n2\" text=\"n2\"/>\n\t\t<node id=\"n3\" text=\"n3\"/>\n\t\t<edge directed=\"true\" source=\"n0\" target=\"n1\"/>\n\t\t<edge directed=\"true\" source=\"n1\" target=\"n2\"/>\n\t\t<edge directed=\"true\" source=\"n2\" target=\"n3\"/>\n\t\t<edge directed=\"true\" source=\"n3\" target=\"n0\"/>\n\t</graph>\n</graphml>"));
	}

	@Test
	public void testPartite() throws IOException {
		Graph<String> partite = Graphs.Examples.partite(2, 3);
		StringWriter writer = new StringWriter();
		ExportFormat.writeOn(writer, partite, true, GraphML.factory());
		assertThat(
				writer.toString(),
				equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n\t<graph edgedefault=\"directed\">\n\t\t<node id=\"n0\" text=\"n0\"/>\n\t\t<node id=\"n1\" text=\"n1\"/>\n\t\t<node id=\"n2\" text=\"n2\"/>\n\t\t<node id=\"n3\" text=\"n3\"/>\n\t\t<node id=\"n4\" text=\"n4\"/>\n\t\t<edge directed=\"true\" source=\"n1\" target=\"n0\"/>\n\t\t<edge directed=\"true\" source=\"n1\" target=\"n3\"/>\n\t\t<edge directed=\"true\" source=\"n1\" target=\"n4\"/>\n\t\t<edge directed=\"true\" source=\"n2\" target=\"n0\"/>\n\t\t<edge directed=\"true\" source=\"n2\" target=\"n3\"/>\n\t\t<edge directed=\"true\" source=\"n2\" target=\"n4\"/>\n\t</graph>\n</graphml>"));
	}

	/**
	 * Creates files for several example graphs in GraphML format in the
	 * directory export/graphml/.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Files.createDirectories(Paths.get("export/graphml"));
		ExportFormat.writeFile("export/graphml/cycle.graphml", Graphs.Examples.cycle(10), false, Dot.factory());
		ExportFormat.writeFile("export/graphml/partite.graphml", Graphs.Examples.partite(3, 5), true,
				Dot.factory());
	}

}
