package isel.mpd.jsonzai;

import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Function;

import static isel.mpd.jsonzai.TokenProvider.*;

/**
 * Instances of this class parse Json strings and returned filled Java objects and/or values
 */
public class JsonParser {
    TokenProvider tokenProvider;


    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v) throws Exception;
    }

    private static Map<Class<?>, List<Field>> typeFields = new HashMap<>();

    private static final Map<Class<?>, Function<String, Object>> primitiveNumbersConverters =
            new ImmutableMap.Builder<Class<?>, Function<String, Object>>()
                    .put(Integer.TYPE, Integer::parseInt)
                    .put(Integer.class, Integer::parseInt)
                    .put(Float.TYPE, Float::parseFloat)
                    .put(Double.TYPE, Double::parseDouble)
                    .build();

    private final Map<TokenProvider.Token, TriFunction<TokenProvider.Token, Class<?>, Field, Object>> parserStrategies =
            new ImmutableMap.Builder<TokenProvider.Token, TriFunction<TokenProvider.Token, Class<?>, Field, Object>>()
                    .put(BooleanToken.INSTANCE_TRUE, this::parseBoolean)
                    .put(BooleanToken.INSTANCE_FALSE, this::parseBoolean)
                    .put(NumberToken.INSTANCE, this::parseNumber)
                    .put(StringToken.INSTANCE, this::parseString)
                    .put(NullToken.INSTANCE, this::parseNull)
                    .put(CurlyBracketOpeningToken.INSTANCE, this::parseObject)
                    .put(SquareBracketOpeningToken.INSTANCE, this::parseArray)
                    .build();

    public JsonParser(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public JsonParser() {
        this.tokenProvider = new TokenProvider();
    }

    public <T> T toObject(String jsonStr, Class<T> t) throws Exception {
        tokenProvider.setString(jsonStr);
        Token token = tokenProvider.getNextToken(t);
        return (T)internalToObject(token, t, null);
    }

    private Object internalToObject(Token token, Class<?> t, Field f) throws Exception {
        return parserStrategies.get(token).apply(token, t, f);
    }

    private Object parseObject(Token token, Class<?> t, Field f) throws Exception{
        Object o = t.newInstance();
        while(true) {
            parseJsonNameValue(t, o);
            Token nextToken = tokenProvider.getNextToken(null);
            if (nextToken == CurlyBracketClosingToken.INSTANCE) {
                return o;
            }

            if (!(nextToken == CommaToken.INSTANCE)) {
                throw new Exception("Expected ',' or '}'");
            }
        }
    }

    private void parseJsonNameValue(Class<?> t, Object o) throws Exception {
        Token nameToken = tokenProvider.getNextToken(String.class);
        Token colonToken = tokenProvider.getNextToken(null);
        if (!(colonToken == ColonToken.INSTANCE)) {
            throw new Exception("Expected ':' and got " + colonToken.getValue());
        }

        Optional<Field> f = getField(t, nameToken.getValue());
        Class<?> fieldType = f.isPresent() ? f.get().getType() : Object.class;
        Object value = internalToObject(tokenProvider.getNextToken(fieldType), fieldType, f.orElse(null));
        if(f.isPresent()) {
            f.get().setAccessible(true);
            f.get().set(o, value);
        }
    }

    private Object parseArray(Token token, Class<?> t, Field f) throws Exception {
        List a = new ArrayList();
        Class<?> memberType = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
        token = tokenProvider.getNextToken(memberType);
        while(token != SquareBracketClosingToken.INSTANCE) {
            Object o = internalToObject(token, memberType, null);
            a.add(o);

            token = tokenProvider.getNextToken(memberType);
            if(token == SquareBracketClosingToken.INSTANCE) {
                break;
            }
            if(token != CommaToken.INSTANCE) {
                throw new Exception("Expected ',' or ']'");
            }
            token = tokenProvider.getNextToken(memberType);
        }
        return a;
    }

    private Object parseBoolean(Token token, Class<?> t, Field f) {
        return Boolean.parseBoolean(token.getValue());
    }

    private Object parseNull(Token token, Class<?> t, Field f) {
        return null;
    }

    private Object parseNumber(Token token, Class<?> t, Field f) {
        return primitiveNumbersConverters.get(t).apply(token.getValue());
    }

    private Object parseString(Token token, Class<?> t, Field f) {
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
