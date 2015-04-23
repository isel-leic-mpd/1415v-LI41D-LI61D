package isel.mpd.jsonzai;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 */
public class JsonParserWithGson {
    private Gson gson = new Gson();
    public <T> T toObject(String jsonStr, Class<T> t) {
        return gson.fromJson(jsonStr, t);
    }

}
