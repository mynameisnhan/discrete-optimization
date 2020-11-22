#include "check.h"
#include "randomVertex.h"
#include "readLgf.h"
#include "types.h"

#include "myShortestPath.h"
#include "mySpanningTree.h"
#include "mySteinerTree.h"

#include <iostream>
#include <string>

int main() {
  std::cout << "Which algorithm would you like to test "
               "(MST,ShortestPath,SteinerTree)?"
            << std::endl;
  std::string algorithm;
  std::cin >> algorithm;
  std::cout << "Which instance would you like to solve (1,2,3,4,5,6)?"
            << std::endl;
  std::string graphNumber;
  std::cin >> graphNumber;

  Graph g{readGraphFromFile("Graph" + graphNumber + ".lgf")};

  // The property map associated with the weights.
  EdgeWeightPropertyMap edgeWeightMap = get(boost::edge_weight, g);

  if (algorithm == "MST") {
    std::cout << "Computing " + algorithm + " of Graph " + graphNumber + ":"
              << std::endl;
    // compute a minimum spanning tree
    std::vector<Edge> spanning_tree{my_spanning_tree(g)};
    checkSpanningTree(g, spanning_tree, edgeWeightMap);
  }

  if (algorithm == "ShortestPath") {
    std::cout << "Computing " + algorithm + " of Graph " + graphNumber + ":"
              << std::endl;
    // generate random start and end vertex
    std::pair<Vertex, Vertex> stVertices{get_tuple_of_distinct_vertices(g)};
    // compute a shortest path
    std::vector<Vertex> path{
        my_shortest_path(g, stVertices.first, stVertices.second)};
    // check if the computed path is a shortest path
    checkPath(g, stVertices.first, stVertices.second, path, edgeWeightMap);
  }

  if (algorithm == "SteinerTree") {
    // read terminal nodes from file
    std::vector<Vertex> terminals;
    if (graphNumber == "4") {
      std::cout
          << "Which set of terminal nodes do you want to choose (1,2,3,4)?"
          << std::endl;
      std::string t;
      std::cin >> t;
      std::cout << "Computing " + algorithm + " of Graph " + graphNumber +
                       " with Terminals" + t + ":"
                << std::endl;
      terminals = readTerminalsFromFile(
          "Graph" + graphNumber + "_Terminals" + t + ".txt", g);
    } else {
      if (graphNumber == "5") {
        std::cout
            << "Which set of terminal nodes do you want to choose (1,2,3)?"
            << std::endl;
        std::string t;
        std::cin >> t;
        std::cout << "Computing " + algorithm + " of Graph " + graphNumber +
                         " with Terminals" + t + ":"
                  << std::endl;
        terminals = readTerminalsFromFile(
            "Graph" + graphNumber + "_Terminals" + t + ".txt", g);
      } else {
        std::cout << "Computing " + algorithm + " of Graph " + graphNumber + ":"
                  << std::endl;
        terminals =
            readTerminalsFromFile("Graph" + graphNumber + "_Terminals.txt", g);
      }
    }

    // compute a steiner tree for g with terminals g
    std::vector<Edge> steiner_tree{my_steiner_tree(g, terminals)};
    // check computed steiner tree
    checkSteinerTree(g, steiner_tree, terminals, edgeWeightMap);
  }
  g.clear();

  return 0;
}
