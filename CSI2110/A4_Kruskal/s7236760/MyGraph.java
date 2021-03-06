import java.util.*;

/**
 * 
 * Class MyGraph implements a graph using an (extended) adjacency matrix data structure
 * It implements the interface Graph<Integer,Double>: an Integer value is stored at each vertex and
 * corresponds to its index in the adjacency matrix; a Double value is stored at each edge and
 * corresponds to edge weight/distance.
 * 
 * For a description of the desired behavior of these methods consult the Graph interface.
 * 
 * @author Lucia Moura
 *
 */

public class MyGraph implements Graph<Integer,Double>{
	
	private int numVertices, numEdges; // number of vertices and number of edges
	private ArrayList<MyVertex> vertices; // list of vertices indexed by the vertex index
	private LinkedList<MyEdge> edges; // list of edges
    private MyEdge [][] adjacencyMatrix; // This will be an numVertices by numVertices adjacency matrix. 
    									 // if vertex index i and index j are connected by edge e
    									 // then adjacencyMatrix[i,j]=e
	
    /**
     *  MyVertex: nested class implementing the Vertex interface 
     *  
     */
	public class MyVertex implements Vertex<Integer>{
		private int index;   // index of vertex in adjacency matrix value in (0..numVertices-1)
		private String name; // label/name of vertex
		private int degree; // degree of vertex
		
		public MyVertex(int index, String name) {
			this.index=index; 
			this.name=name;
			this.degree=0;
		}

		public String getName() {
			return name;
		}
		@Override
		public Integer getElement() {
			return index;
		}
		@Override
		public String toString() {
			return "v"+index+":"+name;
		}
		
	}
	
	 /**
     *  MyEdge: nested class implementing the Edge interface 
     *  
     */
	public class MyEdge implements Comparable<MyEdge>, Edge<Double>  {
        private Double dist;
        private MyVertex v1, v2; 
        public MyEdge(double dist, MyVertex v1, MyVertex v2) {
        	this.dist=dist;
        	this.v1=v1; 
        	this.v2=v2; 
        }
		@Override
		public Double getElement() {
			return dist;
		}
		
		@Override
		public String toString() {
			return "e={"+v1+","+v2+"}"+" dist="+dist;
		}
		
		@Override
		public int compareTo(MyEdge o) {
			return (this.dist).compareTo(o.dist);
		}
		
	}
	
	/** 
	 * MyGraph() the constructor for the class
	 * the array names has length equals to the number n of vertices and specify the labels for vertices 0..n-1
	 * the arrays end1, end2, dist have length equals to the number m of edges
	 * Values end1[i] and end2[i] represent the indices (between 0..n-1) of the vertices that are endpoints of edge i
	 * dist[i] is the distance/weight of edge i
	*/
	
	public MyGraph(String [] names, int [] end1, int[] end2, double [] dist) {
		
		
		// part 1 create vertices 
		
		// *** start student to-do 1
		// complete here what is required for vertex creation

		numVertices=names.length; // sets number of vertices which is the length of array names
		vertices = new ArrayList<MyVertex>();

		for (int i = 0; i < numVertices; i++) {
			vertices.add(new MyVertex(i, names[i]));
		}
		
		// *** end student to-do 1
		
		// part 2: create edges
		numEdges=end1.length; // sets the number of edges which is the length of arrays end1,end2,dist
		if ((end2.length!=numEdges) || (dist.length!=numEdges)) {
			throw new IllegalArgumentException("Uneven array sizes for 2nd 3rd 4th arguments.");
		
		}
		// initialize adjacency matrix with null
		adjacencyMatrix=new MyEdge[numVertices][numVertices];
		edges=new LinkedList<MyEdge>();
		for (int i= 0; i< numVertices; i++)
			for (int j=0; j< numVertices; j++) {
				adjacencyMatrix[i][j]=null;
			}
		
		// *** start student to-do 2
		// part to be completed below: create edges placing in LinkedList edges as well as
		// adding the edge reference to the correct position in adjacency matrix
		
		for (int i = 0; i < numEdges; i++) {
			MyVertex v1 = vertices.get(end1[i]);
			MyVertex v2 = vertices.get(end2[i]);
			MyEdge myEdge = new MyEdge(dist[i], v1, v2);
			edges.add(myEdge);

			v1.degree++;
			v2.degree++;

			adjacencyMatrix[end1[i]][end2[i]] = myEdge;
			adjacencyMatrix[end2[i]][end1[i]] = myEdge;
		}

		// *** end student to-do 2
	}

