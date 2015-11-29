package third_assignment;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to represent the nodes of a graph. It stores the label, if this node is
 * an articulation point, the edges that come out of it and the order in which
 * it was visited in the depth first search
 * 
 * @author Parley Pacheco Martins 1484000 November 29th, 2015
 */
public class Node {
	private String label;
	private boolean articulationPoint;
	private List<Integer> edges;
	public Color color;
	public int time;
	public int parent;
	
	/**
	 * Constructs a node object without a given label
	 */
	public Node() {
		this(null);
	}

	/**
	 * Constructs a node object with a given label
	 * 
	 * @param label
	 *            Label associated to this node
	 */
	public Node(String label) {
		this.label = label;
		edges = new LinkedList<Integer>();
		color = Color.WHITE;
		time = Integer.MAX_VALUE;
	}

	/**
	 * Add an edge leaving this node
	 * 
	 * @param node
	 *            the destination node
	 */
	public void addEdge(int node) {
		edges.add(node);
	}

	/**
	 * Gets the associated label
	 * 
	 * @return The label of this node
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets a new label to the node
	 * 
	 * @param label
	 *            The new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Checks if the node is an articulation point
	 * 
	 * @return true if the node is an articulation point, false otherwise
	 */
	public boolean isArticulationPoint() {
		return articulationPoint;
	}

	/**
	 * Sets this node as an articulation point or not
	 * 
	 * @param ap
	 *            true or false if this is an articulation point
	 */
	public void setArticulationPoint(boolean ap) {
		articulationPoint = ap;
	}

	/**
	 * Gets the list of edges
	 * 
	 * @return An ArrayList with all the edges
	 */
	public List<Integer> getEdges() {
		return edges;
	}

	/**
	 * Check is this node is a root in the depth first search
	 * 
	 * @return true if the node is root, false otherwise
	 */
	public boolean isRoot() {
		return time == 1;
	}
	
	public void resetNode(){
		color = Color.WHITE;
		time = Integer.MAX_VALUE;
		setArticulationPoint(false);
		parent = -1;
	}
	
	/**
	 * Returns the content of the node as a string
	 */
	@Override
	public String toString() {
		return getLabel();
	}
}
