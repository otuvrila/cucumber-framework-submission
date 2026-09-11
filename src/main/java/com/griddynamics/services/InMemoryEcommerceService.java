package com.griddynamics.services;

import com.griddynamics.models.Order;
import com.griddynamics.models.OrderItem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryEcommerceService {

    private final Map<String, String> users = Map.of(
            "alice", "password",
            "bob", "password"
    );

    private final Map<String, Integer> stock = new HashMap<>(Map.of(
            "Laptop", 10,
            "Mouse", 50,
            "Keyboard", 20,
            "Gaming Console", 0,
            "Monitor", 15,
            "Headphones", 25
    ));

    private final List<String> products = List.of(
            "Laptop",
            "Mouse",
            "Keyboard",
            "Gaming Console",
            "Monitor",
            "Headphones"
    );

    public boolean isAvailable() {
        return true;
    }

    public boolean login(String username, String password) {
        return password.equals(users.get(username));
    }

    public List<String> search(String query) {
        return products.stream()
                .filter(p -> p.toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public void addToCart(String product, int quantity) {
        if (!isInStock(product, quantity)) {
            throw new IllegalStateException("Insufficient stock");
        }
    }

    public boolean isInStock(String product, int quantity) {
        return stock.getOrDefault(product, 0) >= quantity;
    }

    public void decreaseStock(String product, int quantity) {
        if (!isInStock(product, quantity)) {
            throw new IllegalStateException("Insufficient stock");
        }

        stock.computeIfPresent(product, (_, currentStock) -> currentStock - quantity);
    }

    public Order checkout(List<OrderItem> cart) {
        for (var item : cart) {
            if (!isInStock(item.productName(), item.quantity())) {
                throw new IllegalStateException("Insufficient stock");
            }
        }

        for (var item : cart) {
            decreaseStock(item.productName(), item.quantity());
        }

        return new Order(UUID.randomUUID(), new LinkedList<>(cart));
    }
}