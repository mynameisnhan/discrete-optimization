/* Read only
Changes will have no impact on execution/evaluation within VPL
*/

import java.util.ArrayList;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

public class CheckerPath {
	
	public static void checkPath(Graph<Integer, DefaultWeightedEdge> graph, Integer startVertex, Integer endVertex, ArrayList<Integer> path) {
		boolean isPath = true;
		if (path.get(0).equals(startVertex) && path.get(path.size()-1).equals(endVertex)) {
			for (int i = 0; i < path.size()-1; i++) {
				if (!graph.containsEdge(path.get(i), path.get(i+1))) {
					isPath = false;
					break;
				}
			}
		}
		else {
			isPath = false;
		}

		if (isPath) {
			double weight = 0;
			for (int i = 0; i < path.size()-1; i++) {
				weight += graph.getEdgeWeight(graph.getEdge(path.get(i), path.get(i+1)));
			}
			if (weight == BellmanFordShortestPath.findPathBetween(graph, startVertex, endVertex).getWeight()) {
				System.out.println("The sequence of nodes defines a shortest path of weight "+weight+".");
			}
			else {
				System.out.println("The sequence of nodes defines a non-shortest path of weight "+weight+".");
			}
		}
		else {
			System.out.println("The sequence of nodes does not define a valid path.");
		}
	}
}
