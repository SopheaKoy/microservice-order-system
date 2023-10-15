package dev.sophea.orderservice.api.order.web;

import dev.sophea.orderservice.api.client.ProductDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {

    private OrderDto orderDto;
    private ProductDto productDto;
}
