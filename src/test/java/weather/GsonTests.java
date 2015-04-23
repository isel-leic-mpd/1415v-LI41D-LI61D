package weather;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Assert.assertEquals(15, res.data.get(0).maxtempC);
        Assert.assertEquals(11, res.data.get(0).mintempC);
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
        System.out.println("generic type: " + Arrays.toString(((ParameterizedType) field.getGenericType()).getActualTypeArguments()));
    }


    class WeatherData {
        public ArrayList<WeatherDetails> data = new ArrayList<>();

        String d;
        int x;

        public WeatherData() {
            data = new ArrayList<>();
        }
//    public WeatherData(List<WeatherDetails> data) {
//        this.data = data;
//    }
    }

    class WeatherDetails {
        public int maxtempC;
        public int mintempC;

        public WeatherDetails() {
        }
    }
}
