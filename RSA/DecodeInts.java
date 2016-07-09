//Reggie Barnett
//CS 1501

import java.util.*;

public class DecodeInts {
    
    static long expomod(long a, long n, long z) {
        long r = a % z;

        for(long i = 1; i < n; i++)
            r = (a * r) % z;
        
        return r;
    }
    
    public static void main(String args[]) {
        long d,n,c;
        char m;
        int num;
        int i = 0;
        Scanner scan = new Scanner(System.in);
        //Ask user for public key (e,n)
        System.out.print("Enter the decoding exponent d: ");
        d = Long.parseLong(scan.next());
        System.out.print("Enter the modulus n: ");
        n = Long.parseLong(scan.next());
        //Ask user for string
        System.out.print("Enter the number of integers to decode: ");
        num = scan.nextInt();
        while(i<num){
            c = scan.nextLong();
            m = (char)expomod(c,d,n);
            System.out.print(m);
            i++;
        }
    }
}