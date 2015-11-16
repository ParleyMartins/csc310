package third_assignment;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
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
		String FOLDER = "C:\\src\\csc310\\src";
		String OUTPUT_FILE = FOLDER + "\\third_assignment\\output.txt";
		try {
			out = new PrintStream(OUTPUT_FILE, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
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
			nodes[i].time = Integer.MAX_VALUE;
			nodes[i].setArticulationPoint(false);
		}
		depthFirstIndex = 0;
	}

	public Node[] getNodes() {
		return nodes;
	}

	private int depthFirstSearch(int nodeId) {
		nodes[nodeId].color = Color.GREY;
		setIndex(nodeId);
		printIndex(nodeId);
		int whiteNodes = 0;
		int backlink = nodeId;
		biconnectedComponents.add(nodeId);

		for (int nextNode : nodes[nodeId].getEdges()) {
			if (nodes[nextNode].time < nodes[backlink].time)
				backlink = nextNode;
			if (nodes[nextNode].color == Color.WHITE) {
				whiteNodes++;
				int backreturn = depthFirstSearch(nextNode);
				checkAP(nodeId, backreturn, whiteNodes);
				if (nodes[backreturn].time < nodes[backlink].time) {
					backlink = backreturn;
				}
			}
		}
		printBacklink(nodeId, backlink);
		printLeaf(whiteNodes, nodeId);
		nodes[nodeId].color = Color.BLACK;
		return backlink;
	}

	private void printBacklink(int nodeId, int backlink) {
		if (nodeId != backlink) {
			String labelNode = nodes[nodeId].getLabel();
			String labelBack = nodes[backlink].getLabel();
			out.print("  Set backlink of " + labelNode);
			out.println(" to " + backlink + " (" + labelBack + ")");
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

	private void checkAP(int nodeId, int backlink, int whitenodes) {
		Node node = nodes[nodeId];
		if (nodeId == backlink && node.getEdges().size() != whitenodes) {
			if (!node.isArticulationPoint()) {
				node.setArticulationPoint(true);
				printArticulationPoint(node.getLabel(), node.isRoot());
			}
			printBiconnectedComponents(nodeId);
		}
	}

	private void printLeaf(int whiteNodes, int nodeId) {
		if (whiteNodes == 0) {
			out.println("  Found a leaf: " + nodes[nodeId].getLabel());
		}
	}

	private void printArticulationPoint(String label, boolean isRoot) {
		out.println();
		out.println("APAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAP");
		out.print("AP  Found Articulation Point ");
		if (isRoot)
			out.print("@ ROOT: ");
		out.println(label);
		out.println("APAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAPAP");
		out.println();
	}

	private void printBiconnectedComponents(int nodeId) {
		out.println();
		out.println("BCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBC");
		out.print("BC  Found bi component: ");
		do {
			int index = biconnectedComponents.removeLast();
			out.print(nodes[index].getLabel() + " ");
		} while (biconnectedComponents.peekLast() != nodeId);
		out.println(nodes[biconnectedComponents.removeLast()].getLabel());
		out.println("BCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBC");
		out.println();
		biconnectedComponents.add(nodeId);
	}
}
