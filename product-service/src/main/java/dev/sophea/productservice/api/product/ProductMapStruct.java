package dev.sophea.productservice.api.product;

import dev.sophea.productservice.api.product.web.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapStruct {

    Product productToProductDto(ProductDto productDto);

    ProductDto productDtoToProduct(Product product);

    List<Product> productToListProduct(List<ProductDto> productDtoList);

    List<ProductDto> productDtoToListProduct(List<Product> productList);
}
