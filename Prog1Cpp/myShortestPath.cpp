#include "myShortestPath.h"


std::vector<Vertex> my_shortest_path(const Graph &g, const Vertex &startVertex,
                                     const Vertex &endVertex) {
  std::vector<Vertex> path;
  // compute a shortest path between startVertex and endVertex and save it in
  // the vector >>path<<
  //TODO
  const int number_of_vertices = num_vertices(g);

  std::vector<Vertex> prev_vertex(number_of_vertices, startVertex);
  std::vector<unsigned int> dist(number_of_vertices, UINT_MAX);

    /*
     * To determine the next node to be processed, the nodes, including their weights, are put into a
     * Priority queue saved. The first entry is in a "normal" priority queue
     * Knots with the greatest weight / greatest distance. To change this, the sorting can be done when creating
     * must be specified. Std :: greater <> is used to change the sorting to ascending order. The next
     * The nodes to be processed must have the lowest weight of the nodes to be processed.
     */
  std::priority_queue<VertexDistance, std::vector<VertexDistance>, std::greater<VertexDistance>> pq;

    /*
     * So that vertices are not processed twice, all processed vertices are saved in a vector.
     * In order to check whether the entry is already in the vector, an iterator is required which covers all elements in the
     * Vector iteriert.
     */
  std::vector<int> completed_vertices;
  std::vector<int>::iterator completed_vertices_iterator;

    /*
     * The weights of the edges are read from the edge_weight property.
     * For the iteration of all edges of a vertex, two iterators are required, the first one over the
     * Edge list is running and the second one that shows the end of the edge list.
     *
     * For the sake of simplicity, a variable of the Edge type is used to save the * dereferenced * edge iterator
     * is used for easier access.
     *
     * The target_vertex variable is the target of an edge starting from the currently considered vertex.
     */
  const EdgeWeightMap weights = get(boost::edge_weight, g);
  OutEdgeIterator out_edge_i, out_edge_end;
  Edge working_edge;
  Vertex target_vertex;

    /*
     * After the initialization - as provided in the Dijkstra algorithm - the distance from the startVertex to
     * the correct distance 0 is set. The start node is then added to the priority queue to be processed
     * added to the weight of the distance.
     */
  dist [startVertex] = 0 ;
  pq.push(std::make_pair(0, startVertex));

    /*
     * As long as the priority queue with the nodes to be processed is not empty, these nodes must be processed
     * will.
     */
  while (!pq.empty()) {

        /*
         * The first element - with the shortest distance - becomes the first element of the priority queue, precisely the one
         * first node, read out and then deleted from the queue.
         */
    Vertex working_vertex = pq.top().second;
    pq.pop();

        /*
         * It is then checked whether the node has already been processed by iterating over the vector.
         * If the node has already been processed, the next entry will be in the priority queue
         * derived.
         *
         * If the node has not yet been processed, it will then be processed and added to the list
         * the processed nodes are included so that they are not processed again.
         */
      completed_vertices_iterator = std::find(completed_vertices.begin(), completed_vertices.end(), working_vertex);
      if (completed_vertices_iterator != completed_vertices.end()) {
          // The element has already been processed and is no longer considered.
          continue;
      }
      completed_vertices.push_back(working_vertex);

        /*
         * Two iterators are required to iterate over the edges of a vertex. The second iterator will
         * Required to check whether the iteration has already reached the end. The first iterator will
         * used to address the different edges.
         */
      tie(out_edge_i, out_edge_end) = out_edges(working_vertex, g);

        /*
         * In the following, all available edges of the vertex are iterated and the distance of the neighbors if necessary
         * adapt.
         */
      for (; out_edge_i != out_edge_end; out_edge_i++) {
            /*
             * For the sake of clarity, the edge iterator is dereferenced and the object is stored in the working_edge variable.
             * target_vertex is the end point of the edge under consideration.
             */
          working_edge = *out_edge_i;
          target_vertex = target(working_edge, g);

            /*
             * It is checked whether the distance of the starting node (the edge) plus the edge weight is really less than
             * is the currently stored distance of the end node.
             *
             * If the distance is really smaller, the distance becomes the end node of the Kange and the predecessor
             * updated.
             */
          if (dist[working_vertex] + weights[working_edge] < dist[target_vertex]) {
              dist[target_vertex] = dist[working_vertex] + weights[working_edge];
              prev_vertex[target_vertex] = working_vertex;
          }

            /*
             * The end node is then transferred to the
             * Added priority queue. If the node has already been processed, it will not be repeated
             * added to the priority queue.
             */
          completed_vertices_iterator = std::find(completed_vertices.begin(), completed_vertices.end(),
                                                    target_vertex);
          if (completed_vertices_iterator == completed_vertices.end()) {
              pq.push(std::make_pair(dist[target_vertex], target_vertex));
          }
      }
  }

    /*
     * After the shortest path to startVertex has been determined for all nodes, now only the shortest has to be
     * Path from the start node to the end node can be saved in the return variable path.
     *
     * For this, the predecessor of the endVertex is always inserted backwards at the first position of the vector.
     */
  path.insert(path.begin(), endVertex);
  Vertex working_vertex = prev_vertex[endVertex];
  while (working_vertex != startVertex) {
      path.insert(path.begin(), working_vertex);
      working_vertex = prev_vertex[working_vertex];
  }
  path.insert(path.begin(), startVertex);
  // return path
  return path;
}
