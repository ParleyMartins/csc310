package third_assignment;

import java.io.PrintStream;
import java.util.ArrayDeque;

public class Graph2015 {
	private Node[] nodes;
	private int depthFirstIndex = 1;
	private PrintStream out = System.out;
	private ArrayDeque<Integer> biconnectedComponents;

	public Graph2015(int numberOfNodes) {
		biconnectedComponents = new ArrayDeque<>();
		nodes = new Node[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			Node node = new Node();
			nodes[i] = node;
		}

//		String FOLDER = "C:\\src\\csc310\\src";
//		String OUTPUT_FILE = FOLDER + "\\third_assignment\\output.txt";
//		File file = new File(OUTPUT_FILE);
//		try {
//			out = new PrintStream(file);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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

	private int depthFirstSearch(int nodeId) {
		Node node = nodes[nodeId];
		node.color = Color.GREY;
		depthFirstIndex++;
		node.depthFirstIndex = depthFirstIndex;
		String printing = "Depth First Index of " + node.getLabel() + " is ";
		printing += node.depthFirstIndex;
		out.println(printing);
		int notWhiteNodes = 0;
		int backlink = nodeId;
		biconnectedComponents.add(nodeId);

		for (int nextNode : node.getEdges()) {
			if (nodes[nextNode].depthFirstIndex < nodes[backlink].depthFirstIndex)
				backlink = nextNode;

			if (nodes[nextNode].color == Color.WHITE) {
				int backreturn = depthFirstSearch(nextNode);
				if (nodes[backreturn].depthFirstIndex < nodes[backlink].depthFirstIndex) {
					backlink = backreturn;
				}
				if (nodeId == backlink) {
					printing = "=====" + "Articulation Point " + node.getLabel();
					out.println(printing);
					out.println("****");
					do {
						out.print(nodes[biconnectedComponents.removeLast()].getLabel()+ " ");
					} while (biconnectedComponents.peekLast() != nodeId);
					out.println(nodes[biconnectedComponents.removeLast()].getLabel());
					out.println("****");
					biconnectedComponents.add(nodeId);
				}
			} else {
				notWhiteNodes++;
			}

		}
		// out.println("Backlink of " + node.getLabel() + " is " +
		// nodes[backlink].getLabel());

		if (node.getEdges().size() == notWhiteNodes) {
			printing = "\tFound a leaf: " + node.getLabel();
			out.println(printing);
		}

		node.color = Color.BLACK;
		return backlink;
	}
}
