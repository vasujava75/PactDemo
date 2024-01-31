package org.consumer.client;

import org.consumer.model.Product;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "/products", accept = "application/json", contentType = "application/json")
public interface ProductClient {

    @GetExchange
    List<Product> getAllProducts();

}
