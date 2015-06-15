package isel.mpd.async;

import isel.mpd.Async.Discount;
import isel.mpd.Async.Quote;
import isel.mpd.misc.TestUtils;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lfalcao on 15/06/15.
 */
public class ShopWithDiscountTests extends BaseShopTests  {
    @Test
    public void shouldGetPricesWithDiscount() throws Exception {
        TestUtils.executeAndMeasurePerformance(this::getPricesWithDiscountSequantial, 1, "getPricesWithDiscountSequential");


    }

    private List<String> getPricesWithDiscountSequantial() {
        return shops.stream()
                .map(s -> s.getPriceStr(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }
}
