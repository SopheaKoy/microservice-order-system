package dev.sophea.productservice.api.product.web;

import lombok.Builder;

@Builder
public record ProductDto(Long id,String uuid, String name , String description , Double price , Boolean status) {
}
