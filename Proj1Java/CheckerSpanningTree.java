/* Read only
Changes will have no impact on execution/evaluation within VPL
*/

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class CheckerSpanningTree {
	
	public static void checkSpanningTree(Graph<Integer, DefaultWeightedEdge> graph, AsSubgraph<Integer, DefaultWeightedEdge> tree) {
		if(!new ConnectivityInspector<Integer, DefaultWeightedEdge>(tree).isConnected()) {
			System.out.println("The computed graph is not connected.");
			return;
		}
		if (tree.edgeSet().size() != tree.vertexSet().size()-1) {
			System.out.println("The computed graph contains too many edges.");
			return;
		}
		double weight = 0;
		for (DefaultWeightedEdge edge : tree.edgeSet()) {
			weight += tree.getEdgeWeight(edge);
		}
		if (weight == new KruskalMinimumSpanningTree<Integer, DefaultWeightedEdge>(graph).getSpanningTree().getWeight()) {
			System.out.println("The computed graph is a minimum spanning tree of weight "+weight+".");
		}
		else {
			System.out.println("The computed graph is a non-minimum spanning tree of weight "+weight+".");
		}
	}
}
