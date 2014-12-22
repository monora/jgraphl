package org.jgraphl.export;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import org.jgraphl.Graph;

/**
 * An XmlExportFormat describes how to export a {@link Graph} to a file in a
 * specific XML based format.
 * <p>
 * Convert resulting file to svg with
 * <p>
 * <code>
 * dot -Tsvg file.dot > file.svg
 * </code>
 * 
 * @author ThorstenSeitz
 *
 * @param <V>
 */
public abstract class ExportFormat<V> {

	protected Graph<V> graph;

	private Map<V, String> vertexIdentifiers;

	@FunctionalInterface
	public static interface Factory<V> {

		ExportFormat<V> create(Graph<V> graph, Writer writer, boolean needsVertexEquality);
	}

	/**
	 * Write given graph in given format into a file with the given name.
	 * 
	 * @param filename
	 * @param graph
	 * @param needsVertexEquality
	 *            use equality instead of identity to identify vertices
	 * @param formatFactory
	 *            factory for creating the desired ExportFormat
	 * 
	 * @throws IOException
	 */
	public static <V> void writeFile(String filename, Graph<V> graph, boolean needsVertexEquality,
			ExportFormat.Factory<V> formatFactory) throws IOException {
		try (Writer writer = fileWriterUtf8(filename)) {
			writeOn(writer, graph, needsVertexEquality, formatFactory);
		}
	}

	/**
	 * Write given graph in the given format on given writer.
	 * 
	 * @param writer
	 * @param graph
	 * @param needsVertexEquality
	 *            use equality instead of identity to identify vertices
	 * @param formatFactory
	 *            factory for creating the desired ExportFormat
	 * 
	 * @throws IOException
	 */
	public static <V> void writeOn(Writer writer, Graph<V> graph, boolean needsVertexEquality,
			ExportFormat.Factory<V> formatFactory) {
		ExportFormat<V> format = formatFactory.create(graph, writer, needsVertexEquality);
		format.export();
	}

	private static Writer fileWriterUtf8(String filename) throws UnsupportedEncodingException,
			FileNotFoundException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
	}

	protected ExportFormat(Graph<V> graph, boolean needsVertexEquality) {
		this.graph = graph;
		this.vertexIdentifiers = needsVertexEquality ? new HashMap<>() : new IdentityHashMap<>();
	}

	public abstract void export();

	protected String getVertexId(V vertex) {
		return vertexIdentifiers.computeIfAbsent(vertex, v -> {
			return "n" + vertexIdentifiers.size();
		});
	}
}
