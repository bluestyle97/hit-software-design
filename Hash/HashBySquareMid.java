package hash;

import java.math.BigInteger;

public class HashBySquareMid implements HashFunction {
    public Integer hash(java.lang.Number value) {
        BigInteger bigValue = BigInteger.valueOf(value.longValue());
        BigInteger square = bigValue.multiply(bigValue);
        BigInteger squareCopy = square;
        int bitCount = 0;
        do {
            squareCopy = squareCopy.divide(BigInteger.valueOf(10));
            ++bitCount;
        } while (squareCopy.compareTo(BigInteger.valueOf(0)) > 0);
        if (bitCount <= 4) {
            return square.intValue();
        }
        for (int i = 0; i < bitCount / 2 - 2; ++i) {
            square = square.divide(BigInteger.valueOf(10));
        }
        return square.mod(BigInteger.valueOf(10000)).intValue();
    }
}