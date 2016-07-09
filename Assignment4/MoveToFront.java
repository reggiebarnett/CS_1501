//Reggie Barnett
//rdb26@pitt.edu
//CS 1501
//Assignment 4: Burrows-Wheeler Data Compression Algorithm

public class MoveToFront{
    // apply move-to-front encoding, reading from standard input and writing to standard output - 20 points
    public static void encode(){ 
        char[] chars = new char[256]; //array for ascii values
        int index;
	char c;
	index = 0;
        //filling with ascii values
        for(int i=0; i<256; i++) 
            chars[i] = (char)i;
	while (!BinaryStdIn.isEmpty()){
            //read char by char from binary
            c = BinaryStdIn.readChar(); 
            //shifting letters
            for (int i=0; i < 256; i++){ 
                if(chars[i] == c)
                    index = i;
            }
            for(int j = index; j != 0; j--)
		chars[j] = chars[j-1];
            BinaryStdOut.write((char)index); //print position of c
            chars[0] = c;
    	}
	BinaryStdOut.close();
    }
    // apply move-to-front decoding, reading from standard input and writing to standard output - 20 points
    public static void decode(){
        char[] chars = new char[256];
        char k, c;
        //fill chars with all ascii values
        for(int i=0; i<256; i++)
            chars[i] = (char)i;
	while (!BinaryStdIn.isEmpty()) {
            k = BinaryStdIn.readChar(); //read char by char from binary
            for (c=chars[k]; k>0; chars[k]=chars[--k]);  //shifting back through chars array
		chars[k] = c; //make sure count is at 0
		BinaryStdOut.write(c); //print char found at position read in
            }
        BinaryStdOut.close();
    }
    
    public static void main(String[] args){
        if(args[0].equals("-"))     // if args[0] is '-', apply move-to-front encoding - 5 points
            encode();
        else if(args[0].equals("+"))// if args[0] is '+', apply move-to-front decoding - 5 points
            decode();
    }
} 
