package isel.mpd.jsonzai;

import java.text.MessageFormat;

/**
 * Receives a Json string ad provides its Tokens each time getNextToken called.
 */
class TokenProvider {
    String jsonString;
    int idx = 0;

    public TokenProvider() {
    }

    public Token getNextToken(Class<?> t) throws Exception {
        skipSpaces();
        char c = jsonString.charAt(idx++);

        if(c == '{' || c == '}') {
            return createCurlyBracesToken(c);
        }

        if(c == '[' || c == ']') {
            return createSquareBracesToken(c);
        }

        if(c == ':') {
            return createColonToken(c);
        }

        if(c == ',') {
            return createCommaToken(c);
        }

        if(c == '"') {
            return createStringToken();
        }

        if(c == 't') {
            return createTrueToken();
        }

        if(c == 'f') {
            return createFalseToken();
        }

        if(c == 'n') {
            return createNullToken();
        }

        if(Character.isDigit(c)) {
            return createNumberToken();
        }

        throw new Exception(MessageFormat.format("Unexpected token at position {0} with char {1}", idx, c));
    }

    private Token createFalseToken() throws Exception {
        checkSubstringAndUpdateIndex("false");
        return BooleanToken.INSTANCE_FALSE;
    }

    private Token createTrueToken() throws Exception {
        checkSubstringAndUpdateIndex("true");
        return BooleanToken.INSTANCE_TRUE;
    }

    private Token createNullToken() throws Exception {
        checkSubstringAndUpdateIndex("null");
        return NullToken.INSTANCE;
    }

    private Token createNumberToken() {
        return new NumberToken(getNumber());
    }

    private Token createCommaToken(char c) {
        return CommaToken.INSTANCE;
    }
    private Token createColonToken(char c) {
        return ColonToken.INSTANCE;
    }
    public Token createCurlyBracesToken(char c) {
        return c == '{' ? CurlyBracketOpeningToken.INSTANCE : CurlyBracketClosingToken.INSTANCE;
    }

    private Token createSquareBracesToken(char c) {
        return c == '[' ? SquareBracketOpeningToken.INSTANCE : SquareBracketClosingToken.INSTANCE;
    }

    private Token createStringToken() {
        return new StringToken(getString());
    }

    private String getString() {
        int endIdx = idx;
        while(endIdx < jsonString.length()) {
            final char c = jsonString.charAt(endIdx++);
            if(c == '"') break;
            if(c == '\\') ++endIdx;
        }
        final String str = getSubstringAndUpdateIdx(endIdx-1);
        ++idx;
        return str;
    }

    private String getNumber() {
        int endIdx = idx--;
        while(endIdx < jsonString.length() && Character.isDigit(jsonString.charAt(endIdx)))
            ++endIdx;
        if(endIdx < jsonString.length() && jsonString.charAt(endIdx) == '.') {
            ++endIdx;
            while(endIdx < jsonString.length() && Character.isDigit(jsonString.charAt(endIdx)))
                ++endIdx;
        }
        return getSubstringAndUpdateIdx(endIdx);
    }

    private void checkSubstringAndUpdateIndex(String str) throws Exception {
        --idx;
        if(jsonString.substring(idx, idx+str.length()).equals(str)) {
            getSubstringAndUpdateIdx(idx+str.length());
            return;
        }
        throw new Exception(MessageFormat.format("Expected \'{0}\'", str));
    }

    private String getSubstringAndUpdateIdx(int endIdx) {
        String s = jsonString.substring(idx, endIdx);
        idx = endIdx;
        skipSpaces();
        return s;
    }


    private void skipSpaces() {
        while(idx < jsonString.length() && Character.isSpaceChar(jsonString.charAt(idx))) {
            ++idx;
        }
    }

    public void setString(String jsonStr) {
        idx = 0;
        jsonString = jsonStr;
    }

    static abstract class Token {
        public final String value;

        public Token(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class StringToken extends Token {
        public static final StringToken INSTANCE = new StringToken(null);
        public StringToken(String value) {
            super(value);
        }

        @Override
        public boolean equals(Object that) {
            return that != null && that.getClass() == StringToken.class;
        }

        @Override
        public int hashCode() {
            return StringToken.class.hashCode();
        }
    }

    public static class NumberToken extends Token {
        public static final NumberToken INSTANCE = new NumberToken(null);

        public NumberToken(String value) {
            super(value);
        }

        @Override
        public boolean equals(Object that) {
            return that != null && that.getClass() == NumberToken.class;
        }

        @Override
        public int hashCode() {
            return NumberToken.class.hashCode();
        }
    }

    public static class BooleanToken extends Token {
        public static final BooleanToken INSTANCE_TRUE = new BooleanToken("true");
        public static final BooleanToken INSTANCE_FALSE = new BooleanToken("false");
        private BooleanToken(String value) {
            super(value);
        }
    }

    public static class SquareBracketOpeningToken extends Token {
        public static SquareBracketOpeningToken INSTANCE = new SquareBracketOpeningToken();
        private SquareBracketOpeningToken() {
            super("[");
        }
    }

    public static class SquareBracketClosingToken extends Token {
        public static SquareBracketClosingToken INSTANCE = new SquareBracketClosingToken();
        private SquareBracketClosingToken() {
            super("]");
        }
    }

    public static class CurlyBracketOpeningToken extends Token {
        public static final CurlyBracketOpeningToken INSTANCE = new CurlyBracketOpeningToken();
        private CurlyBracketOpeningToken() {
            super("{");
        }
    }

    public static class CurlyBracketClosingToken extends Token {
        public static final CurlyBracketClosingToken INSTANCE = new CurlyBracketClosingToken();
        private CurlyBracketClosingToken() {
            super("}");
        }
    }

    public static class ColonToken extends Token {
        public static final ColonToken INSTANCE = new ColonToken();
        private ColonToken() { super(":"); }
    }

    public static class NullToken extends Token {
        public static final NullToken INSTANCE = new NullToken();
        private NullToken() {
            super("null");
        }
    }

    public static class CommaToken extends Token {
        public static final CommaToken INSTANCE = new CommaToken();
        private CommaToken() {
            super(",");
        }
    }
}
