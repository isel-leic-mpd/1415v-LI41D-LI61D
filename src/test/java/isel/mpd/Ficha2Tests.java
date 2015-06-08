package isel.mpd;

import ficha2.Customer;
import ficha2.Order;
import ficha2.OrdersRepository;
import ficha2.Product;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * Created by lfalcao on 03/06/2015.
 */
public class Ficha2Tests {

    private Stream<Order> orders;

    @BeforeClass
    public void beforeClass() throws Exception {
        orders = OrdersRepository.getOrders();

    }



    @Test
    public void test2_1() throws Exception {
        double totalOrders = orders
                .mapToDouble(Order::getTotal)
                .reduce(0, (acc, t) -> acc + t);


        double totalOrders1 = orders
                .mapToDouble(Order::getTotal).sum();
    }

    public void test2_2() throws Exception {
        Stream<Order> sortedOrders = orders
                .filter(o -> o.getYear() >= 2012 && o.getYear() <= 2015)
                .sorted(comparing(Order::getTotal));
    }

    public void test2_3() throws Exception {
        Stream<String> sortedOrders = orders
                .map(o -> o.getCustomer().getCountry())
                .distinct();
    }

    class ProductsSales {

        private Product name;
        private long numSales;
        private double totalSales;

        public ProductsSales(Product name, long numSales, double totalSales) {

            this.name = name;
            this.numSales = numSales;
            this.totalSales = totalSales;
        }

        public Product getName() {
            return name;
        }

        public long getNumSales() {
            return numSales;
        }

        public void setNumSales(long numSales) {
            this.numSales = numSales;
        }

        public double getTotalSales() {
            return totalSales;
        }

        public void setTotalSales(double totalSales) {
            this.totalSales = totalSales;
        }
    }

    public void test2_4() throws Exception {
        final Map<Product, Long> productSales = orders.flatMap(o -> o.getOrderLines())
                .collect(groupingBy(Order.OrderLine::getProduct,
                        counting()
                ));

        final Stream<ProductsSales> productsSalesStream = productSales.entrySet().stream().map(e -> new ProductsSales(e.getKey(), e.getValue(), e.getValue() * e.getKey().getPrice()));

    }


    public void test2_5() throws Exception {
        final Optional<Order> maxOrder = orders.max(comparing(Order::getTotal));

    }


    public void test2_7() throws Exception {

    }

























}
