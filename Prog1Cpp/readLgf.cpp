#include "readLgf.h"

#include <fstream>

// Convert edgeline to EdgeWithWeight
EdgeWithWeight convertEdgeline(const std::string &input) {
  std::stringstream ss(input);
  int node1, node2, w;
  ss >> node1;
  ss >> node2;
  ss >> w;
  assert(node1 >= 1 && node2 >= 1);
  return std::make_pair(
      std::make_pair((unsigned int)(node1 - 1), (unsigned int)(node2 - 1)), w);
}

// Read Graph from file in lemongraph format (.lgf)
Graph readGraphFromFile(const std::string &filename) {
  std::ifstream ifs;
  ifs.open(filename, std::ifstream::in);
  std::string line;
  for (unsigned int i = 0; i < 4; ++i) {
    std::getline(ifs, line);
  }
  Graph g;
  std::getline(ifs, line);
  while (not line.empty()) {
    boost::add_vertex(g);
    std::getline(ifs, line);
  }

  for (unsigned int i = 0; i < 2; ++i) {
    std::getline(ifs, line);
  }

  std::getline(ifs, line);
  EdgeWithWeight edgew;
  while (ifs && (not line.empty())) {
    edgew = convertEdgeline(line);
    add_edge(edgew.first.first, edgew.first.second,
             EdgeWeightProperty(edgew.second), g);
    std::getline(ifs, line);
  }

  ifs.close();

  return g;
}

// Read Terminal Nodes for Steiner Tree from file
std::vector<Vertex> readTerminalsFromFile(const std::string &filename,
                                          const Graph &g) {
  std::ifstream ifs;
  ifs.open(filename, std::ifstream::in);
  std::string line;
  std::vector<Vertex> terminals;
  int nodeLabel;
  std::getline(ifs, line);
  std::pair<VertexIterator, VertexIterator> vertices = boost::vertices(g);
  while (not line.empty()) {
    nodeLabel = std::stoi(line) - 1;
    Vertex v = *(vertices.first + nodeLabel);
    terminals.push_back(v);
    std::getline(ifs, line);
  }
  return terminals;
}
