#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/graph_traits.hpp>

typedef boost::property<boost::edge_weight_t, unsigned int> EdgeWeightProperty;
typedef boost::adjacency_list<boost::vecS, boost::vecS, boost::undirectedS,
                              boost::no_property, EdgeWeightProperty>
    Graph;
typedef boost::graph_traits<Graph>::vertex_descriptor Vertex;
typedef boost::graph_traits<Graph>::edge_descriptor Edge;
typedef boost::graph_traits<Graph>::vertex_iterator VertexIterator;
typedef boost::graph_traits<Graph>::edge_iterator EdgeIterator;
typedef std::pair<std::pair<unsigned int, unsigned int>, unsigned int>
    EdgeWithWeight;
typedef boost::property_map<Graph, boost::edge_weight_t>::type
    EdgeWeightPropertyMap;
