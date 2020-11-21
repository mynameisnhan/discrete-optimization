import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.jgrapht.Graph;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class mySpanningTree {
	
	//Receives a graph and computes a minimum spanning tree
	public static AsSubgraph<Integer, DefaultWeightedEdge> computeMST(Graph<Integer, DefaultWeightedEdge> graph) {
		AsSubgraph<Integer, DefaultWeightedEdge> tree = new AsSubgraph<Integer, DefaultWeightedEdge>(graph, graph.vertexSet(), new HashSet<DefaultWeightedEdge>());
		/**		
		 * 
		 * 		Implement your algorithm here
		 * 
		**/
		return tree;
	}
}
