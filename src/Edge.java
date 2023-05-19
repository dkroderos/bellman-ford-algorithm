public class Edge<T> {
    private T node;
    private T destination;
    private Integer weight; 

    public Edge(T node, T destination, Integer weight) {
        this.node = node;
        this.destination = destination;
        this.weight = weight;
    }

    public T getNode() {
        return this.node;
    }
    
    public void setNode(T node) {
        this.node = node;
    }

    public T getDestination() {
        return this.destination;
    }
    
    public void setDestination(T destination) {
        this.destination = destination;
    }

    public Integer getWeight() {
        return this.weight;
    }
    
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(" + this.node + ")---(" + this.weight + ")--->(" + this.destination + ")");

        return sb.toString();
    }
}
