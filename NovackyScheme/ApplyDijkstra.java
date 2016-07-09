//Reggie Barnett - rdb26
//Assignment 3

/*
ApplyDijkstra takes the graph G** and finds the shortest path between a specified vertex 
(in the example above this vertex is 0) and every other vertex using Dijkstra's algorithm. 
The edges of G** are reverse-calibrated to recover the edge-weights in the original graph G. 
Now use these edge-weights to determine the shortest paths  in the original graph G.
*/
public class ApplyDijkstra {

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
        //Getting source vertex from command line
        int sv;
        if(args.length>=1 && Integer.parseInt(args[0])<G.V() && Integer.parseInt(args[0])>=0)
            sv = Integer.parseInt(args[0]);
        else{ //vertex not in arguments or vertex chosen doesn't exist
            StdOut.println("Vertex does not exist");
            sv = G.V()-1; //Defaults to vertex added
        }
        //Dijkstra
        DijkstraSP D = new DijkstraSP(G,sv);
        
        for(int i=0; i<G.V(); i++){
            if(!D.hasPathTo(i))
                StdOut.print(sv+" to "+i+"\t\tno path");
            else{
                double newEW;
                double EW;
                Iterable<DirectedEdge> path = D.pathTo(i);
                double totalWeight = 0;
                String sp = ""; //String that's going to form the path
                for(DirectedEdge x : path){
                    newEW = x.weight();
                    EW = newEW + vertW[x.to()] - vertW[x.from()]; //reverse calibrate
                    totalWeight += EW;
                    sp += "\t"+x.from()+"->"+x.to()+" "+String.format("%.2f",EW); //adding to path
                }
                StdOut.print(sv+" to "+i+"\t("+String.format("%.2f",totalWeight)+") ");
                StdOut.print(sp);
            }
            StdOut.println();
        }
    } 
}
