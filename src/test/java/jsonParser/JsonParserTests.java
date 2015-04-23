package jsonParser;

import isel.mpd.jsonzai.JsonParser;
import jsonParser.sampleTypes.Album;
import jsonParser.sampleTypes.AlbumsContainer;
import jsonParser.sampleTypes.BagOfPrimitives;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**

 */
public class JsonParserTests {

    private static JsonParser jsonParser;

    @BeforeClass
    public static void setUpClass() throws Exception {
        jsonParser = new JsonParser();
    }

    @Test
    public void shouldDeserializeBooleanValues() throws Exception {
        String jsonStrTrue =  "true";
        String jsonStrFalse =  "false";

        boolean bt = jsonParser.toObject(jsonStrTrue, Boolean.TYPE);
        boolean bf = jsonParser.toObject(jsonStrFalse, Boolean.TYPE);

        assertTrue(bt);
        assertFalse(bf);
    }

    @Test
    public void shouldDeserializeNullValue() throws Exception {
        String jsonStr =  "null";

        Object o = jsonParser.toObject(jsonStr, Object.class);

        assertNull(o);
    }

    @Test
    public void shouldDeserializeANumber() throws Exception {
        String jsonStr =  "10";

        int i = jsonParser.toObject(jsonStr, Integer.TYPE);

        assertEquals(10, i);
    }

    @Test
    public void shouldDeserializeAString() throws Exception {
        String jsonStr1 =  "\"20\"";
        String jsonStr2 =  "\"slb\"";
        String jsonStr3 =  "\"\"";

        String s1 = jsonParser.toObject(jsonStr1, String.class);
        String s2 = jsonParser.toObject(jsonStr2, String.class);
        String s3 = jsonParser.toObject(jsonStr3, String.class);

        assertEquals("20", s1);
        assertEquals("slb", s2);
        assertEquals("", s3);
    }

    @Test
    public void shouldDeserializeBagOfPrimitives() throws Exception {
        String jsonStr =  "{\"value1\":1,\"value2\":\"abc\",\"value3\":3.2,\"value4\":true}";

        BagOfPrimitives bop = jsonParser.toObject(jsonStr, BagOfPrimitives.class);

        assertNotNull(bop);
        assertEquals(1, bop.getValue1());
        assertEquals("abc", bop.getValue2());
        assertEquals(3.2, bop.getValue3(), 0.00001);
        assertEquals(3.2, bop.getValue3(), 0.00001);
        assertTrue(bop.getValue4());
    }

    @Test
    public void shouldDeserializeAnAlbum() throws Exception {
        String jsonStr =  "{\"album_id\":\"10\", \"album_title\":\"Title\"}\n";

        Album a = jsonParser.toObject(jsonStr, Album.class);

        assertNotNull(a);
        assertNotNull(a.album_id);
        assertNotNull(a.album_title);

    }

    @Test
    public void shouldDeserializeAnAlbumsContainerWithNoAlbums() throws Exception {
        String jsonStr =  "{\"title\":\"Free Music Archive - Albums\",\"message\":\"\",\"errors\":[]," +
                "\"total\":\"11259\",\"total_pages\":2252,\"page\":1,\"limit\":\"5\"}";

        AlbumsContainer ac = jsonParser.toObject(jsonStr, AlbumsContainer.class);

        assertNotNull(ac);
        assertEquals("Free Music Archive - Albums", ac.title);
        assertEquals("", ac.message);
        assertNotNull(ac.errors);
        assertEquals(0, ac.errors.size());
        assertEquals("11259", ac.total);
        assertEquals(2252, ac.total_pages);
        assertEquals(1, ac.page);
        assertEquals("5", ac.limit);

    }
}
