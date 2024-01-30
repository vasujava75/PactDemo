package org.consumer.controller;

import org.consumer.client.ProductClient;
import org.consumer.model.Car;
import org.consumer.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ConsumeProductsController {

@Autowired
    ProductClient productClient;
    @GetMapping("/deliverCars")
    List<Car> getAllCars(){
        List<Product> productList= productClient.getAllProducts();
       return productList.stream().filter(product->product.name().equals("Car")).map(product->{
            return new Car(product.name(),product.id().toString());
        }).collect(Collectors.toList());
    }

}
