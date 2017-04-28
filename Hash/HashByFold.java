package hash;

public class HashByFold implements HashFunction {
    public Integer hash(java.lang.Number value) {
        long valueCopy = value.longValue();
        int bitCount = 0;
        do {
            valueCopy /= 10;
            ++bitCount;
        } while (valueCopy > 0);
        if (bitCount <= 4) {
            return value.intValue();
        }
        int result = 0;
        long longValue = value.longValue();
        int tailBits = bitCount % 4;
        result += longValue % (int)Math.pow(10, tailBits);
        longValue /= (int)Math.pow(10, tailBits);
        while(longValue > 0) {
            result += longValue % 10000;
            longValue /= 10000;
        }
        return result;
    }
}