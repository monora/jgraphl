package org.jgraphl.edge;

import org.jgraphl.edge.Edge;
import org.jgraphl.edge.UndirectedEdge;

public interface DirectedEdge<V> extends Edge<V> {

	public UndirectedEdge<V> toUndirected();
	
	default DirectedEdge<V> reverse() {
		return (DirectedEdge<V>) newEdgeSupplier().get().apply(target(), source());
	}
	

}
