import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

	private String name;

	protected List<Node> shortestPath = new LinkedList<>();

	private Integer distance = Integer.MAX_VALUE;

	private Map<Node, Integer> adjacentNodes = new HashMap<>();

	public void addDestination(Node destination, int distance) throws NegativeDistanceException {
		if (distance >= 0 ) {
			adjacentNodes.put(destination, distance);

			if (!destination.getAdjacentNodes().containsKey(this)) {
				destination.addDestination(this, distance);
			}
		} else {
			throw new NegativeDistanceException("Distance not allowed");
		}
	}

	public Node(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) throws NegativeDistanceException {
		if (distance >= 0)
			this.distance = distance;
		else {
			throw new NegativeDistanceException("Distance not allowed");
		}
	}

	public List<Node> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}


	public Map<Node, Integer> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<Node, Integer> lol) {
		adjacentNodes = lol;
	}

	// getters and setters


}
