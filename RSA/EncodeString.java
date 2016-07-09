//Reggie Barnett
//CS 1501

import java.util.*;

public class EncodeString {
    
    static long expomod(long a, long n, long z) {
        long r = a % z;

        for(long i = 1; i < n; i++)
            r = (a * r) % z;
        
        return r;
    }
    
    public static void main(String args[]) {
        long c,e,n;
        String m;
        char x;
        Scanner scan = new Scanner(System.in);
        //Ask user for public key (e,n)
        System.out.print("Enter the encoding exponent e: ");
        e = Long.parseLong(scan.next());
        System.out.print("Enter the modulus n: ");
        n = Long.parseLong(scan.next());
        scan.nextLine(); //eat newline
        //Ask user for string
        System.out.print("Enter string to encode: ");
        m = scan.nextLine();
        for(int i = 0; i < m.length(); i++){
            x = m.charAt(i);
            //Encoding c = x^e mod n
            c = expomod(x,e,n);
            System.out.print(" "+c);
        }
        
    }
    
}
