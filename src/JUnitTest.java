import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

public class JUnitTest {

	static Graph g;

	@Before
	public void init() {
		g = new Graph();
	}

	@Test
	public void isCorrect() {
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

		g.addNode(nodeA);
		g.addNode(nodeB);
		g.addNode(nodeC);
		g.addNode(nodeD);
		g.addNode(nodeE);
		g.addNode(nodeF);

		g = Dijkstra.calculateShortestPathFromSource(g, nodeA);

		int distance = ((Node) g.getNodes().stream().filter(x -> x.getName().equals("B")).toArray()[0]).getDistance();
		assertEquals(distance, 10);
	}

	@Test(expected = NegativeDistanceException.class)
	public void nodeDistance() throws NegativeDistanceException {

		Node a = new Node("A");
		a.addDestination(new Node("B"), -1);

	}
}
