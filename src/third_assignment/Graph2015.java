package third_assignment;

public class Graph2015 {
	Node[] nodes;

	public Graph2015(int numberOfNodes) {
		nodes = new Node[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			Node node = new Node();
			nodes[i] = node;
		}
	}

	public void addEdge(int node1, int node2) {

	}

	public void setLabel(int nodeId, String label) {

	}

	public void biConnect() {

	}

	public void biConnect(String labelRoot) {

	}

	public void biConnect(int nodeRoot) {

	}

	public Node[] getNodes() {
		return nodes;
	}
}
