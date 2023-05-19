import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuccessCreateServeTest {

    @Test
    public void checkCreateServe() {
        //PRECONDITION
        var serveName = "spawn pizza right here next to me";
        var servePrice = 9000.01;
        var pathCreateServe = "/createServe?name=" + serveName + "&price=" + servePrice;
        var pathGetServe = "/byProductName?productName=" + serveName;
        var pathDeleteExistingServe = "/deleteProductById?id=";
        var responseFindServe = TestUtils.callGet(pathGetServe);
        if (responseFindServe.extract().statusCode() == 200 ) {
            var existingServeId = responseFindServe.extract().body().path("id");
            pathDeleteExistingServe += existingServeId;
            TestUtils.callDelete(pathDeleteExistingServe);
        }

        //TEST

        var responseCreateServe = TestUtils.callPut(pathCreateServe).assertThat().statusCode(200);
        var receivedServeId = responseCreateServe.extract().body().path("id");

        //asserts

        var receivedServeName = responseCreateServe.extract().body().path("name");
        var receivedServePrice = responseCreateServe.extract().body().path("price");
        var expectedServePrice = (float)servePrice;

        assertThat(receivedServeName).as("name").isEqualTo(serveName);
        assertThat(receivedServePrice).as("price").isEqualTo(expectedServePrice);

        //POSTCONDITION

        pathDeleteExistingServe = "/deleteProductById?id=" + receivedServeId;
        TestUtils.callDelete(pathDeleteExistingServe);

    }
}
