package dev.sophea.orderservice.api.order;

import dev.sophea.orderservice.api.client.OpenFeignAPIClient;
import dev.sophea.orderservice.api.client.ProductDto;
import dev.sophea.orderservice.api.order.web.CreateOrderDto;
import dev.sophea.orderservice.api.order.web.OrderDto;
import dev.sophea.orderservice.api.order.web.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OpenFeignAPIClient openFeignAPIClient;
    private final OrderMapStruct orderMapStruct;

    @Override
    public void createOrder(CreateOrderDto createOrderDto) {

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .description(createOrderDto.description())
                .remark(createOrderDto.remark())
                .orderDate(LocalDateTime.now())
                .build();

        orderRepository.save(order);

    }

    @Override
    public ResponseDto getOrderById(Long id) {

        Order order = orderRepository.findById(id).get();

        OrderDto orderDto = orderMapStruct.orderDtoToOrder(order);

        ProductDto productDto = openFeignAPIClient.findProductById(order.getId());

        ResponseDto responseDto = ResponseDto.builder()
                .orderDto(orderDto)
                .productDto(productDto)
                .build();

        return responseDto;
    }

    @Override
    public OrderDto getOrderAndProductById(Long id) {

        Order order = orderRepository.findById(id).get();
        ProductDto productDto = openFeignAPIClient.findProductById(order.getId());

        OrderDto orderDto = OrderDto.builder()
                .uuid(order.getUuid())
                .remark(order.getRemark())
                .description(order.getDescription())
                .orderDate(order.getOrderDate())
                .productDtoList(Collections.singletonList(productDto))
                .build();

        return orderDto;
    }

}
