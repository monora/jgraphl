package org.jgraphl.xml;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Simple lightweight XML writer.
 * 
 * @author ThorstenSeitz
 *
 */
public class XmlWriter extends IndentedWriter {

	public XmlWriter(Writer writer) {
		super(writer);
	}

	public XmlWriter writeElement(String tag, WriteAction writeContent) {
		return writeElement(tag, new HashMap<>(), writeContent);
	}

	public XmlWriter writeElement(String tag, Map<String, String> attributes) {
		return emptyTag(tag, attributes);
	}

	public XmlWriter writeElement(String tag, Map<String, String> attributes, WriteAction writeContent) {
		openTag(tag, attributes);
		indented(writeContent);
		closeTag(tag);
		return this;
	}

	private XmlWriter openTag(String tag, Map<String, String> attributes) {
		cr().append('<').append(tag);
		for (Entry<String, String> attribute : attributes.entrySet()) {
			append(' ').append(attribute.getKey());
			append("=\"").append(attribute.getValue()).append('"');
		}
		append('>');
		return this;
	}

	private XmlWriter emptyTag(String tag, Map<String, String> attributes) {
		cr().append('<').append(tag);
		for (Entry<String, String> attribute : attributes.entrySet()) {
			append(' ').append(attribute.getKey());
			append("=\"").append(attribute.getValue()).append('"');
		}
		append("/>");
		return this;
	}

	private XmlWriter closeTag(String tag) {
		append("</").append(tag).append('>');
		return this;
	}
}
