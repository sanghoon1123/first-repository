package sanghoonbook.sanghoonshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import sanghoonbook.sanghoonshop.domain.Order;
import sanghoonbook.sanghoonshop.domain.OrderSearch;
import sanghoonbook.sanghoonshop.domain.service.OrderService;
import sanghoonbook.sanghoonshop.dto.SimpleOrderDto;
import sanghoonbook.sanghoonshop.repository.OrderRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {
    private final OrderService orderService;

    @GetMapping("/api/simple-orders")
    public List<SimpleOrderDto> orders(@ModelAttribute OrderSearch orderSearch){
        List<Order> orders = orderService.findOrders(orderSearch);
        List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(toList());
        return result;
    }


}
