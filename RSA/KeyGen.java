//Reggie Barnett
//CS 1501

import java.util.*;
        
public class KeyGen {
    
    static long findfirstnocommon(long n) {
        long j;
        for(j = 2; j < n; j++)
            if(euclid(n,j) == 1) 
                return j;
        return 0;
    }
    
    static long euclid(long m, long n) {
    // pre: m and n are two positive integers (not both 0)
    // post: returns the largest integer that divides both
    // m and n exactly
        while(m > 0) {
            long t = m;
            m = n % m;
            n = t;
        }
        return n;
    }
    
    static long findinverse(long n, long phi) {
        long i = 2;
        while( ((i * n) % phi) != 1) 
            i++;
        return i;
    }
    
    public static void main(String args[]) { 
        long p,q,n,phi,e,d;
        Scanner scan = new Scanner(System.in);
        //Ask user for two distinct primes with p < q
        System.out.print("Please enter a prime number (p): ");
        p = Long.parseLong(scan.next());
        System.out.print("Please enter another prime number (q): ");
        q = Long.parseLong(scan.next());
        while(q <= p){
            System.out.print("Insert a prime number greater than "+p+": ");
            q = Long.parseLong(scan.next());
        }
        System.out.println("p = "+p+" q = "+q);
        //Compute n 
        n = p * q;
        System.out.println("The value of n = "+n);

        //Compute phi 
        phi = (p-1) * (q-1);
        System.out.println("The value of phi = "+phi);

        // choose a random prime e between 1 and phi, exclusive,  
        // so that e has no common factors with phi.
        e = findfirstnocommon(phi);
        System.out.println("The value of e = "+e);

        // Compute d as the multiplicative inverse of e
        d = findinverse(e,phi); 
        System.out.println("The value of d = "+d);
  
    }
}
