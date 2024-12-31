package com.ecom.ordreservice.web;

import com.ecom.ordreservice.entites.Order;
import com.ecom.ordreservice.repository.OrderRepository;
import com.ecom.ordreservice.restclients.InventoryRestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrdersRestController {

    private OrderRepository orderRepository;
    private InventoryRestClient inventoryRestClient;

    public OrdersRestController(OrderRepository orderRepository, InventoryRestClient inventoryRestClient) {
        this.orderRepository = orderRepository;
        this.inventoryRestClient = inventoryRestClient;
    }
@GetMapping("/orders")
    public List<Order> findAllOrders(){
        //return orderRepository.findAll();
    List<Order> allOrders = orderRepository.findAll();
//    return allOrders;
    allOrders.forEach(o->{
        o.getProductItems().forEach(pi->{
            pi.setProduct(inventoryRestClient.findProductById(pi.getProductId()));
        });
    });
    return allOrders;
    }
    @GetMapping("/orders/{id}")
    public Order findOrderById(@PathVariable String id) {
      //  return orderRepository.findById(id).get();
        Order order = orderRepository.findById(id).get();
        order.getProductItems().forEach(pi->{
            pi.setProduct(inventoryRestClient.findProductById(pi.getProductId()));
        });
        return order;
   }

}
