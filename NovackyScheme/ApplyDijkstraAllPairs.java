//Reggie Barnett - rdb26
//Assignment 3

/*
ApplyDijkstraAllPairs takes the graph G** and finds the shortest path between every vertex 
and every other vertex using DijkstraAllPairsSP's algorithm. The edges of G** are reverse-calibrated
to recover the edge-weights in the original graph G. Now use these edge-weights to determine the 
shortest paths  in the original graph G.
*/
public class ApplyDijkstraAllPairs {
    
    public static void main(String[] args) {
        //Reading in amount of vertices
        int vertices = Integer.parseInt(StdIn.readLine());
        //Storing vertex weights
        double[] vertW = new double[vertices];
        for(int i=0;i<vertices;i++)
            vertW[i] = Double.parseDouble(StdIn.readLine());
        //Reading in new graph, G**
        String[] Gstarstar = StdIn.readAllLines();
        //Making graph
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(Gstarstar);
        //Dijkstra All Pairs
        DijkstraAllPairsSP DAP = new DijkstraAllPairsSP(G);
        //Printing output of paths
        for(int i=0; i<G.V(); i++){
            for(int j=0; j<G.V(); j++){
                if(!DAP.hasPath(i,j))
                    StdOut.print(i+" to "+j+"\t\tno path");
                else{
                    double newEW;
                    double EW;
                    Iterable<DirectedEdge> path = DAP.path(i,j);
                    double totalWeight = 0;
                    String sp = ""; //String that's going to form the path
                    for(DirectedEdge x : path){
                        newEW = x.weight();
                        EW = newEW + vertW[x.to()] - vertW[x.from()]; //reverse calibrate
                        totalWeight += EW;
                        sp += "\t"+x.from()+"->"+x.to()+" "+String.format("%.2f",EW); //adding to path
                    }
                    StdOut.print(i+" to "+j+"\t("+String.format("%.2f",totalWeight)+") ");
                    StdOut.print(sp);
                }
                StdOut.println();
            }
            StdOut.println();
        }
    }
}
