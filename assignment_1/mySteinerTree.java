import java.util.ArrayList;
// import java.util.HashMap;
import java.util.HashSet;

import org.jgrapht.Graph;
import org.jgrapht.graph.AsSubgraph;
// import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class mySteinerTree {
	
	// Receives a graph and computes a steiner tree
	public static AsSubgraph<Integer, DefaultWeightedEdge> computeSteinerTree(Graph<Integer, DefaultWeightedEdge> graph, HashSet<Integer> terminals) {
		AsSubgraph<Integer, DefaultWeightedEdge> steinerTree = new AsSubgraph<Integer, DefaultWeightedEdge>(graph, new HashSet<Integer>(), new HashSet<DefaultWeightedEdge>());
		for (Integer terminal : terminals) {
			steinerTree.addVertex(terminal);
		}

		/*
		*
		* START OF OUR CODE
		*
		*/

		// Find the MST of the graph
		AsSubgraph<Integer, DefaultWeightedEdge> mst = new AsSubgraph<Integer, DefaultWeightedEdge>(graph, new HashSet<Integer>(), new HashSet<DefaultWeightedEdge>());
		mst = mySpanningTree.computeMST(graph);

		// Repeatedly remove vertices
		ArrayList<Integer> vertexSet = new ArrayList<Integer>(mst.vertexSet());
		int prevVertexSize = 0;
		int currVertexSize = mst.vertexSet().size();

		while (prevVertexSize != currVertexSize) {
			prevVertexSize = currVertexSize;
			
		    for (Integer vertex : vertexSet) {
    			if (!terminals.contains(vertex) && mst.containsVertex(vertex) ){
    			    if (mst.degreeOf(vertex) == 1) {
        			    ArrayList<DefaultWeightedEdge> temp = new ArrayList<DefaultWeightedEdge>(mst.edgesOf(vertex));
        			    mst.removeEdge(temp.get(0));
        			    mst.removeVertex(vertex);
    			    }
    			}
            }
            currVertexSize = mst.vertexSet().size();
		}
		
		steinerTree = mst;
		
		/*
		*
		* END OF OUR CODE
		*
		*/
		
		return steinerTree;
	}
}