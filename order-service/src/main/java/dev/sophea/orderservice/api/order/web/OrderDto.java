package dev.sophea.orderservice.api.order.web;

import dev.sophea.orderservice.api.client.ProductDto;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDto(String uuid,
                       String description,
                       String remark,
                       LocalDateTime orderDate ,
                       List<ProductDto> productDtoList) {
}
