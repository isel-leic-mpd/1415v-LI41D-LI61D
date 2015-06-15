package isel.mpd.async;

import isel.mpd.Async.Shop;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lfalcao on 15/06/15.
 */
public class BaseShopTests {
    protected List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"), new Shop("BuyItAll"),
            new Shop("Glorious Prices"));

    protected String product = "iGadget64";
}
