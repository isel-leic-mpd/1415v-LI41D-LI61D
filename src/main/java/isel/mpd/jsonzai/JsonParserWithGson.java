package isel.mpd.jsonzai;

import com.google.gson.Gson;

/**
 */
public class JsonParserWithGson {
    private Gson gson = new Gson();
    public <T> T toObject(String jsonStr, Class<T> t) {
        return gson.fromJson(jsonStr, t);
    }

}
