package org.jgraphl.export.graphml;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.jgraphl.Graph;
import org.jgraphl.edge.Edge;
import org.jgraphl.export.ExportFormat;
import org.jgraphl.xml.XmlWriter;

/**
 * GraphML format (see http://graphml.graphdrawing.org/specification.html).
 * 
 * @author ThorstenSeitz
 *
 * @param <V>
 */
public class GraphML<V> extends ExportFormat<V> {

	private final XmlWriter writer;

	public static <V> Factory<V> factory() {
		return (Graph<V> graph, Writer writer, boolean needsVertexEquality) -> {
			return new GraphML<V>(graph, writer, needsVertexEquality);
		};
	}

	protected GraphML(Graph<V> graph, Writer writer, boolean needsVertexEquality) {
		super(graph, needsVertexEquality);
		this.writer = new XmlWriter(writer);
	}

	@Override
	public void export() {
		writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		Map<String, String> attributes = new HashMap<>();
		attributes.put("xmlns", "http://graphml.graphdrawing.org/xmlns");
		attributes.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		attributes.put("xsi:schemaLocation",//
				"http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd");
		writer.writeElement("graphml", attributes, this::writeGraph);
	}

	private void writeGraph() {
		Map<String, String> attributes = new HashMap<>();
		attributes.put(//
				"edgedefault",//
				graph.isDirected() ? "directed" : "undirected");
		writer.writeElement("graph", attributes, this::writeVerticesAndEdges);
	}

	private void writeVerticesAndEdges() {
		graph.forEachVertex(this::writeVertex);
		graph.edgeStream().forEach(this::writeEdge);
	}

	private void writeVertex(V vertex) {
		String id = getVertexId(vertex);
		Map<String, String> attributes = new HashMap<>();
		attributes.put("id", id);
		attributes.put("text", id);
		writer.writeElement("node", attributes);
	}

	private void writeEdge(Edge<V> edge) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("directed", Boolean.toString(edge.isDirected()));
		attributes.put("source", getVertexId(edge.source()));
		attributes.put("target", getVertexId(edge.target()));
		writer.writeElement("edge", attributes);
	}
}
