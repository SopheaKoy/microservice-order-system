package dev.sophea.productservice.api.product.web;

import dev.sophea.productservice.api.product.Product;
import dev.sophea.productservice.api.product.ProductRepository;
import dev.sophea.productservice.api.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping
    public List<ProductDto> findAll(){
        return productService.getAllProduct();
    }

    @PostMapping
    public void createProduct(@RequestBody CreateProductDto createProductDto){
        productService.saveProduct(createProductDto);
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow();
    }
}
