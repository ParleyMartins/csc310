package third_assignment;

public class Graph2015 {
	private Node[] nodes;
	private int depthFirstIndex = 1;

	public Graph2015(int numberOfNodes) {
		nodes = new Node[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			Node node = new Node();
			nodes[i] = node;
		}
	}

	public void addEdge(int node1, int node2) {
		nodes[node1].addEdge(node2);
		nodes[node2].addEdge(node1);
	}

	public void setLabel(int nodeId, String label) {
		nodes[nodeId].setLabel(label);
	}

	public void biConnect(int root) {
		initialConditions();
		int i = root;
		do {
			if (nodes[i].color == Color.WHITE) {
				depthFirstSearch(i);
			}
			i++;
			i = i % nodes.length;
		} while (i != root);
	}

	private void initialConditions() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].color = Color.WHITE;
		}
		depthFirstIndex = 0;
	}

	public Node[] getNodes() {
		return nodes;
	}

	private void depthFirstSearch(int nodeId) {
		Node node = nodes[nodeId];
		node.color = Color.GREY;
		depthFirstIndex++;
		node.depthFirstIndex = depthFirstIndex;
		System.out.println("Depth First Index of " + node.getLabel() + " is " + node.depthFirstIndex);
		for (int nextNode : node.getEdges()) {
			if (nodes[nextNode].color == Color.WHITE) {
				depthFirstSearch(nextNode);
			}
		}
		node.color = Color.BLACK;
	}
}
