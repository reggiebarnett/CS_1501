import java.math.BigInteger;

public class Polynomial {
    private BigInteger[] coef;  // coefficients
    private int deg;     		// degree of polynomial (0 for the zero polynomial)
    
    /** Creates the constant polynomial P(x) = 1.
      */
    public Polynomial(){
        coef = new BigInteger[1];
        coef[0] = BigInteger.valueOf(1);
        deg = 0;
    }
    /** Creates the linear polynomial of the form P(x) =  x + a.
      */
    public Polynomial(BigInteger a){
        coef = new BigInteger[2];
        coef[1] = BigInteger.valueOf(1);
        coef[0] = a;
        deg = 1;
    }
    /** Creates the polynomial P(x) = a * x^b.
      */
    public Polynomial(BigInteger a, int b) {
        coef = new BigInteger[b+1];
        for(int i = 0; i < b + 1; i++)
        {
			coef[i] = BigInteger.valueOf(0);
		}
        coef[b] = a;
        deg = degree();
    }
    /** Return the degree of this polynomial (0 for the constant polynomial).
      */
    public int degree() {
        int d = 0;
        for (int i = 0; i < coef.length; i++)
            if (coef[i] != BigInteger.valueOf(0)) d = i;
        return d;
    }
    /** Return the sum of this polynomial and b, i.e., return c = this + b.
      */
    public Polynomial plus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(BigInteger.valueOf(0), Math.max(a.deg, b.deg));
        for (int i = 0; i <= a.deg; i++){
            int coefInt = c.coef[i].intValue();
            coefInt += a.coef[i].intValue();
            c.coef[i] = BigInteger.valueOf(coefInt);
        }
        for (int i = 0; i <= b.deg; i++){
            int coefInt = c.coef[i].intValue();
            coefInt += b.coef[i].intValue();
            c.coef[i] = BigInteger.valueOf(coefInt);
        }
        
        c.deg = c.degree();
        return c;
    }
    /** Return the difference of this polynomial and b, i.e., return (this - b).
      */
    public Polynomial minus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(BigInteger.valueOf(0), Math.max(a.deg, b.deg));
        for (int i = 0; i <= a.deg; i++){
            int coefInt = c.coef[i].intValue();
            coefInt += a.coef[i].intValue();
            c.coef[i] = BigInteger.valueOf(coefInt);
        }
        for (int i = 0; i <= b.deg; i++){
            int coefInt = c.coef[i].intValue();
            coefInt -= b.coef[i].intValue();
            c.coef[i] = BigInteger.valueOf(coefInt);
        }
        c.deg = c.degree();
        return c;
    }
    /** Return the product of this polynomial and b, i.e., return (this * b).
      */
    public Polynomial times(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(BigInteger.valueOf(0), a.deg + b.deg);
        for (int i = 0; i <= a.deg; i++)
            for (int j = 0; j <= b.deg; j++){
                int coefInt = c.coef[i+j].intValue();
                coefInt += (a.coef[i].intValue() * b.coef[j].intValue());
                c.coef[i+j] = BigInteger.valueOf(coefInt);
            }
        c.deg = c.degree();
        return c;
    }
    /** Return the composite of this polynomial and b, i.e., return this(b(x))  - compute using Horner's method.
      */
    public Polynomial compose(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(BigInteger.valueOf(0), 0);
        for (int i = a.deg; i >= 0; i--) {
            Polynomial term = new Polynomial(a.coef[i], 0);
            c = term.plus(b.times(c));
        }
        return c;
    }
    /** Return true whenever this polynomial and b are identical to one another.
      */
    public boolean equals(Polynomial b) {
        Polynomial a = this;
        if (a.deg != b.deg) return false;
        for (int i = a.deg; i >= 0; i--)
            if (a.coef[i] != b.coef[i]) return false;
        return true;
    }
    /** Evaluate this polynomial at x, i.e., return this(x).
      */
    public int evaluate(int x) {
        int p = 0;
        for (int i = deg; i >= 0; i--)
            p = coef[i].intValue() + (x * p);
        return p;
    }
    /** Return the derivative of this polynomial.
      */
    public Polynomial differentiate() {
        if (deg == 0) return new Polynomial(BigInteger.valueOf(0), 0);
        Polynomial deriv = new Polynomial(BigInteger.valueOf(0), deg - 1);
        deriv.deg = deg - 1;
        for (int i = 0; i < deg; i++){
            int coefInt = deriv.coef[i].intValue();
            coefInt = (i + 1) * coef[i + 1].intValue();
            deriv.coef[i] = BigInteger.valueOf(coefInt);
        }
        return deriv;
    }
    /** Return a textual representation of this polynomial.
      */
    public String toString() {
        if (deg ==  0) return "" + coef[0];
        if (deg ==  1) return coef[1] + "x + " + coef[0];
        String s = coef[deg] + "x^" + deg;
        for (int i = deg-1; i >= 0; i--) {
			if(coef[i] != null)
			{
				if      (coef[i].intValue() == 0) continue;
				else if (coef[i].intValue()  > 0) s = s + " + " + ( coef[i].intValue());
				else if (coef[i].intValue()  < 0) s = s + " - " + (-coef[i].intValue());
				if      (i == 1) s = s + "x";
				else if (i >  1) s = s + "x^" + i;
			}
        }
        return s;
    }

    public static void main(String[] args) {
        Polynomial zero = new Polynomial(BigInteger.valueOf(0), 0);	//0*x^0
        Polynomial p1   = new Polynomial(BigInteger.valueOf(4), 3);	//4*x^3
        System.out.println("p1(x) = " + p1);
        Polynomial p2   = new Polynomial(BigInteger.valueOf(3), 2);	//3*x^2
        Polynomial p3   = new Polynomial(BigInteger.valueOf(-1), 0);//-1*x^0
        Polynomial p4   = new Polynomial(BigInteger.valueOf(-2), 1);
        Polynomial p    = p1.plus(p2).plus(p3).plus(p4);   			// 4x^3 + 3x^2  - 2x - 1

        Polynomial q1   = new Polynomial(BigInteger.valueOf(3), 2);
        Polynomial q2   = new Polynomial(BigInteger.valueOf(5), 0);
        Polynomial q    = q1.minus(q2);                     // 3x^2 - 5


        Polynomial r    = p.plus(q);
        Polynomial s    = p.times(q);
        Polynomial t    = p.compose(q);

        System.out.println("zero(x) =     " + zero);
        System.out.println("p(x) =        " + p);
        System.out.println("q(x) =        " + q);
        System.out.println("p(x) + q(x) = " + r);
        System.out.println("p(x) * q(x) = " + s);
        System.out.println("p(q(x))     = " + t);
        System.out.println("0 - p(x)    = " + zero.minus(p));
        System.out.println("p(3)        = " + p.evaluate(3));
        System.out.println("p'(x)       = " + p.differentiate());
        System.out.println("p''(x)      = " + p.differentiate().differentiate());

        Polynomial poly = new Polynomial();

        for(int k=0; k<=3; k++){
            poly = poly.times(new Polynomial(BigInteger.valueOf(-k)));
        }

        System.out.println(poly);
   }

}
