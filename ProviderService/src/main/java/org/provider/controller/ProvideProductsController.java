package org.provider.controller;

import org.provider.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ProvideProductsController {
    @GetMapping("/products")
    List<Product> all() {
        Product p1= new Product(1,"Car","Driving");
        Product p2= new Product(2,"Flight","Flying");
        Product p3= new Product(3,"Boat","Sailing");
        return Stream.of(p1, p2,p3)
                .collect(Collectors.toList());
    }
}
