import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

//Implements shortest path algorithms. Can return distances to vertices from a given start vertex and the corresponding shortest path.
public class myShortestPath {
	//Variables
	private Graph<Integer, DefaultWeightedEdge> graph;			//The graph in which we search for a shortest path
	private Integer startVertex;								//The start vertex
	private HashMap<Integer, Double> distances;					//Distances from each vertex to the start vertex
	private HashMap<Integer, Integer> predecessors;				//Predecessors for each vertex on a shortest path to the start vertex
	
	//Constructor
	public myShortestPath(Graph<Integer, DefaultWeightedEdge> graph, Integer startVertex) {
		this.graph = graph;
		this.startVertex = startVertex;
		distances = new HashMap<Integer, Double>();
		predecessors = new HashMap<Integer, Integer>();
	}
	
	//Computes distances and predecessors for all nodes
	public void computeDistPred() {
		for (Integer vertex : graph.vertexSet()) {
			distances.put(vertex, Double.MAX_VALUE);
			predecessors.put(vertex, null);
        }
		distances.put(startVertex, 0.);
		/**		
		 * 
		 * 		Implement your algorithm here
		 * 
		**/
	}
	
	
	//Constructs the shortest path from the start node to a given end node using the list of predecessors
	public ArrayList<Integer> constructPathToNode(Integer endVertex) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		path.add(endVertex);
		while (!path.contains(startVertex)) {
			Integer currentVertex = path.get(path.size()-1);
			Integer nextVertex = predecessors.get(currentVertex);
			path.add(nextVertex);
		}
		Collections.reverse(path);
		return path;
	}
		
	//Returns the distance to a given end node
	public double getDistanceToNode(Integer endVertex) {
		return distances.get(endVertex);
	}
}
