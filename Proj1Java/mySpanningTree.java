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
\
		/*
		START OF CODE
		 */

		//Kruskal's algorithm
		//Sort edges from smallest to largest weight
		var edges = graph.edgeSet().stream().sorted(Comparator.comparingDouble(graph::getEdgeWeight)).iterator();

		//Initialize connected components, in the beginning all vertices are their own component
		disjointSet disjointSet = new disjointSet(graph.vertexSet());

		//While there are multiple components, add edges that connect two
		while(edges.hasNext()) {
			DefaultWeightedEdge edge = edges.next();
			
			//If source and target are in different components, adding the edge between them does not result in a cycle, so add it
			if(!disjointSet.SEARCH(graph.getEdgeSource(edge)).equals(disjointSet.SEARCH(graph.getEdgeTarget(edge)))){
				//Add edge
				tree.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
				//Fuse the two components together
				disjointSet.MERGE(disjointSet.SEARCH(graph.getEdgeSource(edge)), cdisjointSet.SEARCH(graph.getEdgeTarget(edge)));
			}
		}

		/*
		END OF CODE
		 */

		 return tree;
	}
}

class disjointSet {
    private Hashtable<Integer, Integer> vertexComponentAssociation;
    private Set<Integer> vertices;

    public disjointSet(Set<Integer> vertexSet) {
        vertexComponentAssociation = new Hashtable<>();
        vertices = vertexSet;
        for (Integer vertex: vertexSet) {
            vertexComponentAssociation.put(vertex, vertex);
            // no
        }
    }

    public Integer SEARCH(Integer vertex){
        return vertexComponentAssociation.get(vertex);
    }

    public void MERGE(Integer componentAId, Integer componentBId){
        for (Integer vertex: vertices) {
            if (vertexComponentAssociation.get(vertex).equals(componentBId)){
                vertexComponentAssociation.put(vertex, componentAId);
            }
        }
    }


}
