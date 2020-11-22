#include "types.h"

// Convert edgeline to EdgeWithWeight
EdgeWithWeight convertEdgeline(const std::string &input);

// Read Graph from file in lemongraph format (.lgf)
Graph readGraphFromFile(const std::string &filename);

// Read Terminal Nodes for Steiner Tree from file
std::vector<Vertex> readTerminalsFromFile(const std::string &filename,
                                          const Graph &g);
