package dev.sophea.orderservice.api.order;

import dev.sophea.orderservice.api.order.web.CreateOrderDto;
import dev.sophea.orderservice.api.order.web.OrderDto;
import dev.sophea.orderservice.api.order.web.ResponseDto;

import java.util.List;

public interface OrderService {

    void createOrder(CreateOrderDto createOrderDto);

    ResponseDto getOrderById(Long id);

    OrderDto getOrderAndProductById(Long id);
}
