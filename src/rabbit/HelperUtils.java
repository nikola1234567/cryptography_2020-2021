package rabbit;

import java.math.BigInteger;

public class HelperUtils {

    /*
    * param: base - osnovata vo stepenot
    * param: exponent - stepenot/exponentot
    * returns: long - rezultatot od operacijata (base^exponent)
    * */
    public static long power(int base, int exponent) {
        long result = 1;
        for (;exponent != 0; --exponent)
        {
            result *= base;
        }
        return result;
    }

    /*
    * param: number - brojot od koj sakame da izvleceme podniza od bitovi
    * param: k - kolkava podniza od bitovi
    * param: p - od koja pozicija da ja zeme podnizata
    * returns: BigInteger - podnizata od bitovi kako cel broj
    * */
    public static BigInteger bitExtracted(BigInteger number, int k, int p) {
        BigInteger mask =  BigInteger.valueOf(1l).shiftLeft(k).subtract(BigInteger.valueOf(1l));
        BigInteger pair = number.shiftRight(p);
        BigInteger value = pair.and(mask);
        return value;
    }

    /*
    * param: x - prv operand
    * param: y - vtor operand
    * returns: long - proizvodot od x i y
    * */
    public static long karatmultiply(long x, long y)
    {
        // Finding length of both integer
        // numbers x and y
        int noOneLength = numLength(x);
        int noTwoLength = numLength(y);

        // Find maximum length from both numbers
        // using math library max function
        int maxNumLength
                = Math.max(noOneLength, noTwoLength);

        // For very small values
        // multiply directly
        int smallValue = 1 << 4;
        if (maxNumLength < smallValue)
            return x * y;

        // Rounding up the
        // divided Max length
        maxNumLength
                = (maxNumLength / 2) + (maxNumLength % 2);

        // Multiplier
        long maxNumLengthTen
                = (long)Math.pow(10, maxNumLength);

        // Compute the expressions
        long b = x / maxNumLengthTen;
        long a = x - (b * maxNumLengthTen);
        long d = y / maxNumLengthTen;
        long c = y - (d * maxNumLength);

        // Compute all mutilpying variables
        // needed to get the multiplication
        long z0 = karatmultiply(a, c);
        long z1 = karatmultiply(a + b, c + d);
        long z2 = karatmultiply(b, d);

        return z0 + ((z1 - z0 - z2) * maxNumLengthTen)
                + (z2 * (long)(Math.pow(10, 2 * maxNumLength)));
    }

    /*
    * param: n - broj
    * returns: int - broj na cifri vo vlezniot argument n
    * */
    public static int numLength(long n)
    {
        int noLen = 0;
        while (n > 0) {
            noLen++;
            n /= 10;
        }

        // Returning length of number n
        return noLen;
    }

    /*
    * param: l - broj
    * returns: BigInteger - brojot dobien na vlez ako e povekje od 32 biti se doveduva na <= 32 biti
    * */
    public static BigInteger shrinkTo32Bits(BigInteger l) {
        return l.mod(BigInteger.valueOf(HelperUtils.power(2, 32)));
    }
}
