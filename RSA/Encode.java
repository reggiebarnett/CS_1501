//Reggie Barnett
//CS 1501

import java.util.*;

public class Encode {
    
    static long expomod(long a, long n, long z) {
        long r = a % z;

        for(long i = 1; i < n; i++)
            r = (a * r) % z;
        
        return r;
    }
    
    public static void main(String args[]) {
        long c,e,n;
        char m;
        Scanner scan = new Scanner(System.in);
        //Ask user for public key (e,n)
        System.out.print("Please enter a public key pair (e, n).\nEnter \"e\": ");
        e = Long.parseLong(scan.next());
        System.out.print("Enter \"n\": ");
        n = Long.parseLong(scan.next());
        //Ask user for char
        System.out.print("Please enter the individual character to be encoded: ");
        if(scan.hasNextLong())
            m = (char)scan.nextLong();
        else
            m = scan.next().charAt(0);
        //Encoding c = m^e mod n
        c = expomod(m,e,n);
        System.out.println("Transmitting encoded "+m+" as "+c);
    }
}
