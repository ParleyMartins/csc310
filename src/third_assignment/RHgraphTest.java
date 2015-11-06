/****************************************************************************
 * RHgraphTest.java
 * AUCSC 310
 * November 4, 2015
 * Created by R. Heise, exclusively for learning purposes.
 * 
 * Sets up 3 graphs from McConnell, pages 258 to 261 (AUCSC 310 
 * textbook), and finds all articulation points and biconnected 
 * components of these graphs.  These point and components are found by
 * starting at variety of places in each graph.
 * 
 * Methods available:
 * main() - controls the running of everything:  Graph1 -> Graph2 -> Graph3
 * 
 * doGraph1() - sets up first graph, and prints articulation points and 
 *              biconnected components from various starting points
 * 
 * doGraph2() - sets up 2nd graph, and prints articulation points and 
 *              biconnected components from various starting points
 * 
 * doGraph3() - sets up 3rd graph, and prints articulation points and 
 *              biconnected components from various starting points
 * 
 */
package third_assignment;


public class RHgraphTest {

    /**
     * main()
     * 
     * @param args the command line arguments, but NONE are used
     */
    public static void main(String[] args){

	System.out.println("Starting graph processing tests for biconnected "
                + "components");

        //**********First graph
        doGraph1();

	//**********Second graph
	System.out.println();
	System.out.println();
        doGraph2();
        
	//***********Third graph
	System.out.println();
	System.out.println();       
        doGraph3();
                
    }//main
    
    public static void doGraph1(){
        Graph2015 myGraph = new Graph2015(9); 
        System.out.print("=============================================");
        System.out.println("===========================================");
        System.out.print("=============================================");
        System.out.println("===========================================");
	System.out.println("Graph 1, example on pg 258 of McConnell");

	myGraph.addEdge(0, 1);
	myGraph.addEdge(0, 2);
	myGraph.addEdge(1, 2);
	myGraph.addEdge(1, 3);
	myGraph.addEdge(2, 3);
	myGraph.addEdge(3, 4);
	myGraph.addEdge(3, 5);
	myGraph.addEdge(4, 5);
	myGraph.addEdge(4, 6);
	myGraph.addEdge(5, 7);
	myGraph.addEdge(6, 7);
	myGraph.addEdge(7, 8);

	myGraph.setLabel(0, "A");
	myGraph.setLabel(1, "B");
	myGraph.setLabel(2, "C");
	myGraph.setLabel(3, "D");
	myGraph.setLabel(4, "E");
	myGraph.setLabel(5, "F");
	myGraph.setLabel(6, "G");
	myGraph.setLabel(7, "H");
	myGraph.setLabel(8, "I");

        System.out.println("================================================");
        System.out.println("=====Biconnected components starting at F");
	myGraph.biConnect(5);  //find APs starting @ F
        
        System.out.println();
	System.out.println("================================================");
        System.out.println("=====Biconnected components starting at A");
	myGraph.biConnect(0);  //find APs starting @ A
        
	System.out.println();
	System.out.println("================================================");
	System.out.println("=====Biconnected components starting at D");
        myGraph.biConnect(3);  //start at D, an AP (root is an AP)
    }//doGraph1
    
    public static void doGraph2(){
        Graph2015 myGraph2 = new Graph2015(11);
        System.out.print("=============================================");
        System.out.println("===========================================");
        System.out.print("=============================================");
        System.out.println("===========================================");
	System.out.println("Graph 2, 1a on pg 261 of McConnell");

	myGraph2.addEdge(0, 1);
	myGraph2.addEdge(0, 4);
	myGraph2.addEdge(0, 7);

	myGraph2.addEdge(1, 4);
	myGraph2.addEdge(1, 5);

	myGraph2.addEdge(2, 5);
	myGraph2.addEdge(2, 9);

	myGraph2.addEdge(3, 6);
	myGraph2.addEdge(3, 10);

	myGraph2.addEdge(4, 8);

	myGraph2.addEdge(5, 8);
	myGraph2.addEdge(5, 9);

	myGraph2.addEdge(6, 9);
	myGraph2.addEdge(6, 10);

	myGraph2.addEdge(9, 10);

	myGraph2.setLabel(0, "A");
	myGraph2.setLabel(1, "B");
	myGraph2.setLabel(2, "C");
	myGraph2.setLabel(3, "D");
	myGraph2.setLabel(4, "E");
	myGraph2.setLabel(5, "F");
	myGraph2.setLabel(6, "G");
	myGraph2.setLabel(7, "H");
	myGraph2.setLabel(8, "I");
	myGraph2.setLabel(9, "J");
	myGraph2.setLabel(10, "K");

        System.out.println("================================================");
        System.out.println("=====Biconnected components starting at A");
	myGraph2.biConnect(0);  //find APs starting at A
        
        System.out.println();
	System.out.println("================================================");
	System.out.println("=====Biconnected components starting at F");
	myGraph2.biConnect(5);  //find APs starting at F
        
        System.out.println();
	System.out.println("================================================");
	System.out.println("=====Biconnected components starting at H");
	myGraph2.biConnect(7);  //find APs starting at H       
    }//doGraph2
    
    public static void doGraph3(){
        Graph2015 myGraph3 = new Graph2015(11);
        System.out.print("=============================================");
        System.out.println("===========================================");
        System.out.print("=============================================");
        System.out.println("===========================================");
	System.out.println("Graph 3, 1b on pg 261 of McConnell");

	myGraph3.addEdge(0, 1);

	myGraph3.addEdge(1, 5);

	myGraph3.addEdge(2, 3);
	myGraph3.addEdge(2, 6);
	myGraph3.addEdge(2, 7);

	myGraph3.addEdge(3, 7);

	myGraph3.addEdge(4, 5);
	myGraph3.addEdge(4, 8);
	myGraph3.addEdge(4, 9);

	myGraph3.addEdge(5, 6);
	myGraph3.addEdge(5, 8);
	myGraph3.addEdge(5, 9);
	myGraph3.addEdge(5, 10);

	myGraph3.addEdge(6, 10);

	myGraph3.addEdge(8, 9);

	myGraph3.setLabel(0, "A");
	myGraph3.setLabel(1, "B");
	myGraph3.setLabel(2, "C");
	myGraph3.setLabel(3, "D");
	myGraph3.setLabel(4, "E");
	myGraph3.setLabel(5, "F");
	myGraph3.setLabel(6, "G");
	myGraph3.setLabel(7, "H");
	myGraph3.setLabel(8, "I");
	myGraph3.setLabel(9, "J");
	myGraph3.setLabel(10, "K");

        System.out.println("================================================");
        System.out.println("=====Biconnected components starting at A");
	myGraph3.biConnect(0);  //find APs, starting at A
        
	System.out.println();
	System.out.println("================================================");
        System.out.println("=====Biconnected components starting at G");
	myGraph3.biConnect(6);  //find APs starting at G
        
	System.out.println();
        System.out.println("================================================");
        System.out.println("=====Biconnected components starting at F");
	myGraph3.biConnect(5);  //find APs starting at F
    }
}//RHgraphTest class
