package dev.sophea.orderservice.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service" , url = "http://localhost:8086/api/v1")
public interface OpenFeignAPIClient {


    // Configuration simple you don't register with Eureka Server

    @GetMapping("/products")
    public List<ProductDto> findAllProduct();

    @GetMapping("/products/{id}")
    public ProductDto findProductById(@PathVariable Long id);

}
