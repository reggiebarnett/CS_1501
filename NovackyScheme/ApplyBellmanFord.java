//Reggie Barnett - rdb26
//Assignment 3

/*
ApplyBellman takes the auxiliary graph G* and finds the shortest path from vertex s to all 
the other vertices. These distances are the weights assigned to each of the vertices in the 
original graph G to re-weight the edges of G and are calculated by Bellman-Ford. In 
addition to finding the vertex-weights, ApplyBellman forms a graph identical to G, 
call it G** with newly calculated edge-weights (note these weights are now non-negative).
*/
public class ApplyBellmanFord {
    
    public static void main(String[] args) {
        //Taking input from CreateAuxiliaryGraph
        String[] Gstar = StdIn.readAllLines();
        //Making graph
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(Gstar);
        //Getting source vertex from command line
        int sv;
        if(args.length>=1 && Integer.parseInt(args[0])<G.V() && Integer.parseInt(args[0])>=0)
            sv = Integer.parseInt(args[0]);
        else{ //vertex not in arguments or vertex chosen doesn't exist
            StdOut.println("Vertex does not exist");
            sv = G.V()-1; //Defaults to vertex added
        }
        //BellmanFord
        BellmanFordSP BF = new BellmanFordSP(G,sv);
        //Printing vertex amount and shortest path to each vertex
        StdOut.println(G.V()-1);
        double[] vertW = new double[G.V()-1];
        int j = 0;
        for(int i=0;i<G.V();i++){
            if(i != sv){
                vertW[j] = BF.distTo(i); //storing vertex weight for later use
                StdOut.printf("%.2f\n",vertW[j]);
                j++;
            }
        }
        //Creating G**
        int NV = G.V()-1;
        int NE = G.E()-NV;
        StdOut.println(NV); //New vertex amount (original vertices)
        StdOut.println(NE); //New edge amount (original edges)
        //Calculating the new edge weights
        j=2;
        for(int i=0;i<NE;i++){
            String[] graph =  Gstar[j].split(" ");
            int fromV = Integer.parseInt(graph[0]); //from vertex
            int toV = Integer.parseInt(graph[1]); //to vertex
            double EW = Double.parseDouble(graph[2]); //current edge weight
            double newEW = EW + vertW[fromV] - vertW[toV]; //new edge weight
            StdOut.println(fromV+" "+toV+" "+newEW);
            j++;
        }
    }
}
