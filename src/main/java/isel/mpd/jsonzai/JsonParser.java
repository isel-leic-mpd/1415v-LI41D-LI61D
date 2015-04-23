package isel.mpd.jsonzai;

import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * Instances of this class parse Json strings and returned filled Java objects and/or values
 */
public class JsonParser {
    TokenProvider tokenProvider;


    public interface BiFunction<T, U, R> {
        R apply(T t, U u) throws Exception;
    }

    private static Map<Class<?>, List<Field>> typeFields = new HashMap<>();

    private static final Map<Class<?>, Function<String, Object>> primitiveNumbersConverters =
            new ImmutableMap.Builder<Class<?>, Function<String, Object>>()
                    .put(Integer.TYPE, Integer::parseInt)
                    .put(Integer.class, Integer::parseInt)
                    .put(Float.TYPE, Float::parseFloat)
                    .put(Double.TYPE, Double::parseDouble)
                    .build();

    private final Map<TokenProvider.Token, BiFunction<TokenProvider.Token, Class<?>, Object>> parserStrategies =
            new ImmutableMap.Builder<TokenProvider.Token, BiFunction<TokenProvider.Token, Class<?>, Object>>()
                    .put(TokenProvider.BooleanToken.INSTANCE_TRUE, this::parseBoolean)
                    .put(TokenProvider.BooleanToken.INSTANCE_FALSE, this::parseBoolean)
                    .put(TokenProvider.NumberToken.INSTANCE, this::parseNumber)
                    .put(TokenProvider.StringToken.INSTANCE, this::parseString)
                    .put(TokenProvider.NullToken.INSTANCE, this::parseNull)
                    .put(TokenProvider.CurlyBracketOpeningToken.INSTANCE, this::parseObject)
                    .put(TokenProvider.SquareBracketOpeningToken.INSTANCE, this::parseArray)
                    .build();

    public JsonParser(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public JsonParser() {
        this.tokenProvider = new TokenProvider();
    }

    public <T> T toObject(String jsonStr, Class<T> t) throws Exception {
        tokenProvider.setString(jsonStr);
        return (T)internalToObject(t);
    }

    private Object internalToObject(Class<?> t) throws Exception {
        TokenProvider.Token token = tokenProvider.getNextToken(t);
        return parserStrategies.get(token).apply(token, t);
    }

    private Object parseObject(TokenProvider.Token token, Class<?> t) throws Exception{
        Object o = t.newInstance();
        while(true) {
            parseJsonNameValue(t, o);
            TokenProvider.Token nextToken = tokenProvider.getNextToken(null);
            if (nextToken == TokenProvider.CurlyBracketClosingToken.INSTANCE) {
                return o;
            }

            if (!(nextToken == TokenProvider.CommaToken.INSTANCE)) {
                throw new Exception("Expected ',' or '}'");
            }
        }
    }

    private void parseJsonNameValue(Class<?> t, Object o) throws Exception {
        TokenProvider.Token nameToken = tokenProvider.getNextToken(String.class);
        TokenProvider.Token colonToken = tokenProvider.getNextToken(null);
        if (!(colonToken == TokenProvider.ColonToken.INSTANCE)) {
            throw new Exception("Expected ':'");
        }

        Optional<Field> f = getField(t, nameToken.getValue());
        Class<?> fieldType = f.isPresent() ? f.get().getType() : Object.class;
        Object value = internalToObject(fieldType);
        if(f.isPresent()) {
            f.get().setAccessible(true);
            f.get().set(o, value);
        }
    }

    private Object parseArray(TokenProvider.Token token, Class<?> t) {
        return null;
    }

    private Object parseBoolean(TokenProvider.Token token, Class<?> t) {
        return Boolean.parseBoolean(token.getValue());
    }

    private Object parseNull(TokenProvider.Token token, Class<?> t) {
        return null;
    }

    private Object parseNumber(TokenProvider.Token token, Class<?> t) {
        return primitiveNumbersConverters.get(t).apply(token.getValue());
    }

    private Object parseString(TokenProvider.Token token, Class<?> t) {
        return token.getValue();
    }

    private  Optional<Field> getField(Class<?> t, String fieldName) {
        List<Field> fields = typeFields.get(t);
        if(fields == null) {
            typeFields.put(t, fields = getFieldsForType(t));
        }
        return fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst();
    }

    private List<Field> getFieldsForType(Class<?> t) {
        ArrayList<Field> fields = new ArrayList<>();
        while(t != Object.class) {
            fields.addAll(Arrays.asList(t.getDeclaredFields()));
            t = t.getSuperclass();
        }
        return fields;
    }



}
