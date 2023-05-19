import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

public class Graph<T> {
    private HashMap<T, ArrayList<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<T, ArrayList<Edge>>();
    }

    public void addEdge(T node, T destination, Integer weight) {
        this.adjacencyList.putIfAbsent(node, new ArrayList<Edge>());
        this.adjacencyList.putIfAbsent(destination, new ArrayList<Edge>());
        this.adjacencyList.get(node).add(new Edge(destination, weight));
    }

    public ArrayList<Edge> getNeighbors(T node) {
        return this.adjacencyList.getOrDefault(node, new ArrayList<Edge>());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (T node : adjacencyList.keySet()) {
            sb.append("\n(" + node + ")\n|\n");
            ArrayList<Edge> edges = adjacencyList.get(node);

            for (Edge edge : edges) {
                sb.append(edge.toString() + "\n");
            }
        }

        return sb.toString();
    }

    public void removeAllNodesExcpet(T node) {
        Iterator<T> iterator = adjacencyList.keySet().iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (!t.equals(node))
                iterator.remove();
        }
    }

    private class Edge {
        private T destination;
        private Integer weight;

        public Edge(T destination, Integer weight) {
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "|---[" + (weight >= Short.MAX_VALUE ? "∞" : weight) + "]---> (" + destination + ")";
        }
    }

}
