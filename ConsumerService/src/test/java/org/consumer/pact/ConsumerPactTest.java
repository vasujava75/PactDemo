package org.consumer.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.consumer.client.ProductClient;
import org.consumer.controller.ConsumeProductsController;
import org.consumer.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

import static java.util.Collections.singletonMap;


@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProviderService", pactVersion = PactSpecVersion.V3)
public class ConsumerPactTest {

    ConsumeProductsController consumeProductsController;

    @Pact(consumer = "ConsumerService")
    public RequestResponsePact pactProductsCall(PactDslWithProvider builder){
       return builder.given("products avilable")
                .uponReceiving("get all the proudcts")
                 .method("GET")
                .path("/products")
               .headers("Accept", "application/json")
                .willRespondWith()
                .status(200)
                .body(PactDslJsonArray.arrayMinLike(2)
                        .stringType("name")
                        .stringType("info")
                        .integerType("id"))
               .headers(singletonMap("Content-Type", "application/json"))
               .toPact();
    }

    @Test
    public void testCarConsumerWithPact(MockServer mockServer){
        var webClient = WebClient.builder().baseUrl(mockServer.getUrl()).build();
        var factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        var client = factory.createClient(ProductClient.class);
        consumeProductsController= new ConsumeProductsController();
        consumeProductsController.setProductClient(client);
        List<Car> carList= consumeProductsController.getAllCars();
    }

}
