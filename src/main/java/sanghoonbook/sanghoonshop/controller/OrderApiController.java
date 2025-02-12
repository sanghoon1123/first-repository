package sanghoonbook.sanghoonshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sanghoonbook.sanghoonshop.domain.Order;
import sanghoonbook.sanghoonshop.domain.OrderSearch;
import sanghoonbook.sanghoonshop.domain.OrderStatus;
import sanghoonbook.sanghoonshop.domain.service.OrderService;
import sanghoonbook.sanghoonshop.dto.OrderDto;
import sanghoonbook.sanghoonshop.dto.OrderItemDto;
import sanghoonbook.sanghoonshop.dto.SimpleOrderDto;
import sanghoonbook.sanghoonshop.repository.OrderRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("/api/orderItem")
    public List<OrderDto> orders(){
        List<Order> orders = orderService.findOrders(new OrderSearch());
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());
        
        return result;
    }

    @GetMapping("/api/orderItem2")
    public List<OrderDto> orders2(@ModelAttribute OrderSearch orderSearch){
        List<Order> orders = orderService.findOrders(orderSearch);
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());

        return result;
    }

    @GetMapping("/api/orderItem3")
    public List<OrderDto> orders3(@RequestParam String name,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findOrdersByMemberName(name, pageable);
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());
        return result;

    }


    @GetMapping("/api/orderItem4")
    public List<OrderDto> orders3(@ModelAttribute OrderSearch orderSearch,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size){

        Page<Order> orders2 = orderService.findOrders2(orderSearch, page, size);
        List<OrderDto> result = orders2.stream().map(o -> new OrderDto(o)).collect(toList());
        return result;


    }

    @GetMapping("/api/orderItem5")
    public Page<OrderDto> orders4(@ModelAttribute OrderSearch orderSearch,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size){

        return orderService.findOrders3(orderSearch, page, size);



    }

    @PostMapping("/api/orderItem6")
    public Page<OrderDto> orders5(@RequestBody OrderSearch orderSearch,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size){

        Page<OrderDto> orders = orderService.findOrders3(orderSearch, page, size);
        return ResponseEntity.ok(orders).getBody();



    }
}
