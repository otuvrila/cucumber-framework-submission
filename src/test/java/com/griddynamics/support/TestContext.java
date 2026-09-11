package com.griddynamics.support;

import com.griddynamics.models.Order;
import com.griddynamics.models.OrderItem;
import com.griddynamics.services.InMemoryEcommerceService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TestContext {

    private String currentUser;
    private boolean loginSuccessful;
    private String lastError;

    private List<String> searchResults = new ArrayList<>();

    private final List<OrderItem> cart = new LinkedList<>();
    private List<OrderItem> orderConfirmation = new LinkedList<>();
    private UUID orderId;

    private final InMemoryEcommerceService service;

    public TestContext() {
        this.service = new InMemoryEcommerceService();
    }

    // -------------------------
    // Authentication
    // -------------------------

    public void login(String username, String password) {
        loginSuccessful = service.login(username, password);

        if (loginSuccessful) {
            currentUser = username;
            lastError = null;
        } else {
            currentUser = null;
            lastError = "Invalid credentials";
        }
    }

    public void logout() {
        currentUser = null;
        loginSuccessful = false;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public boolean isLoggedOut() {
        return currentUser == null;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    // -------------------------
    // Errors
    // -------------------------

    public String getLastError() {
        return lastError;
    }

    public void setError(String error) {
        this.lastError = error;
    }

    // -------------------------
    // Search
    // -------------------------

    public void search(String query) {
        searchResults = service.search(query);
    }

    public List<String> getSearchResults() {
        return searchResults;
    }

    // -------------------------
    // Application health
    // -------------------------

    public boolean isApplicationAvailable() {
        return service.isAvailable();
    }

    // -------------------------
    // Cart
    // -------------------------

    public void addToCart(String product, int quantity) {
        if (isLoggedOut()) {
            setError("User must be logged in");
            return;
        }

        try {
            service.addToCart(product, quantity);

            cart.stream()
                    .filter(item -> item.productName().equals(product))
                    .findFirst()
                    .ifPresentOrElse(
                            item -> {
                                cart.remove(item);
                                cart.add(new OrderItem(product, item.quantity() + quantity));
                            },
                            () -> cart.add(new OrderItem(product, quantity)));

            lastError = null;
        } catch (IllegalStateException e) {
            lastError = e.getMessage();
        }
    }

    public List<OrderItem> getCart() {
        return cart;
    }

    // -------------------------
    // Order
    // -------------------------

    public void checkout() {
        if (lastError != null) {
            return;
        }

        if (isLoggedOut()) {
            setError("User must be logged in");
            return;
        }

        try {
            Order order = service.checkout(cart);
            orderId = order.id();
            orderConfirmation = order.items();
            lastError = null;
        } catch (IllegalStateException e) {
            lastError = e.getMessage();
        }
    }

    public List<OrderItem> getOrderConfirmation() {
        return orderConfirmation;
    }

    public UUID getOrderId() {
        return orderId;
    }
}