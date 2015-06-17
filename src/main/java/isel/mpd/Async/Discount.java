package isel.mpd.Async;

/**
 * Created by lfalcao on 15/06/15.
 */
public class Discount extends FakeService {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote);

    }

    private static String apply(Quote quote) {
        double price = quote.getPrice();
        Code discountCode = quote.getDiscountCode();
        delay("Discount for " + quote.getShopName());
        //System.out.println("applyDiscount " + System.currentTimeMillis());
        return String.valueOf(price - price * discountCode.percentage / 100);
    }
}