package com.ecom.ordreservice;

import com.ecom.ordreservice.entites.Order;
import com.ecom.ordreservice.entites.OrderState;
import com.ecom.ordreservice.entites.ProductItem;
import com.ecom.ordreservice.model.Product;
import com.ecom.ordreservice.repository.OrderRepository;
import com.ecom.ordreservice.repository.ProductItemRepository;
import com.ecom.ordreservice.restclients.InventoryRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ecom.ordreservice.restclients")

public class OrdreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdreServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(
            OrderRepository orderRepository,
            ProductItemRepository productItemRepository
         //InventoryRestClient inventoryRestClient
    ){
        return args -> {
            //List<Product> allProducts = inventoryRestClient.getAllProducts();
            List<String> productsIds = List.of("P01","P02","P03");
            for (int i = 0; i < 5; i++) {
                Order order = Order.builder()
                        .id(UUID.randomUUID().toString())
                        .date(LocalDate.now())
                        .state(OrderState.PENDING)
                        .build();
                Order savedOrder = orderRepository.save(order);
                productsIds.forEach(p->{
                    ProductItem productItem = ProductItem.builder()
                            .productId(p)
                            .quantity(new Random().nextInt(20))
                            .price(Math.random()*6000+100)
                            .order(savedOrder)
                            .build();
                    productItemRepository.save(productItem);
                });

            }
        };
    }
}
