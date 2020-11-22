#include "randomVertex.h"

#include <random>

#include <boost/graph/random.hpp>

std::pair<Vertex, Vertex> get_tuple_of_distinct_vertices(const Graph &g) {
  // generate random start and end vertex
  time_t seed = time(0);
  std::mt19937 RandomNumGen(seed++);
  Vertex startVertex = random_vertex(g, RandomNumGen);
  Vertex endVertex;
  do {
    endVertex = random_vertex(g, RandomNumGen);
  } while (startVertex == endVertex);
  return std::make_pair(startVertex, endVertex);
}
