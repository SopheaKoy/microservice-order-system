package dev.sophea.productservice.api.product;

import dev.sophea.productservice.api.product.web.CreateProductDto;
import dev.sophea.productservice.api.product.web.ProductController;
import dev.sophea.productservice.api.product.web.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapStruct productMapStruct;

    @Override
    public List<ProductDto> getAllProduct() {

        var listProduct = productRepository.findAll();

        return productMapStruct.productDtoToListProduct(listProduct);
    }

    @Override
    public Product saveProduct(CreateProductDto createProductDto) {

        Product product = Product.builder()
                .uuid(UUID.randomUUID().toString())
                .name(createProductDto.name())
                .description(createProductDto.description())
                .price(createProductDto.price())
                .status(createProductDto.status())
                .build();

        return productRepository.save(product);
    }
}
