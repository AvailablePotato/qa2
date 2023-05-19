import org.example.components.OrderComponent;
import org.example.components.ProductComponent;
import org.example.components.UserComponent;
import org.example.entity.Product;
import org.example.entity.User;
import org.example.enums.ProductType;
import org.example.repositories.OrderRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class OrderComponentUnitTest extends AbstractTest {
    @Mock
    UserComponent userComponent;

    @Mock
    ProductComponent productComponent;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    OrderComponent orderComponent;

    @Test
    void createOrderUnitTest() {
        var user = new User();
        var userName = "Vova";
        var userPhone = "+79632281337";
        var productName = "milk";
        var productPrice = 100;

        user.setPhone(userPhone);
        user.setName(userName);
        user.setId(1L);

        var product = new Product();
        product.setId(1L);
        product.setName(productName);
        product.setPrice(productPrice);
        product.setProductType(ProductType.GOOD);
        product.setRemainder(1);

        Mockito.when(userComponent.getOrCreateUser(userName, userPhone)).thenReturn(user);
        Mockito.when(productComponent.getProductByName(productName)).thenReturn(product);

        orderComponent.createOrder(userName, userPhone, productName);

        Mockito.verify(orderRepository, Mockito.times(1)).save(any());
    }

    @Test
    void createOrderUnitTestB() {
        var user = new User();
        var userName = "Vova";
        var userPhone = "+79632281337";
        var productName = "milk";
        var productPrice = 100;

        user.setPhone(userPhone);
        user.setName(userName);
        user.setId(1L);

        var product = new Product();
        product.setId(1L);
        product.setName(productName);
        product.setPrice(productPrice);
        product.setProductType(ProductType.GOOD);
        product.setRemainder(1);

        //Mockito.when(userComponent.getOrCreateUser(userName, userPhone)).thenReturn(user);
        Mockito.when(userComponent.getOrCreateUser(userName, userPhone)).thenThrow(new NullPointerException());
        //Mockito.when(productComponent.getProductByName(productName)).thenReturn(product);

        //orderComponent.createOrder(userName, userPhone, productName);

        assertThrows(NullPointerException.class, () -> orderComponent.createOrder(userName, userPhone, productName) );

        //Mockito.verify(orderRepository, Mockito.times(1)).save(any());
    }

}
