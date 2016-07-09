//Reggie Barnett - rdb26
//Assignment 3

/*
To create the auxiliary graph G* = (E*, V*) from graph G =(E, V), where 
E = set of all edges in G and V is the set of all vertices in G:
    Set V* = V ∪ {s}, where s is a new vertex not in V.  Note: V* is the vertex set of G*.
    Set E* = E ∪ {s->v | v ε V}, where E* is the edge set of G* and s is the vertex described above.
*/
public class CreateAuxiliaryGraph {
    
   /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         //Reading in from the file
        String[] in = StdIn.readAllLines();
        //Taking the number of vertices and setting that as the new vertex
        int newVertex = Integer.parseInt(in[0]);
        //Updating vertex amount
        int updateV = newVertex;
        updateV++;
        in[0] = Integer.toString(updateV);
        //Updating amount of edges 
        int updateE = Integer.parseInt(in[1]);
        updateE += newVertex;
        in[1] = Integer.toString(updateE);
        //Printing out original graph
        for(int i=0; i<in.length; i++)
            StdOut.println(in[i]);
        //Adding to graph, making G*
        for(int i=0; i<newVertex;i++)
            StdOut.println(newVertex+" "+i+" 0.0");
    }
}
