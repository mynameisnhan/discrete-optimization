import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet; // OUR CODE

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

// Implements shortest path algorithms. Can return distances to vertices from a given start vertex and the corresponding shortest path.
public class myShortestPath {
	// Variables
	private Graph<Integer, DefaultWeightedEdge> graph;			// The graph in which we search for a shortest path
	private Integer startVertex;								// The start vertex
	private HashMap<Integer, Double> distances;					// Distances from each vertex to the start vertex
	private HashMap<Integer, Integer> predecessors;				// Predecessors for each vertex on a shortest path to the start vertex
	
	// Constructor
	public myShortestPath(Graph<Integer, DefaultWeightedEdge> graph, Integer startVertex) {
		this.graph = graph;
		this.startVertex = startVertex;
		distances = new HashMap<Integer, Double>();
		predecessors = new HashMap<Integer, Integer>();
	}
	
	// Computes distances and predecessors for all nodes
	public void computeDistPred() {
		for (Integer vertex : graph.vertexSet()) {
			distances.put(vertex, Double.MAX_VALUE);
			predecessors.put(vertex, null);
        }
		distances.put(startVertex, 0.);
		
		/*
		 *
		 * START OF OUR CODE
		 *
		 */
		 
		// Dijkstra's algorithm
		
		// While there are still unvisited vertices...
		HashSet<Integer> unvisitedVertexSet = new HashSet<>(graph.vertexSet());
//        while (unvisitedVertexSet.size() != 0) {
        while (!unvisitedVertexSet.isEmpty()) {            
            // ...select the vertex with the smallest cumulative distance and remove it from vertexSet
            double i = Double.MAX_VALUE;
            Integer v_i=0;
            for (Integer v : unvisitedVertexSet) {
                if(distances.get(v) < i) {
                    i = distances.get(v);
                    v_i = v;
                }
            }

            unvisitedVertexSet.remove(v_i);
            
            // ...for each neighbor of the vertex...
            HashSet<DefaultWeightedEdge> i_incomingEdges = new HashSet<>(graph.incomingEdgesOf(v_i));
            for (DefaultWeightedEdge a : i_incomingEdges) {
                int source = graph.getEdgeSource(a);
                int target = graph.getEdgeTarget(a);
                if (source == v_i) {
                    if (distances.get(v_i) + graph.getEdgeWeight(a) < distances.get(target)) {
                        distances.put(target, distances.get(v_i) + graph.getEdgeWeight(a));
                        predecessors.put(target, v_i);
                    }
                }
                
                else {
                    if (distances.get(v_i) + graph.getEdgeWeight(a) < distances.get(source)) {
                        distances.put(source, distances.get(v_i) + graph.getEdgeWeight(a));
                        predecessors.put(source, v_i);
                    }
                }
            }
        }
        
        /*
		 *
		 * END OF OUR CODE
		 *
		 */

	}
	
	
	// Constructs the shortest path from the start node to a given end node using the list of predecessors
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
		
	// Returns the distance to a given end node
	public double getDistanceToNode(Integer endVertex) {
		return distances.get(endVertex);
    }
}
