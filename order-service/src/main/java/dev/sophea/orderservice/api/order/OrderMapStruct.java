package dev.sophea.orderservice.api.order;

import dev.sophea.orderservice.api.order.web.CreateOrderDto;
import dev.sophea.orderservice.api.order.web.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapStruct {

    Order orderToOrderDto(OrderDto orderDto);

    OrderDto orderDtoToOrder(Order model);

    CreateOrderDto createOrderDtoToOrder(Order model);

    List<OrderDto> listOrderDtoToListOrder(List<Order> model);
}
