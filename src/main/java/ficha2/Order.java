package ficha2;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by lfalcao on 03/06/2015.
 */
public class Order {
    private double total;
    private Customer customer;
    private List<OrderLine> orderLines;
    private int year;

    public Customer getCustomer() {
        return customer;
    }

    public Stream<OrderLine> getOrderLines() {
        return orderLines.stream();
    }


    public double getTotal() {
        return total;
    }

    public int getYear() {
        return year;
    }

    public class OrderLine {
        private Product product;
        private int quantity;

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
