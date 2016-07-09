//Reggie Barnett
//CS1501


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;
public class Compress{

    //Assumes s is 8 characters long
    public static char bitToChar(String s) {
        int ch = 0;
        for(int i = 0; i <= 7; i++) {
            int bit = (int)Math.pow(2.0, 7.0 - (double)i);
            if(s.charAt(i) == '1')
                ch += bit;
        }
        return (char)ch;
    }
   
    public static StringBuffer getBitStream(char c) {
        int ch = (int)c;
        StringBuffer sb = new StringBuffer();
        for(double i = 7.0; i >= 0.0; i--) {
            int bit = (int)Math.pow(2.0, i);
            sb.append(ch / bit);
            ch = ch % bit;
        }
        return sb;
    }
   
    public static void main(String[] args) throws IOException{     
        AHT aht = new AHT();
        PrintWriter outFile = new PrintWriter("statistics.txt");
	StringBuffer sb = new StringBuffer();
        
        double bitsRead=0, bitsTrans=0;
	   
         while(!BinaryStdIn.isEmpty()){  
            char c = BinaryStdIn.readChar();
		if(c == 10 && !StdIn.hasNextChar()) 
                    continue;
                    //StdOut.print(c + " ");
            outFile.print(c);
            if(aht.size() == 1) {
                sb.append(getBitStream(c));
                //StdOut.println(getBitStream(c));
                aht.update(c);
            }
            else if(aht.characterInTree(c)) {
                sb.append(aht.getCodeWordFor(c));
                //StdOut.print("code word for " + c + ": ");
                //StdOut.println(aht.getCodeWordFor(c));
                aht.update(c);
            }
            else {
                sb.append(aht.getCodeWordForNYT());
                //StdOut.println(aht.getCodeWordForNYT());
                //StdOut.println(getBitStream(c));
                sb.append(getBitStream(c));
                aht.update(c);
            }
            bitsRead += 8;
        }
	//BinaryStdOut.write(sb.toString());
        //BinaryStdOut.flush();
        //StdOut.println(sb.toString());
        //StdOut.println(sb.length());
         
         bitsTrans = sb.length();
        //Checking length of string, if not long enough, appends 0's to end
        int r = sb.length() % 8;
        if(r != 0){
            for(int i = 0; i < 8-r; i++)
                sb.append('0');
        }
        
        //StdOut.println(sb.toString());
        //StdOut.println(sb.length());
        
        //Converting string
        String bits = sb.toString();
        String[] Hex = new String[sb.length()/8];
        for(int i = 0; i < bits.length()/8; i++){
            Hex[i] = bits.substring(i*8,(i+1)*8);
            BinaryStdOut.write(bitToChar(Hex[i]));
        } 
        BinaryStdOut.flush();
        
        //StdOut.println();
        //StdOut.print(aht.toString());
        
        double ratio = 100.0*((bitsRead-bitsTrans)/bitsRead);
        outFile.println();
        outFile.println("bits read = "+bitsRead);
        outFile.println("bits transmitted = "+bitsTrans);
        outFile.println("compression ratio = "+ratio);
        outFile.close();
    }
}
