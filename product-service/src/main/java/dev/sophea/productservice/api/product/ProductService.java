package dev.sophea.productservice.api.product;

import dev.sophea.productservice.api.product.web.CreateProductDto;
import dev.sophea.productservice.api.product.web.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProduct();

    Product saveProduct(CreateProductDto createProductDto);

}
