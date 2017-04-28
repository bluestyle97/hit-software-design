package hash;

public class HashByMod implements HashFunction {
    public Integer hash(java.lang.Number value) {
        long result = value.longValue() % 127;
        return (int)result;
    }
}