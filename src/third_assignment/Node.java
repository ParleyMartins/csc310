package third_assignment;

import java.util.ArrayList;

public class Node {
	private String label;
	private boolean articulationPoint;
	private ArrayList<Integer> edges;

	public Node() {
		this(null);
	}
	
	public Node(String label) {
		this.label = label;
		edges = new ArrayList<>();		
	}
	
	public void addEdge(int node){
		edges.add(node);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean isArticulationPoint(){
		return articulationPoint;
	}
	
	public void setArticulationPoint(boolean ap){
		articulationPoint = ap;
	}
}
