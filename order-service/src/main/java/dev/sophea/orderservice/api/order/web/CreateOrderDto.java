package dev.sophea.orderservice.api.order.web;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateOrderDto (String uuid,
                              String description,
                              String remark,
                              LocalDateTime orderDate){
}
