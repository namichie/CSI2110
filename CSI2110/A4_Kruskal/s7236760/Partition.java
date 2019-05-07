/**
 * 
 * Disjoint Partition of a set providing an union-find data structure
 * where clusters are implemented as linked lists of elements of type T 
 * Each cluster is represented by a Dnode of a doubly linked list of clusters
 * Each cluster/Dnode, points to a singly linked list of Node each containing an element in the cluster.
 * For efficient implementation of method find, each Node points to the Dnode of the cluster it belongs to.
 * 
 * @author Lucia Moura
 *
 * @param <T>
 */
public class Partition <T> {
	
	// inner class specifying a node in the singly linked lists
	public class Node {
		private Node next;
		private T element;
		private Dnode cluster=null;
		public Node (T element, Node next, Dnode cluster) {
			this.element=element;
			this.next=next;
			this.cluster=cluster;
		}
	}
	
	// inner class specifying a node in the doubly linked list of clusters
	public class Dnode {
		private Node first;
		private Dnode next, prev;
		private int size;
		
		Dnode(Node first, Dnode prev, Dnode next) {
			this.first=first;
			this.prev=prev;
			this.next=next;
			this.size=0;
		}
	}
		
	private Dnode headCluster, tailCluster; // references to the dummy head and tail of the doubly linked list
	private int countClusters; // size of doubly linked list (not counting the dummies)
	
	public Partition() {	
		// creates an empty doubly linked list of clusters with dummy head and tail
		headCluster=new Dnode(null,null,null);
		tailCluster=new Dnode(null,headCluster,null);
		headCluster.next=tailCluster;
		countClusters=0;
	}
	
	public int numClusters() {
		return countClusters;
	}
	/**
	 * makeCluster creates a new cluster formed by the given element and inserts at the end of the list of clusters
	 * @param element
	 */
	public Node makeCluster(T element) {  // nothing needs to be changed here
		Node newNode=new Node(element,null,null);
		Dnode newCluster=new Dnode(newNode,tailCluster.prev,tailCluster);
		tailCluster.prev.next=newCluster;
		tailCluster.prev=newCluster;
		newCluster.first.cluster=newCluster;
		newCluster.size++;
		countClusters++;
		return newNode;
	}

	/****** for students to implement ***
	 * find returns the Dnode corresponding to the cluster where the node belongs to
	 * 
	 */
	public Dnode find(Node node) { 
		return node.cluster;
	}
	

	/******** for students to implement ****
	 *  union merges the cluster where node p belongs to with the one node q belongs to.
	 *  This method must run in O(min(n_q,n_p)) time, where n_p is the size of the cluster of node p
	 *  and n_q is the size of the cluster of node q.
	 *  Note: You must do appropriate updates on the linked list and double linked list and its corresponding
	 *  nodes and sizes to correctly reflect the fact that the clusters have been merged.
	 *  */
	public void union (Node p, Node q) {

		Dnode c1 = find(p);
		Dnode c2 = find(q);
		
		// Points to the 1st elem in the cluster
		Node r = c1.first; 
		Node s = c2.first;
		
		if (c1.size > c2.size) {
			
			// Merge sizes
			c1.size += c2.size;
			
			// Loop through elems of 1st cluster
			while (r.next != null) {
				r = r.next;
			}
			r.next = s; // last elem in cluster points to 1st elem in 2nd cluster
			
			// Reset cluster links on all elements in 2nd cluster to 1st cluster
			do {
				s.cluster = c1;
				s = s.next;
			} while (s != null);
			
			// Remove the 2nd cluster
			c2.prev.next = c2.next;
			c2.next.prev = c2.prev;
			c2.prev = null;
			c2.first = null;
			c2.next = null;
			
		} else {
			c2.size += c1.size;
			
			// Loop through elems of 1st cluster
			while (s.next != null) {
				s = s.next;
			}
			s.next = r; // last elem in cluster points to 1st elem in 2nd cluster

			// Reset cluster links on all elements in 2nd cluster to 1st cluster
			do {
				r.cluster = c2;
				r = r.next;
			} while (r != null);
			
			// Remove the 2nd cluster
			c1.prev.next = c1.next;
			c1.next.prev = c1.prev;
			c1.prev = null;
			c1.first = null;
			c1.next = null;
		}
	}
	
	@Override
	public String toString() {
		// prints all clusters information and elements (nothing to be changed here)
		StringBuilder s = new StringBuilder(); int num=0;
		for (Dnode d=headCluster.next; d!=tailCluster; d=d.next) {
			s.append("Cluster ");
		    s.append(++num); s.append(" (size="); s.append(d.size); s.append("): ");
			for (Node n=d.first; n!=null; n=n.next) {
				s.append(n.element.toString());
				s.append(',');
			}
			s.append("\n");
		}
		return s.toString();
	}
	
}