	@Override
	public int numVertices() { // to be implemented by student in O(1)
		return numVertices; 
	}

	@Override
	public int numEdges() { // to be implemented by student in O(1)
		return numEdges; 
	}
	
	@Override
	public Iterable<Vertex<Integer>> vertices() { // to be implemented by student in O(n) where n is number of vertices
		
		ArrayList <Vertex<Integer>> newVertices = new ArrayList<Vertex<Integer>>();
			
		for (MyVertex v : vertices) {
			newVertices.add(v);
		}
		
		return newVertices;
	}

	@Override
	public Iterable<Edge<Double>> edges() { // to be implemented by student in O(m) where m is number of edges
		
		LinkedList<Edge<Double>> newEdges = new LinkedList<Edge<Double>>();
		
		for (MyEdge e : edges) {
			newEdges.add(e);
		}
		return newEdges;
	}

	@SuppressWarnings("unused")
	@Override
	public int degree(Vertex<Integer> v) throws IllegalArgumentException { // to be implemented by student in O(1)
		MyVertex  vertex = verifyVertex(v);	
		return vertex.degree;
	}
		
	@Override
	public Iterable<Edge<Double>> incidentEdges(Vertex<Integer> v) { // to be implemented by student in O(n), n number of vertices
	
		LinkedList<Edge<Double>> incidentEdges = new LinkedList<Edge<Double>>();

		for (MyEdge e : edges) {
			if (e.v1.equals(v) || e.v2.equals(v)) {
				incidentEdges.add(e);
			}
		}
		return incidentEdges;
	}

	@Override
	public Edge<Double> getEdge(Vertex<Integer> u, Vertex<Integer> v) throws IllegalArgumentException { // to be implemented by student in O(1)
		return adjacencyMatrix[u.getElement()][v.getElement()];
	}
	
	@Override
	public Vertex<Integer>[] endVertices(Edge<Double> e) throws IllegalArgumentException { // to be implemented by student in O(1)
		MyEdge edge = verifyEdge(e);
		Vertex<Integer>[] eV = new MyVertex[2];
		eV[0] = edge.v1;
		eV[1] = edge.v2;
		return eV;
	}
	
	@Override
	public Vertex<Integer> opposite(Vertex<Integer> v, Edge<Double> e) throws IllegalArgumentException { // to be implemented by student in O(1)
		
		// Get end vertices of the edge
		Vertex<Integer>[] eV = endVertices(e);
		
		if (v.equals(eV[0])) {
			return eV[1];
		} else if (v.equals(eV[1])) {
			return eV[0];
		} else {
			throw new IllegalArgumentException("There is no opposite vertex.");
		}
	}
	
	// Helper functions to check for validity and to safely downcast the argument
	private MyVertex verifyVertex(Vertex<Integer> v) {
		if ( ! (v instanceof MyVertex)) {
			throw new IllegalArgumentException("Invalid vertex");
		}
		MyVertex vertex = (MyVertex) v;
		return vertex;
	}
	
	private MyEdge verifyEdge(Edge<Double> e) {
		if ( ! (e instanceof MyEdge)) {
			throw new IllegalArgumentException("Invalid edge");
		}
		MyEdge edge = (MyEdge) e;
		return edge;
	}
}
