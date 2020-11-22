import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors; // OUR CODE


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
		HashMap<Integer, Boolean> visited = new HashMap<>();

		// Fer each vertex...
		for (Integer vertex : graph.vertexSet()) {
			distances.put(vertex, Double.MAX_VALUE); // ...set distance to infinity
			predecessors.put(vertex, null); // ...set prev to null
			visited.put(vertex, Boolean.FALSE);
		}

		// Set start vertex's distance to 0
		distances.put(startVertex, 0.000);
		visited.put(startVertex, Boolean.TRUE);

		// Update all neighbours of start vertex
		// graph.vertexSet().stream().filter(x -> graph.containsEdge(startVertex, x)).filter(x -> visited.get(x) == Boolean.FALSE).forEach(x -> updateVertexDistance(startVertex,x));
		graph.vertexSet().stream().filter(x -> graph.containsEdge(startVertex, x)).filter(x -> visited.get(x).equals(Boolean.FALSE))
		.forEach(x -> updateVertexDistance(startVertex, x));

		// For each vertex...
		for (Integer vertex : graph.vertexSet()) {

			// Determine if there are any reachable (non infinite distance) non-added vertices
			if (distances.entrySet().stream().anyMatch(x -> visited.get(x.getKey())==Boolean.FALSE && x.getValue()!=Double.MAX_VALUE)) {
			
				// Get vertex,distance pair with minimum distance
				HashMap.Entry<Integer, Double> min = Collections.min(distances.entrySet().stream()
				.filter(x -> visited.get(x.getKey()) == Boolean.FALSE).collect(Collectors.toList()), HashMap.Entry.comparingByValue());
				
				// Set the minimum vertex to added
				visited.put(min.getKey(), Boolean.TRUE);
			
				// Update all neighbouring non-added vertices
				graph.vertexSet().stream().filter(x -> graph.containsEdge(min.getKey(), x))
				.filter(x -> visited.get(x) == Boolean.FALSE).forEach(x -> updateVertexDistance(min.getKey(), x));
			}
			
			else {	
				break;
			}
		}

		/*
		*
		* END OF OUR CODE
		*
		*/
	}

	private void updateVertexDistance(Integer source, Integer target){
		if(distances.get(source) + graph.getEdgeWeight(graph.getEdge(source,target)) < distances.get(target)){
			distances.put(target, distances.get(source) + graph.getEdgeWeight(graph.getEdge(source,target)));
			predecessors.put(target, source);
		}
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
