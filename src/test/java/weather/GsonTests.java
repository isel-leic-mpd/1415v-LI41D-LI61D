package weather;

import com.google.gson.Gson;
import isel.mpd.weather.WeatherData;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

/**
 * Created by lfalcao on 08/04/2015.
 */
public class GsonTests {
    @Test
    public void test_object_with_list() {
        String src = "{ \"data\" : [{\"maxtempC\": \"15\", \"maxtempF\": \"59\", "
                + "\"mintempC\": \"11\", \"mintempF\": \"52\", \"uvIndex\": \"0\" }]}";

        Gson gson = new Gson();
        WeatherData res = gson.fromJson(src, WeatherData.class);

        Assert.assertNotNull(res);
        Assert.assertEquals(15, res.getData().get(0).maxtempC);
        Assert.assertEquals(11, res.getData().get(0).mintempC);
    }


    @Test
    public void test_dump_field_types() {
        Class<WeatherData> wd = WeatherData.class;
        Arrays.stream(wd.getDeclaredFields()).forEach(GsonTests::showFieldInfo);
    }

    private static void showFieldInfo(Field field) {
        System.out.println("---------------------------");
        System.out.println("name: " + field.getName());
        System.out.println("type: " + field.getType().getName());
        System.out.println("type: " + field.getGenericType().getTypeName());
        if(field.getGenericType() != field.getType())
            System.out.println("generic type: " + Arrays.toString(((ParameterizedType) field.getGenericType()).getActualTypeArguments()));
    }


}
