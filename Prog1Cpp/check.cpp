#include "check.h"

#include <iostream>

#include <boost/graph/connected_components.hpp>
#include <boost/graph/dijkstra_shortest_paths.hpp>
#include <boost/graph/kruskal_min_spanning_tree.hpp>

// Compute total sum of a vector of weighted edges
int weightSum(const std::vector<Edge> &edges,
              const EdgeWeightPropertyMap &edgeWeightMap) {
  int totalWeight{0};
  for (std::vector<Edge>::const_iterator ei = edges.begin(); ei != edges.end();
       ++ei)
    totalWeight += edgeWeightMap[*ei];
  return totalWeight;
}

// Check whether the given mst is a proper minimal spanning tree for graph g
bool checkSpanningTree(const Graph &g, const std::vector<Edge> &mst,
                       const EdgeWeightPropertyMap &edgeWeightMap) {
  std::vector<int> component(num_vertices(g));
  if (boost::connected_components(g, &component[0]) != 1) {
    std::cout << "Graph has too many or not enough connected components"
              << std::endl;
    return false;
  }
  if (mst.size() + 1 != num_vertices(g)) {
    std::cout << "The computed graph contains too many or not enough edges"
              << std::endl;
    return false;
  }
  int mstCosts{weightSum(mst, edgeWeightMap)};
  std::vector<Edge> spanning_tree;
  boost::kruskal_minimum_spanning_tree(g, std::back_inserter(spanning_tree));
  if (mstCosts == weightSum(spanning_tree, edgeWeightMap)) {
    std::cout << "The computed graph is a minimum spanning tree of weight "
              << mstCosts << "." << std::endl;
    return true;
  }
  assert(mstCosts > weightSum(spanning_tree, edgeWeightMap));
  std::cout << "The computed graph is a non-minimum spanning tree of weight "
            << mstCosts << "." << std::endl;
  return false;
}

// Check whether the given path is a shortest path in g
bool checkPath(const Graph &g, const Vertex &startVertex,
               const Vertex &endVertex, const std::vector<Vertex> &path,
               const EdgeWeightPropertyMap &edgeWeightMap) {
  // check whether the vertex sequence in path represents a valid path in g+
  if (path.empty()) {
    std::cout << "This is not a valid path." << std::endl;
    return false;
  }
  for (std::vector<Vertex>::const_iterator i = path.begin() + 1;
       i != path.end(); ++i) {
    if (!edge(*(i - 1), *i, g).second) {
      std::cout << "This is not a valid path." << std::endl;
      return false;
    }
  }
  // check whether the first and last vertex in path match startVertex and
  // endVertex
  if (path.front() != startVertex or path.back() != endVertex) {
    std::cout << "This is not a path from " << startVertex << " to "
              << endVertex << " but from " << path.front() << " to "
              << path.back() << std::endl;
    return false;
  }
  // >>path<< is a valid path from startVertex to endVertex
  // check whether >>path<< is a shortest path
  // compute the length of >>path<<
  int pathWeight{0};
  for (std::vector<Vertex>::const_iterator i = path.begin() + 1;
       i != path.end(); ++i) {
    pathWeight += edgeWeightMap[edge(*(i - 1), *i, g).first];
  }
  // compute a shortest path from startVertex to endVertex
  std::vector<Vertex> p(num_vertices(g));
  std::vector<int> d(num_vertices(g));
  boost::dijkstra_shortest_paths(
      g, startVertex, boost::predecessor_map(&p[0]).distance_map(&d[0]));
  if (pathWeight > d[endVertex]) {
    std::cout << "The sequence of nodes defines a non-shortest path of weight "
              << pathWeight << "." << std::endl;
    return false;
  }
  assert(pathWeight == d[endVertex]);
  std::cout << "The sequence of nodes defines a shortest path of weight "
            << pathWeight << "." << std::endl;

  return true;
}
bool checkSteinerTree(const Graph &g, const std::vector<Edge> &steiner_tree,
                      const std::vector<Vertex> &terminals,
                      const EdgeWeightPropertyMap &edgeWeightMap) {
  // Check whether all terminals are in the same connected component
  std::vector<int> component(num_vertices(g));
  connected_components(g, &component[0]);
  int terminalsComponent = component[terminals[0]];
  for (std::vector<Vertex>::const_iterator i = terminals.begin();
       i != terminals.end(); ++i) {
    if (component[*i] != terminalsComponent) {
      std::cout << "The computed graph does not connect all terminal nodes."
                << std::endl;
      return false;
    }
  }
  int treeWeight = weightSum(steiner_tree, edgeWeightMap);
  std::cout << "The computed graph connects all terminal nodes and has weight "
            << treeWeight << "." << std::endl;
  return true;
}
