package org.jgraphl.xml;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

/**
 * Simple wrapper for {@link Writer} with indentation and hiding of
 * {@link IOException} in append methods (to make the {@link IndentedWriter}
 * easily usable from within lambdas).
 * <p>
 * Implementation note: to make the methods chainable for subclasses as well
 * they should return the type of <code>this</code> instead of
 * {@link IndentedWriter}. Self types are not available in Java but can be
 * simulated with generics and some hoops to jump through, but this would make
 * IndentedWriter ugly and not nicely usable by itself, so I decided to not use
 * them.
 * 
 * @author ThorstenSeitz
 *
 */
public class IndentedWriter implements Closeable {

	/**
	 * Wrapper for IOException because checked exceptions break lambdas.
	 * 
	 * @author ThorstenSeitz
	 *
	 */
	public static class WriteException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public WriteException(IOException cause) {
			super(cause);
		}
	}

	/**
	 * Write action.
	 * 
	 * @author ThorstenSeitz
	 *
	 */
	public static interface WriteAction {
		void write();
	}

	/**
	 * The wrapped {@link Writer}.
	 */
	private final Writer writer;

	/**
	 * The current indentation.
	 */
	private int indent = 0;

	/**
	 * Create an IndentedWriter by wrapping a {@link Writer}.
	 * 
	 * @param writer
	 */
	public IndentedWriter(Writer writer) {
		super();
		this.writer = writer;
	}

	/**
	 * Indent while running the {@link WriteAction}.
	 * 
	 * @param action
	 * @return this to allow method chaining
	 */
	public IndentedWriter indented(WriteAction action) {
		++indent;
		action.write();
		--indent;
		cr();
		return this;
	}

	/**
	 * Append a character.
	 * 
	 * @param c
	 * @return this to allow method chaining
	 */
	public IndentedWriter append(char c) {
		try {
			writer.append(c);
		} catch (IOException e) {
			throw new WriteException(e);
		}
		return this;
	}

	/**
	 * Append a string.
	 * 
	 * @param string
	 * @return this to allow method chaining
	 */
	public IndentedWriter append(String string) {
		try {
			writer.append(string);
		} catch (IOException e) {
			throw new WriteException(e);
		}
		return this;
	}

	/**
	 * Insert a newline followed by the current indentation (inserting one tab
	 * per indentation increment).
	 * 
	 * @return this to allow method chaining
	 */
	public IndentedWriter cr() {
		try {
			writer.append('\n');
			for (int i = 0; i < indent; ++i) {
				writer.append('\t');
			}
		} catch (IOException e) {
			throw new WriteException(e);
		}
		return this;
	}

	/**
	 * Close the wrapped {@link Writer}.
	 */
	@Override
	public void close() throws IOException {
		writer.close();
	}

}
