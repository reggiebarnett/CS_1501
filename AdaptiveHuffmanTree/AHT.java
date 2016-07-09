//Reggie Barnett
//CS1501

import java.lang.StringBuffer;

public class AHT {
     Node[] rlo;          //list of nodes in the tree in reverse-level order rlo[255], rlo[254], ...
     Node NYT;            //special node representing unseen characters
     Node[] characters;   //pointers to the nodes in the tree holding each character (if null then the character has not been seen)
     int size;            //number of Nodes in the tree

     public AHT(){
          characters = new Node[256];
          rlo = new Node[256];

          for(int k=0; k<256; k++){
              characters[k] = null;
          }

          NYT = new Node(0);
          NYT.place = 255;               //NYT is going to be placed at the root of the tree
          NYT.weight = 0;                //NYT has weight 0
          NYT.label = '0';               //NYT is always a leftchild
          NYT.leaf = true;               //NYT is always a leaf
          rlo[NYT.place] = NYT;          //place NYT at the root of the tree
          NYT.parent = null;             //the root of the tree has no parent
          size = 1;
     }

   /*
    * Update the adaptive Huffman tree after inserting character c. This is the UPDATE procedure discussed in class.
    */
   public void update(char c){
       Node newNode;
       if(!characterInTree(c)){
          Node newNYT = new Node(0);
          newNYT.place = NYT.place - 2;              
          newNYT.weight = 0;                
          newNYT.label = '0';              
          newNYT.leaf = true;      
          newNYT.parent = NYT;
          rlo[NYT.place-2] = newNYT;
          
          newNode = new Node(1, c);
          newNode.place = NYT.place - 1;
          newNode.label = '1';
          newNode.parent = NYT;
          rlo[NYT.place-1] = newNode;
          characters[c] = newNode;
          NYT = newNYT;
          size=size+2;
       }
       else{
           newNode = characters[c];
           characters[c].weight++;
           satisfiesSiblingProperty();
       }
       Node node = newNode;
       while(node.place != 255) {
           node = node.parent;
           node.weight++;
           satisfiesSiblingProperty();
       }
       
   }


   /*
    * Swap Nodes u and v in the reverse-level ordered array. Note: Node u is at index u.place!
    */
   private void swap(Node u, Node v){      
       Node tempP = v.parent;
       char tempLabel = v.label;
       int tempPlace = v.place;
       int uplace = u.place;
       int vplace = v.place;
       Node V = rlo[u.place];
       Node U = rlo[v.place];
       U.label = V.label;
       U.place = V.place;
       U.parent = V.parent;
       V.label = tempLabel;
       V.place = tempPlace;
       V.parent = tempP;
       rlo[uplace] = U;
       rlo[vplace] = V;
   }

   /*
    * Return true if character c has been seen, otherwise, return false.
    */
   public boolean characterInTree(int c){
       for(int i = 255; rlo[i] != null; i--) {
           if(rlo[i].character == c)
               return true;
       }
       return false;
   }
   
   /*
    * Return true if the reverse-level order traversal is a monotonically decreasing sequence, otherwise, return false.
    */
   private boolean satisfiesSiblingProperty(){
       boolean satisfies = true;
       for(int i = NYT.place + 1; i < 255; i++){
           if(i < 254 && rlo[i + 2].weight < rlo[i].weight && rlo[i + 2] != rlo[i].parent) {
               swap(rlo[i + 2], rlo[i]);
               satisfies = false;
           }
           else if(rlo[i-1].weight > rlo[i].weight && rlo[i] != rlo[i-1].parent){
               swap(rlo[i - 1], rlo[i]);
               satisfies = false;
           }   
       }
       return satisfies;
   }
   
   /*
    * Return the sequence of labels (characters) from the root to the NYT node.
    */
   public StringBuffer getCodeWordForNYT() {
       StringBuffer sb = new StringBuffer();
       Node node = NYT;
       sb.append(node.label);
       while(node.parent != null) {
	     node = node.parent;
             if(node.parent != null)
                 sb.insert(0, node.label);
       }
       return sb;
   }

   /*
    * Return the sequence of labels (characters from the root to the Node for character c.
    */
   public StringBuffer getCodeWordFor(char c){
       StringBuffer sb = new StringBuffer();
       Node node = characters[c];
       for(int i = 255; rlo[i] != null; i--){ 
	   if(rlo[i].character == c){
		node = rlo[i];
		break;
           }
       }
       if(node != null){
           sb.append(node.label);
           node = node.parent;
           while(node.parent != null) {
               sb.insert(0, node.label);
               node = node.parent;
           }
       }
       
       return sb;
   }

   /*
    * return the reference to the root node.
    */
   public Node root(){
       return rlo[255];
   }

   public int size(){
      return size;
   }

   /*
    * I've provided this to help debug your tree.
    */
   public String toString(){
       String result = "[";

       for(int k=255; k>=0 ; k--){
           if(rlo[k] != null)
                result += "(" + rlo[k].weight + "," + rlo[k].place + "," + rlo[k].character + ") ";
       }
       return result + "]\nsize = " + size;
   }
   
   public static void main(String[] args){
       AHT aht = new AHT();
       char c;
       while(StdIn.hasNextChar()){
           c = StdIn.readChar();
           if(c == 10 && !StdIn.hasNextChar())
               break;
           else   
                aht.update(c);
       } 
       StdOut.println(aht);
   }
}
