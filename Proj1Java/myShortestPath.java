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
		
		//START OF OUR CODE
		public void bellmanFord(){
			//Distance to all vertices is inf and pred is null
			for (Integer vertex : graph.vertexSet()) {
				distances.put(vertex, Double.MAX_VALUE);
				predecessors.put(vertex, null);
			}
			//Distance to start vertex is zero
			distances.put(startVertex, 0.);
	
			//V-1 times
			for (int i = 1; i < graph.vertexSet().size()-1; i++) {
				//relax each edge
				for (var edge: graph.edgeSet()) {
					//Get vertices of edge and respective weight
					Integer u = graph.getEdgeSource(edge);
					Integer v = graph.getEdgeTarget(edge);
					double weight = graph.getEdgeWeight(edge);
	
					//Currently checks edge both ways
					//TODO: directed graph handling
					if(distances.get(u) + weight < distances.get(v)) {
						distances.put(v, distances.get(u) + weight);
						predecessors.put(v, u);
					}else if(distances.get(v) + weight < distances.get(u)){
						distances.put(u, distances.get(v) + weight);
						predecessors.put(u, v);
					}
	
				}
			}
		}
	
		/**
		 * Dijkstra's algorithm
		 */
		public void dijkstra() {
			Map<Integer,Boolean> colour = new HashMap<>();
			//Initialize all distances as Inf, pred as null
			for (Integer vertex : graph.vertexSet()) {
				distances.put(vertex, Double.MAX_VALUE);
				predecessors.put(vertex, null);
				colour.put(vertex,Boolean.FALSE);
			}
			//Add start vertex with distance = 0
			distances.put(startVertex, 0.);
			colour.put(startVertex,Boolean.TRUE);
			//Update all neighbours of start vertex
			adjacent(startVertex)
					.filter(x->colour.get(x)==Boolean.FALSE)
					.forEach(x->updateVertexDistance(startVertex,x));
	
			//Only works on graphs where a path to every other node is possible (as of now)
			//TODO: break if only unreachable nodes remain
			for (int i = 0; i < graph.vertexSet().size()-1; i++) {
				//Get vertex,distance pair with minimum distance
				Map.Entry<Integer, Double> min = Collections.min(
						distances.entrySet().stream()
								.filter(x->colour.get(x.getKey())==Boolean.FALSE) //Only consider unadded vertices
								.collect(Collectors.toList()),
						Map.Entry.comparingByValue());
				//Set the minimum vertex to added
				colour.put(min.getKey(),Boolean.TRUE);
				//Update all neighbours added vertex
				adjacent(min.getKey())
						.filter(x->colour.get(x)==Boolean.FALSE)
						.forEach(x->updateVertexDistance(min.getKey(),x));
			}
		}
	
		private Stream<Integer> adjacent(Integer start){
			return graph.vertexSet().stream().filter(x-> graph.containsEdge(start,x));
		}
	
		private void updateVertexDistance(Integer source, Integer target){
			if(distances.get(source) + graph.getEdgeWeight(graph.getEdge(source,target)) < distances.get(target)){
				distances.put(target, distances.get(source) + graph.getEdgeWeight(graph.getEdge(source,target)));
				predecessors.put(target, source);
			}
		}
		// END OF OUR CODE

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
