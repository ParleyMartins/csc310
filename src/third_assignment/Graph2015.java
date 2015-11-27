package third_assignment;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This class implements a graph and finds all the biconnected components and
 * articulations points on it. Even though it's used the depth first sweep in to
 * find these elements, it's assumed that the graph will be connected and
 * undirected. It's initialized with the amount of nodes in the graph.
 * 
 * Public methods to add egdes, set labels, get all the nodes and find the
 * biconnected components and articulation points are provided.
 * 
 * @author Parley Pacheco Martins 1484000 November 29th, 2015
 */
public class Graph2015 {
	private Node[] nodes;
	private int depthFirstIndex = 0;
	private PrintStream out = System.out;
	private Deque<Integer> biconnectedComponents;

	/**
	 * Initializes the Graph with the specified number of nodes
	 * 
	 * @param numberOfNodes
	 *            The amount of nodes in the graph.
	 */
	public Graph2015(int numberOfNodes) {
		biconnectedComponents = new ArrayDeque<>();
		nodes = new Node[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			Node node = new Node();
			nodes[i] = node;
		}
	}

	/**
	 * Add an undirected edge to the graph. Technically, add an edge from node1
	 * to node2 and an edge from node2 to node1.
	 * 
	 * @param node1
	 *            The id of the first node in this edge.
	 * @param node2
	 *            The id of the second node in this edge.
	 */
	public void addEdge(int node1, int node2) {
		nodes[node1].addEdge(node2);
		nodes[node2].addEdge(node1);
	}

	/**
	 * Associates a node to a label
	 * 
	 * @param nodeId
	 *            The node that is going to receive a label.
	 * @param label
	 *            The new label of the node.
	 */
	public void setLabel(int nodeId, String label) {
		nodes[nodeId].setLabel(label);
	}

	/**
	 * Finds the biconnected components in the graph beginning the search in the
	 * given node.
	 * 
	 * @param root
	 *            The node where the depth first search must begin.
	 */
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

	/**
	 * Sets the initial conditions to find biconnected components
	 */
	private void initialConditions() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].resetNode();
		}
		biconnectedComponents.clear();
		depthFirstIndex = 0;
	}

	/**
	 * Gets the nodes of this graph
	 * 
	 * @return the array with the graph nodes.
	 */
	public Node[] getNodes() {
		return nodes;
	}

	/**
	 * Implements the depth first search on this graph. It's a recursive
	 * algorithm.
	 * 
	 * @param nodeId
	 *            The id of the node where the search must be realized.
	 * @return The backlink (back edge) of this node. When there's no backlink
	 *         (root), it will return the nodeId as backlink.
	 */
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
				nodes[nextNode].parent = nodeId;
				int backreturn = depthFirstSearch(nextNode);
				checkAP(nodeId, backreturn);
				if (nodes[backreturn].time < nodes[backlink].time) {
					backlink = backreturn;
				}
			}
		}

		printBacklink(nodeId, backlink);
		checkAndPrintLeaf(whiteNodes, nodeId);
		nodes[nodeId].color = Color.BLACK;
		return backlink;
	}

	/**
	 * Prints the backlink of the node
	 * 
	 * @param nodeId
	 *            Node that had the search performed on
	 * @param backlink
	 *            The backlink value.
	 */
	private void printBacklink(int nodeId, int backlink) {
		/*
		 * This comparison is made for printing purposes only
		 */
		if (backlink != nodeId && nodes[nodeId].parent != backlink) {
			String labelNode = nodes[nodeId].getLabel();
			String labelBack = nodes[backlink].getLabel();
			out.print("  Set backlink of " + labelNode);
			out.println(" to " + nodes[backlink].time + " (" + labelBack + ")");
		}
	}

	/**
	 * Sets the current depth first index in the given node. It happens when the
	 * depth first search is first called on this node.
	 * 
	 * @param nodeId
	 *            The node that is being performed the search on.
	 */
	private void setIndex(int nodeId) {
		depthFirstIndex++;
		nodes[nodeId].time = depthFirstIndex;
	}

	/**
	 * Prints the depth first index of the node
	 * 
	 * @param nodeId
	 *            node in the graph
	 */
	private void printIndex(int nodeId) {
		out.print("Depth First Index of " + nodes[nodeId].getLabel());
		out.println(" is " + depthFirstIndex);
	}

	/**
	 * This checks if the current node is an articulation point
	 * 
	 * @param nodeId
	 *            The current node in the depth first search
	 * @param backlink
	 *            The backlink of one of children of the node
	 */
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

	/**
	 * Checks if a node (the root) still has white (unvisited) children
	 * 
	 * @param nodeId
	 *            The node id that is going to be checked
	 * @return True if the given node has white children, false otherwise.
	 */
	private boolean checkWhiteNodes(int nodeId) {
		for (int edge : nodes[nodeId].getEdges()) {
			if (nodes[edge].color == Color.WHITE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a given node is a leaf and prints the leaf.
	 * 
	 * @param whiteNodesAmount
	 *            The amount of white children of the given node
	 * @param nodeId
	 *            The node id to be checked.
	 */
	private void checkAndPrintLeaf(int whiteNodesAmount, int nodeId) {
		// If the node has no white nodes, it's a leaf
		if (whiteNodesAmount == 0) {
			out.println("  Found a leaf: " + nodes[nodeId].getLabel());
		}
	}

	/**
	 * Prints articulation point (AP) in the specified format. If AP is the
	 * root, prints that information too.
	 * 
	 * @param label
	 *            The label of the node that is an articulation point
	 * @param isRoot
	 *            a boolean informing if the AP is the root of the search.
	 */
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

	/**
	 * Prints the bicconected component up to the given articulation point.
	 * 
	 * @param nodeId
	 *            The node that is an Articulation Point
	 */
	private void printBiconnectedComponents(int nodeId) {
		out.println();
		out.println("BCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBC");
		out.print("BC  Found bi component: ");
		Integer index = biconnectedComponents.pollLast();
		do {
			out.print(nodes[index].getLabel() + " ");
			index = biconnectedComponents.pollLast();
		} while (index != null && index != nodeId);
		/*
		 * Re add last index because it's removed in the while loop. I chose to
		 * do that because it's less complexity than peeking and polling every
		 * time.
		 */
		out.print(nodes[index].getLabel() + " ");
		biconnectedComponents.addLast(index);
		out.println();
		out.println("BCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBCBC");
		out.println();
	}
}
