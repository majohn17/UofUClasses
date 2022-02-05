package rational;

import java.math.BigInteger;

/**
 * Provides rational number (fraction) objects. The rational arithmetic provided by Rational objects is subject to
 * integer overflow if the numerator and/or denominator becomes too large.
 */
public class BigRat
{
    /**
     * The numerator of this Rat. The gcd of |num| and den is always 1.
     */
    private BigInteger num;

    /**
     * The denominator of this Rat. den must be positive.
     */
    private BigInteger den;

    /**
     * Creates the rational number 0
     */
    public BigRat ()
    {
        this(0);
    }

    /**
     * Creates the rational number n.
     * 
     * DO NOT MODIFY THE HEADER OF THIS CONSTRUCTOR. IT SHOULD TAKE A SINGLE LONG AS ITS PARAMETER
     */
    public BigRat (long n)
    {
        this(n, 1);
    }

    /**
     * If d is zero, throws an IllegalArgumentException. Otherwise creates the rational number n/d
     * 
     * DO NOT MODIFY THE HEADER OF THIS CONSTRUCTOR. IT SHOULD TAKE TWO LONGS AS ITS PARAMETERS
     */
    public BigRat (long n, long d)
    {
        this(new BigInteger(String.valueOf(n)), new BigInteger(String.valueOf(d)));
    }

    /**
     * If d is zero, throws an IllegalArgumentException. Otherwise creates the rational number n/d
     */
    public BigRat (BigInteger n, BigInteger d)
    {
        // Denominator must not be zero
        if (d.equals(BigInteger.ZERO))
        {
            throw new IllegalArgumentException();
        }

        // Deals with signs
        if (d.compareTo(BigInteger.ZERO) == -1)
        {
            d = d.negate();
            n = n.negate();
        }

        // Deal with lowest terms
        BigInteger g = n.gcd(d);
        this.num = n.divide(g);
        this.den = d.divide(g);
    }

    /**
     * Returns the sum of this and r Rat x = new Rat(5, 3); Rat y = new Rat(1, 5); Rat z = x.add(y); a/b + c/d = (ad +
     * bc) / bd
     */
    public BigRat add (BigRat r)
    {
        BigInteger n = this.num.multiply(r.den).add(this.den.multiply(r.num));
        BigInteger d = this.den.multiply(r.den);
        return new BigRat(n, d);
    }

    /**
     * Returns the difference of this and r a/b - c/d = (ad - bc) / bd
     */
    public BigRat sub (BigRat r)
    {
        BigInteger n = this.num.multiply(r.den).subtract(this.den.multiply(r.num));
        BigInteger d = this.den.multiply(r.den);
        return new BigRat(n, d);
    }

    /**
     * Returns the product of this and r Rat x = new Rat(5, 3); Rat y = new Rat(1, 5); Rat z = x.mul(y); a/b * c/d =
     * ac/bd
     */
    public BigRat mul (BigRat r)
    {
        return new BigRat(this.num.multiply(r.num), this.den.multiply(r.den));
    }

    /**
     * If r is zero, throws an IllegalArgumentException. Otherwise, returns the quotient of this and r. a/b / c/d = ad /
     * bc
     */
    public BigRat div (BigRat r)
    {
        if (r.num.equals(BigInteger.ZERO))
        {
            throw new IllegalArgumentException();
        }
        else
        {
            return new BigRat(this.num.multiply(r.den), this.den.multiply(r.num));
        }
    }

    /**
     * Returns a negative number if this < r, zero if this = r, a positive number if this > r To compare a/b and c/d,
     * compare ad and bc
     */
    public int compareTo (BigRat r)
    {
        return this.num.multiply(r.den).compareTo(this.den.multiply(r.num));
    }

    /**
     * Returns a string version of this in simplest and lowest terms. Examples: 3/4 => "3/4" 6/8 => "3/4" 2/1 => "2" 0/8
     * => "0" 3/-4 => "-3/4"
     */
    public String toString ()
    {
        if (this.den.equals(BigInteger.ONE))
        {
            return this.num + "";
        }
        else
        {
            return this.num + "/" + this.den;
        }
    }
}
