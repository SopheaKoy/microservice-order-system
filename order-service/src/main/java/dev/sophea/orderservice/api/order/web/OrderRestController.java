package dev.sophea.orderservice.api.order.web;

import dev.sophea.orderservice.api.order.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderMapStruct orderMapStruct;

    @PostMapping
    public void saveOrder(@RequestBody CreateOrderDto createOrderDto){
        orderService.createOrder(createOrderDto);
    }

    @GetMapping
    public List<OrderDto> findAllOrder(){

        var listOrder = orderRepository.findAll();

        return orderMapStruct.listOrderDtoToListOrder(listOrder);
    }

    @GetMapping("/with-product/{id}")
    @CircuitBreaker(name = "order" , fallbackMethod = "fallbackMethod")
    public OrderDto findOrderAndProductById(@PathVariable Long id){

        return orderService.getOrderAndProductById(id);
    }

    public OrderDto fallbackMethod(Throwable throwable){
        return OrderDto.builder()
                .remark("Call fallback Method !!!!")
                .build();
    }


}
