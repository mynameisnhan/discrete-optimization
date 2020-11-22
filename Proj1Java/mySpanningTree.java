import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.jgrapht.Graph;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import java.util.Iterator; // OUR CODE
		
public class mySpanningTree {
	
	// Receives a graph and computes a minimum spanning tree
	public static AsSubgraph<Integer, DefaultWeightedEdge> computeMST(Graph<Integer, DefaultWeightedEdge> graph) {
		AsSubgraph<Integer, DefaultWeightedEdge> tree = new AsSubgraph<Integer, DefaultWeightedEdge>(graph, graph.vertexSet(), new HashSet<DefaultWeightedEdge>());
			
		/*
		*
		* START OF OUR CODE
		*
		*/

		// Kruskal's algorithm
		
		HashSet<Integer> vertexSet = new HashSet<Integer>(graph.vertexSet());
	
		// Sort edges by ascending order of weight values
		Iterator<DefaultWeightedEdge> edgeSet = graph.edgeSet().stream().sorted(Comparator.comparingDouble(graph::getEdgeWeight)).iterator();
 
		// Initialize connected components
		HashMap<Integer, Integer> connectedComponents = new HashMap<>();		
		for (Integer vertex: vertexSet) {
			connectedComponents.put(vertex, vertex);
		}
		
		// For each edge...
		while (edgeSet.hasNext()) {
			DefaultWeightedEdge edge = edgeSet.next();
			Integer edgeSource = connectedComponents.get(graph.getEdgeSource(edge));
			Integer edgeTarget = connectedComponents.get(graph.getEdgeTarget(edge));

			// ...if its addition would not introduce a cycle...
			if (edgeSource != edgeTarget) {
				// ...add it to the tree
				tree.addEdge(graph.getEdgeSource(edge),graph.getEdgeTarget(edge),edge);
				for (Integer vertex: vertexSet) {
					if (connectedComponents.get(vertex).equals(edgeTarget)){
						connectedComponents.put(vertex, edgeSource);
					}
				}		
			}
		}

		/*
		*
		* END OF OUR CODE
		*
		*/

		return tree;
	}

}