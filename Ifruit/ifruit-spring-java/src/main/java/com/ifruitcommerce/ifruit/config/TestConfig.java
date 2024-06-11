package com.ifruitcommerce.ifruit.config;

import com.ifruitcommerce.ifruit.entities.*;
import com.ifruitcommerce.ifruit.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Date;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create Users
        User u1 = new User(null, "Alice", "alice@example.com", "11111111111", "alice123");
        User u2 = new User(null, "Bob", "bob@example.com", "22222222222", "bob456");
        User u3 = new User(null, "Charlie", "charlie@example.com", "33333333333", "charlie789");

        userRepository.saveAll(Arrays.asList(u1, u2, u3));

        // Create Products
        Product apple = new Product(null, "Apple", "Fresh Red Apple", 1.50, "apple.jpg");
        Product orange = new Product(null, "Orange", "Juicy Orange", 1.20, "orange.jpg");
        Product banana = new Product(null, "Banana", "Ripe Banana", 2.00, "banana.jpg");

        productRepository.saveAll(Arrays.asList(apple, orange, banana));

        // Create Categories
        Category fruit = new Category(null, "Fruit");
        Category citrus = new Category(null, "Citrus");

        categoryRepository.saveAll(Arrays.asList(fruit, citrus));

        // Set Product Categories
        apple.setCategories(Arrays.asList(fruit));
        orange.setCategories(Arrays.asList(fruit, citrus));
        banana.setCategories(Arrays.asList(fruit));

        productRepository.saveAll(Arrays.asList(apple, orange, banana));

        // Create Orders
        Order o1 = new Order(null, new Date(), OrderStatus.WAITING_PAYMENT, u1);
        Order o2 = new Order(null, new Date(), OrderStatus.PAID, u2);
        Order o3 = new Order(null, new Date(), OrderStatus.SHIPPED, u3);

        orderRepository.saveAll(Arrays.asList(o1, o2, o3));

        // Create Order Items
        OrderItem oi1 = new OrderItem(null, 3, apple.getPrice(), o1, apple);
        OrderItem oi2 = new OrderItem(null, 2, orange.getPrice(), o1, orange);
        OrderItem oi3 = new OrderItem(null, 5, banana.getPrice(), o2, banana);
        OrderItem oi4 = new OrderItem(null, 1, apple.getPrice(), o3, apple);

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

        // Create Payments
        Payment pay1 = new Payment(null, new Date(), o2);
        Payment pay2 = new Payment(null, new Date(), o3);

        o2.setPayment(pay1);
        o3.setPayment(pay2);

        orderRepository.saveAll(Arrays.asList(o2, o3));
    }
}