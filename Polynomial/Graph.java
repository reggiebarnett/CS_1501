/*************************************************************************
 *  Compilation:  javac Graph.java        
 *  Execution:    java Graph input.txt
 *  Dependencies: Bag.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 *
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java Graph tinyG.txt
 *  13 vertices, 13 edges 
 *  0: 6 2 1 5 
 *  1: 0 
 *  2: 0 
 *  3: 5 4 
 *  4: 5 6 3 
 *  5: 3 4 0 
 *  6: 0 4 
 *  7: 8 
 *  8: 7 
 *  9: 11 10 12 
 *  10: 9 
 *  11: 9 12 
 *  12: 11 9 
 *
 *  % java Graph mediumG.txt
 *  250 vertices, 1273 edges 
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15 
 *  1: 220 203 200 194 189 164 150 130 107 72 
 *  2: 141 110 108 86 79 51 42 18 14 
 *  ...
 *  
 *************************************************************************/


/**
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the vertices adjacent to a given vertex, which takes
 *  time proportional to the number of such vertices.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41undirected">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
 
import java.math.BigInteger;
 
public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    private Bag<Graph> graphs;
    private int currentCall = 0;
    
    /**
     * Initializes an empty graph with <tt>V</tt> vertices and 0 edges.
     * param V the number of vertices
     * @throws java.lang.IllegalArgumentException if <tt>V</tt> < 0
     */
    public Graph(int V) 
    {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) 
        {
            adj[v] = new Bag<Integer>();
        }
    }

    /**  
     * Initializes a graph from an input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     * @param in the input stream
     * @throws java.lang.IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
     * @throws java.lang.IllegalArgumentException if the number of vertices or edges is negative
     */
    public Graph(In in) 
    {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) 
        {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    /**
     * Initializes a new graph that is a deep copy of <tt>G</tt>.
     * @param G the graph to copy
     */
    public Graph(Graph G) 
    {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    /**
     * Returns the number of vertices in the graph.
     * @return the number of vertices in the graph
     */
    public int V() 
    {
        return V;
    }

    /**
     * Returns the number of edges in the graph.
     * @return the number of edges in the graph
     */
    public int E() 
    {
        return E;
    }

    /**
     * Adds the undirected edge v-w to the graph.
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= V) { System.out.println(this + "this.V(): " + this.V() + "\tv: " + v + "\tw: " + w); throw new IndexOutOfBoundsException(); }
        if (w < 0 || w >= V) { System.out.println(this + "this.V(): " + this.V() + "\tv: " + v + "\tw: " + w); throw new IndexOutOfBoundsException(); }
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }
    
    public void removeEdge(int v, int w) {
	if (v < 0 || v >= V) { System.out.println(this + "this.V(): " + this.V() + "\tv: " + v + "\tw: " + w); throw new IndexOutOfBoundsException(); }
        if (w < 0 || w >= V) { System.out.println(this + "this.V(): " + this.V() + "\tv: " + v + "\tw: " + w); throw new IndexOutOfBoundsException(); }
        E--;
        adj[v].remove(w);
        adj[w].remove(v);
    }
    
    /**
     * Checks if the graph contains undirected edge v-w.
     * @param v one vertex in the edge
     * @param w corresponding vertex in the edge
     * @return whether or not the graph contains edge v-w
     */
    public boolean hasEdge(int v, int w) {
        return adj[v].contains(w);
    }

    /**
     * Merge vertex w into v, removing w from the list
     * All references to and from w are added to v instead
     * @param v vertex to merge to
     * @param w vertex to be merged into v
     * @return a new graph of size V-1
     */
    public Graph merge(int v, int w){
        //System.out.println("Merge " + v + " and " + w);
        Graph g = new Graph(V - 1);
        for(int i = 0; i <= g.V(); i++)
        {
            if(i == w)
            {
                for(int copyValue : adj[i])
                {
                    if(copyValue > w) copyValue--;
                    if(!g.hasEdge(copyValue, v) && copyValue != v) 
                    {
                        g.addEdge(v, copyValue); 
                    }
                }
            }
            for(int value : adj[i])
            {
                if(value == w)
                {
                    if(i < w && !g.hasEdge(i, v) && i != v) g.addEdge(i, v);
                    else if(i > w && !g.hasEdge(i - 1, v) && i - 1 != v) g.addEdge(i - 1, v);
                }
                else if (value > w)
                {
                    if(i < w && !g.hasEdge(i, value - 1)) g.addEdge(i, value - 1);
                    else if(i > w && !g.hasEdge(i - 1, value - 1)) g.addEdge(i - 1, value - 1);
                }
                else
                {
                    if(i < w && !g.hasEdge(i, value)) g.addEdge(i, value);
                    else if(i > w && !g.hasEdge(i - 1, value)) g.addEdge(i - 1, value);
                }
            }
        }
        return g;
    }

    /**
     * Returns the vertices adjacent to vertex <tt>v</tt>.
     * @return the vertices adjacent to vertex <tt>v</tt> as an Iterable
     * @param v the vertex
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<Integer> adj(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj[v];
    }
    
    /**
     * Extract components of a disconnected graph into their own graph object
     * Assumed to be called only when the graph is disconnected
     * @return a bag of graphs
     */
    public Bag<Graph> getComponents(){
        Bag<Graph> components = new Bag<Graph>();
        int count = 0;
        while(count < this.V())
        {
            Search search = new Search(this, count);
            Graph component = new Graph(search.count());
            for(int i = count; i < count + search.count(); i++) {
                for(int value : adj[i]) {
                    if(!component.hasEdge(i - count, value - count)) {
                        component.addEdge(i - count, value - count);
                    }
                }
            }
            //System.out.println(component);
            count += search.count();
            components.add(component);
        }		
        return components;
    }
    
    /**
     * Determine whether or not the graph is connected using depth-first search
     * Returns true if connected, false if disconnected
     */
    public boolean isConnected(){
        Search search = new Search(this, 0);	   
        //System.out.println("Search count: " + search.count());
        if(search.count() != this.V())
            return false;
        return true;
    }
	
    /**
     * Return a chromatic polynomial of the graph
     * Use the form P(Kn, x) = x(x-1)(x-2) … (x-n+1). 
     */
    public Polynomial getPolynomial(){
        graphs = new Bag<Graph>();
        Polynomial poly = new Polynomial(BigInteger.valueOf(0), 0);
        boolean nullGraph = (E < (V * (V - 1)) / 4); //checks whether or not the graph will be complete or null
        if(nullGraph)
        {
            poly = NullGraphs(this);
        }
        else
        {
            CompleteGraphs(this);
        }
        if(!nullGraph)
        {
            for(Graph g : graphs)
            {
                Polynomial newPoly = new Polynomial(BigInteger.valueOf(0));
                for(int i = 1; i < g.V(); i++)
                {
                    newPoly = newPoly.times(new Polynomial(BigInteger.valueOf(i * -1)));
                }
                poly = poly.plus(newPoly);
            }
        }
        return poly;
    }
	
    public void CompleteGraphs(Graph currentGraph){
        Graph graph1 = new Graph(currentGraph);
        Graph graph2 = new Graph(currentGraph);
        for(int i = 0; i < currentGraph.V(); i++) {
            for(int j = 1; j < currentGraph.V(); j++) {
                if(i == j) continue;
                else // complete graphs
                {
                    if(currentGraph.E() == (currentGraph.V()*(currentGraph.V()-1))/2)
                    {
                        graphs.add(currentGraph);
                        return;
                    }
                    else if(!currentGraph.hasEdge(i, j))
                    {
                        graph1.addEdge(i, j);
                        graph2 = graph2.merge(i, j);
                        CompleteGraphs(graph1);
                        CompleteGraphs(graph2);
                        return;
                    }
                }
            }
        }
    }
	
    public Polynomial NullGraphs(Graph currentGraph){
        Graph graph1 = new Graph(currentGraph);
        Graph graph2 = new Graph(currentGraph);
        for(int i = 0; i < currentGraph.V(); i++) {
            for(int j = 1; j < currentGraph.V(); j++) {
                if(i == j) continue;
                else
                {
                    if(currentGraph.E() == 0)
                    {
                        return new Polynomial(BigInteger.valueOf(1), currentGraph.V());
                    }
                    else if(currentGraph.hasEdge(i, j))
                    {
                        graph1.removeEdge(i, j);
                        graph2 = graph2.merge(i, j);
                        return NullGraphs(graph1).minus(NullGraphs(graph2));
                    }
                }
            }
        }
        return new Polynomial(BigInteger.valueOf(0));
    }
    
    /**
     * Returns a string representation of the graph.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *    followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Unit tests the <tt>Graph</tt> data type.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        StdOut.println("\nGraph: "+args[0]+"\n");
        Graph G = new Graph(in);
        Polynomial poly = new Polynomial();
        StdOut.println(G);
        if(G.isConnected())
        {
            StdOut.println(args[0]+" is connected");
            poly = G.getPolynomial();
        }
        else
        {
            StdOut.println(args[0]+" is not connected");
            Bag<Graph> components = G.getComponents();
            StdOut.println(args[0]+" has " + components.size() + " components.");

            for(Graph g : components)
            {
                poly = poly.times(g.getPolynomial());
            }
        }
        StdOut.println("P(x) = "+poly);
    }
}
