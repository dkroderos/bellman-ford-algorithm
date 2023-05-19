import java.util.Scanner;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;

public class BellmanFordAlgorithm {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Edge<String>> edges = new ArrayList<Edge<String>>();
    private static HashSet<String> nodes = new HashSet<String>();
    private static String startingNode = "";
    private static ArrayList<Edge<String>> shortestPaths = new ArrayList<Edge<String>>();
    private static Graph<String> graph;
    private static HashMap<String, Integer> weights;

    public static void main(String[] args) throws Exception {
        String bellmanFord = " ____       _ _                             _____             _\n"
                + "| __ )  ___| | |_ __ ___   __ _ _ __       |  ___|__  _ __ __| |\n"
                + "|  _ \\ / _ \\ | | '_ ` _ \\ / _` | '_ \\ _____| |_ / _ \\| '__/ _` |\n"
                + "| |_) |  __/ | | | | | | | (_| | | | |_____|  _| (_) | | | (_| |\n"
                + "|____/ \\___|_|_|_| |_| |_|\\__,_|_| |_|     |_|  \\___/|_|  \\__,_|\n"
                + "                                                                  \n"
                + "    _    _                  _ _   _                                \n"
                + "   / \\  | | __ _  ___  _ __(_) |_| |__  _ __ ___                  \n"
                + "  / _ \\ | |/ _` |/ _ \\| '__| | __| '_ \\| '_ ` _ \\                 \n"
                + " / ___ \\| | (_| | (_) | |  | | |_| | | | | | | | |                \n"
                + "/_/   \\_\\_|\\__, |\\___/|_|  |_|\\__|_| |_|_| |_| |_|                \n"
                + "           |___/                                                  ";

        System.out.println(bellmanFord + "\n");

        // Get edges
        System.out.println("Enter edges [{node} {destination} {weight}]:\n");

        while (true) {
            System.out.print("-> ");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals(""))
                break;

            String[] edge = input.trim().split(" ");

            edges.add(new Edge<String>(edge[0], edge[1], Integer.parseInt(edge[2])));
            nodes.add(edge[0]);
            nodes.add(edge[1]);
        }

        System.out.println();

        System.out.println("Given graph:");

        graph = createGraph(edges);
        System.out.println(graph);

        // Get starting node
        boolean validStartingNode = false;

        while (!validStartingNode) {
            System.out.print("Choose a starting node: ");
            startingNode = scanner.nextLine().trim().toUpperCase();

            if (startingNode == "")
                System.out.println("Cannot continue without a starting node!");
            else if (!nodes.contains(startingNode))
                System.out.println("Starting node (" + startingNode + ") not found!");
            else
                validStartingNode = true;
        }

        // Set shortest path
        shortestPaths.add(new Edge<String>(startingNode, startingNode, 0));
        nodes.remove(startingNode);
        for (String node : nodes)
            shortestPaths.add(new Edge<String>(startingNode, node, (int) Short.MAX_VALUE));

        weights = edgeToHashMap(shortestPaths);

        Integer relaxationsLeft = nodes.size();
        boolean relaxationFinished = false;
        boolean relaxAll = true;

        System.out.print("Do you want a pause in each relaxation? [y/N]: ");
        String input = scanner.nextLine().toUpperCase();

        if (input != "" && input.charAt(0) == 'Y')
            relaxAll = false;

        while (!relaxationFinished) {
            System.out.println("\nCurrent shortest path: ");

            System.out.println(graphPaths(startingNode, shortestPaths));

            System.out.println("Relaxations left: " + relaxationsLeft);

            if (!relaxAll) {
                System.out.print("Do you wish to continue the relaxation? [Y/n]: ");
                input = scanner.nextLine().toUpperCase();

                if (input == "")
                    input = "Y";

                if (input.charAt(0) == 'Y') {
                    shortestPaths = getRelaxedEdges(startingNode, edges, weights);
                } else
                    relaxationFinished = true;

            } else if (relaxAll)
                shortestPaths = getRelaxedEdges(startingNode, edges, weights);
 
            relaxationsLeft--;
            if (relaxationsLeft <= 0)
                relaxationFinished = true;
        }

        System.out.println("\nShortest path: ");
        System.out.println(graphPaths(startingNode, shortestPaths));

    }

    private static Graph<String> createGraph(ArrayList<Edge<String>> edges) {
        Graph<String> graph = new Graph<String>();

        for (Edge<String> edge : edges)
            graph.addEdge(edge.getNode(), edge.getDestination(), edge.getWeight());

        return graph;
    }

    private static Graph<String> graphPaths(String startingNode, ArrayList<Edge<String>> edges) {
        Graph<String> graph = createGraph(edges);

        graph.removeAllNodesExcpet(startingNode);

        return graph;
    }

    private static ArrayList<Edge<String>> getRelaxedEdges(String startingNode, ArrayList<Edge<String>> edges,
            HashMap<String, Integer> weights) {
        ArrayList<Edge<String>> relaxedEdges = new ArrayList<Edge<String>>();

        for (Edge<String> edge : edges) {
            if (weights.get(edge.getNode()) >= Short.MAX_VALUE)
                weights.replace(edge.getNode(), weights.get(edge.getNode()));
            else if (weights.get(edge.getNode()) + edge.getWeight() < weights.get(edge.getDestination()))
                weights.replace(edge.getDestination(), (weights.get(edge.getNode()) + edge.getWeight()));
            else
                weights.replace(edge.getDestination(), weights.get(edge.getDestination()));
        }

        for (HashMap.Entry<String, Integer> input : weights.entrySet()) {
            relaxedEdges.add(new Edge<String>(startingNode, input.getKey(), input.getValue()));
        }

        return relaxedEdges;
    }

    private static HashMap<String, Integer> edgeToHashMap(ArrayList<Edge<String>> edges) {
        HashMap<String, Integer> paths = new HashMap<String, Integer>();

        for (Edge<String> edge : edges)
            paths.putIfAbsent(edge.getDestination(), edge.getWeight());

        return paths;
    }
}