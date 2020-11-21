import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.jgrapht.Graph;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class mySteinerTree {
	
	//Receives a graph and computes a steiner tree
	public static AsSubgraph<Integer, DefaultWeightedEdge> computeSteinerTree(Graph<Integer, DefaultWeightedEdge> graph, HashSet<Integer> terminals) {
		AsSubgraph<Integer, DefaultWeightedEdge> steinerTree = new AsSubgraph<Integer, DefaultWeightedEdge>(graph, new HashSet<Integer>(), new HashSet<DefaultWeightedEdge>());
		for (Integer terminal : terminals) {
			steinerTree.addVertex(terminal);
		}
		/**		
		 * 
		 * 		Implement your algorithm here
		 * 
		**/
		return steinerTree;
	}

}
