package jsonParser.sampleTypes;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class AlbumsContainer {
    public String title;
    public String message;
    public List<String> errors = new ArrayList<>();
    public String total;
    public int total_pages;
    public int page;
    public String limit;
}
