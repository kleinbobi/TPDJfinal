import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class Dijkstra {

	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
		try {
			source.setDistance(0);
		} catch (NegativeDistanceException e) {
			
		}

		Set<Node> settledNodes = new HashSet<>();
		Set<Node> unsettledNodes = new HashSet<>();

		unsettledNodes.add(source);

		while (unsettledNodes.size() != 0) {
			Node currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for (Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
				Node adjacentNode = adjacencyPair.getKey();
				Integer edgeWeight = adjacencyPair.getValue();
				if (!settledNodes.contains(adjacentNode)) {
					calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
		}
		return graph;
	}

	private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
		Integer sourceDistance = sourceNode.getDistance();
		if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			try {
				evaluationNode.setDistance(sourceDistance + edgeWeigh);
			} catch (NegativeDistanceException e) {
			}
			LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}

	private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
		Node lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (Node node : unsettledNodes) {
			int nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		return lowestDistanceNode;

	}

	public static void main(String[] args) {
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");
		Node nodeF = new Node("F");

		// Werte und Nodes
		try {
			nodeA.addDestination(nodeB, 10);
			nodeA.addDestination(nodeC, 15);

			nodeB.addDestination(nodeD, 12);
			nodeB.addDestination(nodeF, 15);

			nodeC.addDestination(nodeE, 10);

			nodeD.addDestination(nodeE, 2);
			nodeD.addDestination(nodeF, 1);

			nodeF.addDestination(nodeE, 5);
		} catch (NegativeDistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Graph graph = new Graph();

		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);

		graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);

		graph.getNodes().stream().sorted((s1, s2) -> s1.getName().compareTo(s2.getName()))
				.forEach(x -> System.out.println("Node" + x.getName() + " hat den folgenden Wert: " + x.getDistance()));
	}
}
