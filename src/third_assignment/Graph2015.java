package third_assignment;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;

public class Graph2015 {
	private Node[] nodes;
	private int depthFirstIndex = 0;
	private PrintStream out = System.out;
	private Deque<Integer> biconnectedComponents;

	public Graph2015(int numberOfNodes) {
		biconnectedComponents = new ArrayDeque<>();
		nodes = new Node[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			Node node = new Node();
			nodes[i] = node;
		}
		// String FOLDER = "C:\\src\\csc310\\src";
		// String OUTPUT_FILE = FOLDER + "\\third_assignment\\output.txt";
		// try {
		// out = new PrintStream(OUTPUT_FILE, "UTF-8");
		// } catch (FileNotFoundException | UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
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
//		 while (biconnectedComponents.peekLast() != null) {
//		 int a = biconnectedComponents.pollLast();
//		 out.println(nodes[a]);
//		 }
	}

	private void initialConditions() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].color = Color.WHITE;
			nodes[i].time = Integer.MAX_VALUE;
			nodes[i].setArticulationPoint(false);
		}
		biconnectedComponents.clear();
		depthFirstIndex = 0;
	}

	public Node[] getNodes() {
		return nodes;
	}

	private int depthFirstSearch(int nodeId) {
		nodes[nodeId].color = Color.GREY;
		setIndex(nodeId);
		printIndex(nodeId);
		int notWhiteNodes = 0;
		int backlink = nodeId;

//		if(biconnectedComponents.peekLast() != nodeId)
			biconnectedComponents.add(nodeId);
		for (int nextNode : nodes[nodeId].getEdges()) {
			if (nodes[nextNode].time < nodes[backlink].time)
				backlink = nextNode;
			if (nodes[nextNode].color == Color.WHITE) {
				int backreturn = depthFirstSearch(nextNode);
				biconnectedComponents.add(nodeId);
				checkAP(nodeId, backreturn);
				if (nodes[backreturn].time < nodes[backlink].time) {
					backlink = backreturn;
				}
			} else {
				notWhiteNodes++;
			}
		}

		printBacklink(nodeId, backlink);
		printLeaf(notWhiteNodes, nodeId);
		nodes[nodeId].color = Color.BLACK;
		return backlink;
	}

	private void printBacklink(int nodeId, int backlink) {
		int timeNode = nodes[nodeId].time;
		int timeBack = nodes[backlink].time;
		if (nodeId != backlink && timeNode != timeBack + 1) {
			String labelNode = nodes[nodeId].getLabel();
			String labelBack = nodes[backlink].getLabel();
			out.print("  Set backlink of " + labelNode);
			out.println(" to node " + backlink + " (" + labelBack + ")");
		}
	}

	private void setIndex(int nodeId) {
		depthFirstIndex++;
		nodes[nodeId].time = depthFirstIndex;
	}

	private void printIndex(int nodeId) {
		out.print("Depth First Index of " + nodes[nodeId].getLabel());
		out.println(" is " + depthFirstIndex);
	}

	private void checkAP(int nodeId, int backlink) {
		Node node = nodes[nodeId];
		if (nodeId == backlink) {// Is this node the backlink?
			if (!node.isArticulationPoint()) { // Have I done this before?
				if (node.isRoot()) { // Special case
					boolean hasWhiteNodes = checkWhiteNodes(nodeId);
					if (hasWhiteNodes) {
						node.setArticulationPoint(true);
						printArticulationPoint(node.getLabel(), node.isRoot());
					}
				} else {
					node.setArticulationPoint(true);
					printArticulationPoint(node.getLabel(), node.isRoot());
				}
			}
			printBiconnectedComponents(nodeId);
		}
	}

	private boolean checkWhiteNodes(int nodeId) {
		for (int edge : nodes[nodeId].getEdges()) {
			if (nodes[edge].color == Color.WHITE) {
				return true;
			}
		}
		return false;
	}

	private void printLeaf(int notWhiteNodes, int nodeId) {
		if (notWhiteNodes == nodes[nodeId].getEdges().size()) {
			out.println("  Found a leaf: " + nodes[nodeId].getLabel());
		}
	}

	private void printArticulationPoint(String label, boolean isRoot) {
		out.println();
		out.println("APAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAP");
		out.print("AP  Found Articulation Point");
		if (isRoot)
			out.print(" @ ROOT");
		out.println(": " + label);
		out.println("APAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAP");
		out.println();
	}

	private void printBiconnectedComponents(int nodeId) {
		out.println();
		out.println("BCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBC");
		out.print("BC  Found bi component: ");
		Integer index = biconnectedComponents.pollLast();
		do {
			out.print(nodes[index].getLabel() + " ");
			index = biconnectedComponents.pollLast();
		} while (index != null &&index != nodeId);
		biconnectedComponents.addLast(index);
		out.println();
		out.println("BCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBC");
		out.println();
	}
}
