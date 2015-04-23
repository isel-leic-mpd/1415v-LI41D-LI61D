package jsonParser.sampleTypes;

/**
 * Sample type with primitive fields used in {@link jsonParser.JsonParserTests}
 */
public class BagOfPrimitives {
    private int value1;
    private String value2;
    private double value3;
    private boolean value4;

    public BagOfPrimitives() {
        // no-args constructor
    }

    public int getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

    public double getValue3() {
        return value3;
    }

    public boolean getValue4() { return value4; }
}
