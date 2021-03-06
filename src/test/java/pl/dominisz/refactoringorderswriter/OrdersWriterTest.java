package pl.dominisz.refactoringorderswriter;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class OrdersWriterTest {
    Orders orders = new Orders();
    Order order111 = new Order(111);

    @Before
    public void SetupOneOrder() {
        orders.addOrder(order111);
    }

    @Test
    public void NoOrder() {
        assertEquals("{\"orders\": []}", new OrdersWriter(new Orders()).getContents());
    }

    @Test
    public void OneOrder() {
        String order111 = "{\"id\": 111, \"products\": []}";
        assertEquals("{\"orders\": [" + order111 + "]}", new OrdersWriter(orders).getContents());
    }

    @Test
    public void TwoOrders() {
        orders.addOrder(new Order(222));

        String order111Json = JsonOrder111WithProduct("");
        String order222Json = "{\"id\": 222, \"products\": []}";
        assertEquals("{\"orders\": [" + order111Json + ", " + order222Json + "]}", new OrdersWriter(orders).getContents());
    }

    @Test
    public void OneOrderWithOneProduct() {
        order111.addProduct(new Product("Shirt", 1, 3, new BigDecimal("2.99"), Currency.getInstance("TWD")));

        String order111Json = JsonOrder111WithProduct("{\"code\": \"Shirt\", \"color\": \"blue\", \"size\": \"M\", \"price\": 2.99, \"currency\": \"TWD\"}");
        assertEquals("{\"orders\": [" + order111Json + "]}", new OrdersWriter(orders).getContents());
    }

    @Test
    public void OneOrderWithOneProductNoSize() {
        order111.addProduct(new Product("Pot", 2, -1, new BigDecimal("16.50"), Currency.getInstance("SGD")));

        String order111Json = JsonOrder111WithProduct("{\"code\": \"Pot\", \"color\": \"red\", \"price\": 16.50, \"currency\": \"SGD\"}");
        assertEquals("{\"orders\": [" + order111Json + "]}", new OrdersWriter(orders).getContents());
    }

    private String JsonOrder111WithProduct(String productJson) {
        return "{\"id\": 111, \"products\": [" + productJson + "]}";
    }
}
