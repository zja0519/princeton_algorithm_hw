
public class SAP {
	// constructor takes a digraph (not necessarily a DAG)
	   private Digraph digraph;
	   
	   public SAP(Digraph G) {
		   if (G==null) throw new NullPointerException();
		   digraph = new Digraph(G.V());
		   for (int v = 0; v < G.V(); v++) {
	            // reverse so that adjacency list is in same order as original
	            Iterable<Integer> curNeighbor = G.adj(v);
	            Stack<Integer> reverse = new Stack<Integer>();
	            for (int w : curNeighbor) {
	                reverse.push(w);
	            }
	            for(int w : reverse) {
	            	digraph.addEdge(v, w);
	            }
	        }
	   }
	
	   // length of shortest ancestral path between v and w; -1 if no such path
	   public int length(int v, int w) {
		   if(v<0 || v>this.digraph.V()-1 || w<0 || w>this.digraph.V()-1) throw new IndexOutOfBoundsException();
		   BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(this.digraph,v);
		   BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(this.digraph,w);
		   int curMin = Integer.MAX_VALUE;
		   
		   for(int curV=0;curV<this.digraph.V();curV++){
			   if(bfs_v.hasPathTo(curV) && bfs_w.hasPathTo(curV)){
				   curMin = Math.min(curMin, bfs_v.distTo(curV)+bfs_w.distTo(curV));
			   }
		   }
		   
		   if(curMin == Integer.MAX_VALUE) curMin=-1;
		   return curMin;
	   }
	
	   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	   public int ancestor(int v, int w) {
		   if(v<0 || v>this.digraph.V()-1 || w<0 || w>this.digraph.V()-1) throw new IndexOutOfBoundsException();
		   BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(this.digraph,v);
		   BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(this.digraph,w);
		   int curMin = Integer.MAX_VALUE;
		   int curDistance = 0;
		   int ancestor = -1;
		   
		   for(int curV=0;curV<this.digraph.V();curV++){
			   if(bfs_v.hasPathTo(curV) && bfs_w.hasPathTo(curV)){
				   curDistance = bfs_v.distTo(curV)+bfs_w.distTo(curV);
				   if (curDistance<curMin) {
					   ancestor = curV;
					   curMin = curDistance;
				   }
			   }
		   }
		   
		   return ancestor;
	   }
	
	   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	   public int length(Iterable<Integer> v, Iterable<Integer> w) {
		   if (v==null || w==null) throw new NullPointerException();
		   BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(this.digraph,v);
		   BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(this.digraph,w);
		   int curMin = Integer.MAX_VALUE;
		   
		   for(int curV=0;curV<this.digraph.V();curV++){
			   if(bfs_v.hasPathTo(curV) && bfs_w.hasPathTo(curV)){
				   curMin = Math.min(curMin, bfs_v.distTo(curV)+bfs_w.distTo(curV));
			   }
		   }
		   
		   if(curMin == Integer.MAX_VALUE) curMin=-1;
		   return curMin;
	   }
	
	   // a common ancestor that participates in shortest ancestral path; -1 if no such path
	   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		   if (v==null || w==null) throw new NullPointerException();
		   BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(this.digraph,v);
		   BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(this.digraph,w);
		   int curMin = Integer.MAX_VALUE;
		   int curDistance = 0;
		   int ancestor = -1;
		   
		   for(int curV=0;curV<this.digraph.V();curV++){
			   if(bfs_v.hasPathTo(curV) && bfs_w.hasPathTo(curV)){
				   curDistance = bfs_v.distTo(curV)+bfs_w.distTo(curV);
				   if (curDistance<curMin) {
					   ancestor = curV;
					   curMin = curDistance;
				   }
			   }
		   }
		   
		   return ancestor;  
	   }
	
	   // do unit testing of this class
	   public static void main(String[] args) {
		   In in = new In(args[0]);
		    Digraph G = new Digraph(in);
		    SAP sap = new SAP(G);
		    while (!StdIn.isEmpty()) {
		        int v = StdIn.readInt();
		        int w = StdIn.readInt();
		        int length   = sap.length(v, w);
		        int ancestor = sap.ancestor(v, w);
		        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		    }
	   }
}
