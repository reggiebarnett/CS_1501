//Reggie Barnett
//CS 1501

import java.lang.*;
import java.util.*;
import java.math.BigInteger;
import java.io.*;

public class RSA {
    
     static BigInteger expomod(BigInteger a, BigInteger n, BigInteger z) {
        BigInteger r = a.mod(z);
        long nl = n.longValue();
        for(long i = 1; i < nl; i++)
            r = (a.multiply(r)).mod(z);
        
        return r;
    }
     
     static BigInteger[] XGCD(BigInteger p, BigInteger q) {
        if (q.equals(BigInteger.valueOf(0)))
            return new BigInteger[] { p, BigInteger.valueOf(1), BigInteger.valueOf(0) };
        BigInteger[] vals = XGCD(q, p.mod(q));
        BigInteger d = vals[0];
        BigInteger a = vals[2];
        BigInteger b = vals[1].subtract(p.divide(q).multiply(vals[2]));
        return new BigInteger[] { d, a, b };
    }
   
   
     
    private static String BigIntegerToString(BigInteger big) {
         byte[] bytes = big.toByteArray();
         StringBuffer sb = new StringBuffer();
         for (int i = 0; i < bytes.length; i++) {
             int value = (bytes[i] & 0x7F) + (bytes[i] < 0 ? 128 : 0); // 0 to 255 Ascii Code
             sb.append((char) value);
         }
         return sb.toString();
    }
    
    static void KeyGen(int pB) throws IOException{
        //creating files to store values
        PrintWriter Pub = new PrintWriter("public.txt");
        PrintWriter Pri = new PrintWriter("private.txt");
        
        // get a random number
        Random rnd = new Random();
        
        // get two distinct primes of size primeBits
        BigInteger p = new BigInteger(pB,128,rnd);
        BigInteger q;
        do q = new BigInteger(pB,128,rnd);
        while(p.compareTo(q) >= 0);
        
        // compute the modulus
        BigInteger n = p.multiply(q);
        System.out.println("n: "+n);
        
        // compute phi
        BigInteger pMinus1 = p.subtract(BigInteger.valueOf(1));
        BigInteger qMinus1 = q.subtract(BigInteger.valueOf(1));
        BigInteger phi = pMinus1.multiply(qMinus1);
        System.out.println("phi: "+phi);
        
        // get e relatively prime to phi
        BigInteger e = BigInteger.valueOf(3);
        while(e.gcd(phi).compareTo(BigInteger.valueOf(1)) > 0)
            e = e.add(BigInteger.valueOf(2));
        System.out.println("e: "+e);
        
        // compute d the decryption exponent
        BigInteger d = e.modInverse(phi);
        /*BigInteger[] values = XGCD(e,phi); <-- Couldn't get to work
        BigInteger d = values[0];*/
        System.out.println("d: "+d);
        
        //printing to files
        Pub.print(n+"\n"+e);
        Pub.close();
        Pri.print(n+"\n"+d);
        Pri.close();
        System.out.println("Keys Generated");
    }
    
    static void Encode(String fname)throws IOException{
        BigInteger n,e,c,mB;
        String encrypt;
        char m;
        PrintWriter encFile = new PrintWriter(fname+".enc");
        File file = new File(fname);
        File pf = new File("public.txt");
        Scanner scan = new Scanner(file);
        Scanner pub = new Scanner(pf);
        n = pub.nextBigInteger();
        e = pub.nextBigInteger();
        //System.out.print(n+"\n"+e);
        while(scan.hasNextLine()){
            encrypt = scan.nextLine();
            //System.out.println(encrypt);
            for(int i = 0;i < encrypt.length();i++){
                m = encrypt.charAt(i);
                //System.out.print(m+" ");
                mB = BigInteger.valueOf(m);
                c = expomod(mB,e,n);
                //System.out.println(c);
                encFile.print(c+" ");
            }
            if(scan.hasNextLine())
                encFile.print(expomod(BigInteger.valueOf(10),e,n)+" ");
        }
        encFile.close();
        System.out.println("File Encrypted");
    }
    
    static void BDecode(String fname)throws IOException{
        BigInteger d,n,c,mB;
        BigInteger decrypt;
        char m;
        PrintWriter decFile = new PrintWriter((fname.substring(0,fname.length()-3))+"cop");
        File file = new File(fname);
        File pf = new File("private.txt");
        Scanner scan = new Scanner(file);
        Scanner pri = new Scanner(pf);
        n = pri.nextBigInteger();
        d = pri.nextBigInteger();
        //System.out.print(n+"\n"+d);
        while(scan.hasNextBigInteger()){
           c = scan.nextBigInteger();
           //System.out.print(c+" ");
           decrypt = expomod(c,d,n);
           //System.out.print(decrypt);
           decFile.print(BigIntegerToString(decrypt));
        }
        decFile.close();
        System.out.println("File Decrypted");
    }
    
    public static void main(String args[]) throws IOException{
        String command = args[0];
        if(args.length == 1)
        {
            int nBits = Integer.parseInt(command);
            KeyGen(nBits);
            return;
        }
        switch(command)
        {
            case("-encrypt"):
            {
                //get name of file to be encrypted
                String fname = args[1];
                Encode(fname);
                break;
            }
            case("-decrypt"):
            {
                //get name of file to be decrypted
                String fname = args[1];
                BDecode(fname);
                break;
            }
            default:
            {
                System.out.println("Invalid input");
                break;
            }
        }

    }
}
