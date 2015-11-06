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

	public void biConnect(int root) {
		int i = root;
		do {
			if(nodes[i].color == Color.WHITE){
				depthFirstSearch(i);
			}
			i++;
			i = i%nodes.length;
		} while (i != root);
	}

	public Node[] getNodes() {
		return nodes;
	}
	
	private void depthFirstSearch(int nodeId){
		Node node = nodes[nodeId];
		node.color = Color.GREY;
	}
}
