//Reggie Barnett
//rdb26@pitt.edu
//CS 1501
//Assignment 4: Burrows-Wheeler Data Compression Algorithm

public class BurrowsWheeler {
    //apply Burrows-Wheeler encoding, reading from standard input and writing to standard output - 20 points
    public static void encode(){
        int i;
        String s = BinaryStdIn.readString();
        CircularSuffixArray CSA = new CircularSuffixArray(s);
        //find the first string (0th index) of the CSA
        for(i=0; i<CSA.length(); i++)
            if (CSA.index(i)==0)
                break;
        //print out the position of 0th index of CSA
        BinaryStdOut.write(i);
        //print out the last char of each permutation
        for (i=0; i<CSA.length(); i++){
            int index = (CSA.index(i)+s.length()-1)%s.length();
            BinaryStdOut.write(s.charAt(index));
        }
        BinaryStdOut.close();
    }
    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output - 20 points
    public static void decode(){
        //row with original string before compression
        int start = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int count[] = new int[256]; 
        int next[] = new int[s.length()]; //array to reconstruct from sorted array
        //putting ascii vals of s into count array
        for (int i=0; i<s.length(); i++) 
            count[s.charAt(i)+1]++; 
        //shifting count array
        for (int i=1; i<256; i++)
            count[i] += count[i-1];
        //constructing next array
        for (int i=0; i<s.length(); i++) 
            next[count[s.charAt(i)]++] = i; 
        int c = 0; //tracks length of string
        for (int i=next[start]; c<s.length(); i=next[i]){ //jumps between next array values
            BinaryStdOut.write(s.charAt(i)); //prints out letters of s found by next array
            c++;
        }
        BinaryStdOut.close();
    }
    public static void main(String[] args){
        // if args[0] is '-', apply Burrows-Wheeler encoding   - 5 points
        if (args[0].equals("-")) 
            encode();
         // if args[0] is '+', apply Burrows-Wheeler decoding   - 5 points
        else if (args[0].equals("+")) 
            decode();
    }
}
