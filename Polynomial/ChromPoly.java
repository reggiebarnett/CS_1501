
public class ChromPoly {

    public static void main(String[] args){
        for(int i = 0;i<args.length;i++){ //for loop to handle multiple arguments
            In in = new In(args[i]);
            StdOut.println("\nGraph: "+args[i]+"\n");
            Graph G = new Graph(in);
            Polynomial poly = new Polynomial();
            StdOut.println(G);
            if(G.isConnected()) //connected graphs
            {
                StdOut.println(args[i]+" is connected");
                poly = G.getPolynomial(); //getting the polynomial
            }
            else //not connected
            {
                StdOut.println(args[i]+" is not connected");
                Bag<Graph> components = G.getComponents(); //finding how many components in the graph
                StdOut.println(args[i]+" has " + components.size() + " components.");

                for(Graph g : components)
                {
                    poly = poly.times(g.getPolynomial()); //getting the polynomial
                }
            }
            StdOut.println("P(x) = "+poly);
        }
    } 
}
