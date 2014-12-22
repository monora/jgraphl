package org.jgraphl.export.dot;

import java.io.Writer;

import org.jgraphl.Graph;
import org.jgraphl.edge.Edge;
import org.jgraphl.export.ExportFormat;
import org.jgraphl.xml.IndentedWriter;

/**
 * DOT format (see http://www.graphviz.org/doc/info/lang.html).
 * 
 * @author ThorstenSeitz
 *
 * @param <V>
 */
public class Dot<V> extends ExportFormat<V> {

	private final IndentedWriter writer;

	public static <V> Factory<V> factory() {
		return (Graph<V> graph, Writer writer, boolean needsVertexEquality) -> {
			return new Dot<V>(graph, new IndentedWriter(writer), needsVertexEquality);
		};
	}

	protected Dot(Graph<V> graph, IndentedWriter writer, boolean needsVertexEquality) {
		super(graph, needsVertexEquality);
		this.writer = writer;
	}

	@Override
	public void export() {
		writer.append(graph.isDirected() ? "digraph" : "graph")//
				.append(" {")//
				.indented(this::writeEdges)//
				.append("}");
	}

	private void writeEdges() {
		graph.edgeStream().forEach(this::writeEdge);
	}

	private void writeEdge(Edge<V> edge) {
		writer.cr()//
				.append(getVertexId(edge.source()))//
				.append(edge.isDirected() ? " -> " : " -- ")//
				.append(getVertexId(edge.target()));
	}

}
