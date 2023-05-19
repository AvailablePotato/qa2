import org.example.components.OrderComponent;
import org.example.components.ProductComponent;
import org.example.components.UserComponent;
import org.example.repositories.OrderRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OrderComponentTest extends AbstractTest {
    @Autowired
    UserComponent userComponent;

    @Autowired
    ProductComponent productComponent;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderComponent orderComponent;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({
            "Oleg, +7999", "Fedya, +7889"
        }
    )//or file @csvfilesource
    //enumsource(ptype.class) - for all enums
    //methodsource(mymeth)
    void createOrderTest(String userName, String userPhone) {
        //prec
        //var userName = "Vova";
        //var userName =
        System.out.println("USERNAME = " + userName);
        //var userPhone = "+79632281337";
        var productName = "milk";
        var productPrice = 100;

        var product = productComponent.addNewGoods(productName,productPrice);

        //TEST
        var createOrder = orderComponent.createOrder(userName, userPhone, productName);

        //assert
        var order = orderRepository.findById(createOrder.getId()).get();
        var actorId = order.getAuthorId();
        var productId = order.getProductId();
        var user = userRepository.findById(actorId);

        assertThat(user).isNotEmpty();
        assertThat(user.get().getName()).isEqualTo(userName);
        assertThat(user.get().getPhone()).isEqualTo(userPhone);
        assertThat(product.getId()).isEqualTo(productId);
    }

    @Test
    void errorWhenTryToCreateOrderWithoutProductTest() {
        var prodName = "pivo";
        var error = assertThrows(
                NoSuchElementException.class,
                () -> orderComponent.createOrder("Petya", "+7228", prodName)
        );
        assertThat(error.getMessage()).isEqualTo("Продукта с именем '%s' нет!", prodName);
    }


}
