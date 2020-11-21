/* Read only
Changes will have no impact on execution/evaluation within VPL
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Main {
	public static void main(String[] args) throws IOException {
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Which algorithm would you like to test (MST,ShortestPath,SteinerTree)?");
        String algorithm = reader.readLine();
        System.out.println("Which instance would you like to solve (1,2,3,4,5,6)?");
        String graphNumber = reader.readLine();
        
        if (algorithm.equals("MST")) {
			System.out.println("Computing MST of Graph "+graphNumber+":");
			Graph<Integer, DefaultWeightedEdge> graph = GraphReader.readGraph("Graph"+graphNumber+".lgf");
			AsSubgraph<Integer, DefaultWeightedEdge> tree = mySpanningTree.computeMST(graph);
			CheckerSpanningTree.checkSpanningTree(graph, tree);
		}
        		
        if (algorithm.equals("ShortestPath")) {
			System.out.println("Computing ShortestPath of Graph "+graphNumber+":");
			Graph<Integer, DefaultWeightedEdge> graph = GraphReader.readGraph("Graph"+graphNumber+".lgf");
			ArrayList<Integer> vertexList = new ArrayList<Integer>(graph.vertexSet());
			Random randomGenerator = new Random();
			Integer startVertex = vertexList.get(randomGenerator.nextInt(vertexList.size()));
			Integer endVertex = vertexList.get(randomGenerator.nextInt(vertexList.size()));
			myShortestPath shortestPath = new myShortestPath(graph, startVertex);
			shortestPath.computeDistPred();
			ArrayList<Integer> path = shortestPath.constructPathToNode(endVertex);
			CheckerPath.checkPath(graph, startVertex, endVertex, path);
        }
		
        if (algorithm.equals("SteinerTree")) {
        	String terminalNumber = "";
        	String output = "Computing SteinerTree of Graph "+graphNumber;
        	if (Integer.parseInt(graphNumber)==4) {
        	   System.out.println("Which set of terminal nodes do you want to choose (1,2,3,4)?");
              	terminalNumber = reader.readLine();
              	output += " with Terminals"+terminalNumber;
        	}
        	if (Integer.parseInt(graphNumber)==5) {
        	   System.out.println("Which set of terminal nodes do you want to choose (1,2,3)?");
              	terminalNumber = reader.readLine();
              	output += " with Terminals"+terminalNumber;
        	}
            output += ":";
			System.out.println(output);
			Graph<Integer, DefaultWeightedEdge> graph = GraphReader.readGraph("Graph"+graphNumber+".lgf");
			HashSet<Integer> terminals = GraphReader.readTerminals("Graph"+graphNumber+"_Terminals"+terminalNumber+".txt");
			AsSubgraph<Integer, DefaultWeightedEdge> steinerTree = mySteinerTree.computeSteinerTree(graph, terminals);
			CheckerSteinerTree.checkSteinerTree(steinerTree, terminals);
		}
        reader.close();
	}
}