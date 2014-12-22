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
		return factory(null);
	}

	public static <V> Factory<V> factory(LabelProvider<V> labelProvider) {
		return (Graph<V> graph, Writer writer, boolean needsVertexEquality) -> {
			Dot<V> dot = new Dot<>(graph, new IndentedWriter(writer), needsVertexEquality);
			if (labelProvider != null) {
				dot.setLabelProvider(labelProvider);
			}
			return dot;
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
				.append(getLabel(edge.source()))//
				.append(edge.isDirected() ? " -> " : " -- ")//
				.append(getLabel(edge.target()));
	}

}
