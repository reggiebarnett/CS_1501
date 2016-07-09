//Reggie Barnett
//CS 1501

import java.util.*;

public class Decode {
    
    static long expomod(long a, long n, long z) {
        long r = a % z;

        for(long i = 1; i < n; i++)
            r = (a * r) % z;
        
        return r;
    }
    
    public static void main(String args[]) {
       long d,n,c;
       char m;
       Scanner scan = new Scanner(System.in);
        //Ask user for private key (d,n)
        System.out.print("Please enter a private key pair (d, n).\nEnter \"d\": ");
        d = Long.parseLong(scan.next());
        System.out.print("Enter \"n\": ");
        n = Long.parseLong(scan.next());
        //Ask user for int
        System.out.print("Please enter the integer to be decoded: ");
        c = Long.parseLong(scan.next());
        //decoding c to m = c^d mod n
        m = (char)expomod(c,d,n);
        System.out.println("Decoding "+c+" to "+m);
    }
}
