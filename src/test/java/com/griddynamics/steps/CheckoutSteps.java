package com.griddynamics.steps;

import com.griddynamics.models.OrderItem;
import com.griddynamics.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CheckoutSteps {

    private final TestContext context;

    public CheckoutSteps(TestContext context) {
        this.context = context;
    }

    @Given("my cart contains:")
    public void myCartContains(List<OrderItem> items) {
        items.forEach(item -> context.addToCart(item.productName(), item.quantity()));
    }

    @When("I checkout")
    public void iCheckout() {
        context.checkout();
    }

    @Then("the order should be successfully created")
    public void theOrderShouldBeSuccessfullyCreated() {
        assertNotNull(
                context.getOrderId(),
                "Order ID should be generated"
        );
    }

    @Then("I should see an order confirmation")
    public void iShouldSeeAnOrderConfirmation() {
        assertNotNull(
                context.getOrderId(),
                "Order confirmation should have an order ID"
        );

        assertFalse(
                context.getOrderConfirmation().isEmpty(),
                "Order confirmation should contain products"
        );
    }

    @Then("the checkout should fail with {string}")
    public void theCheckoutShouldFailWith(String expectedError) {
        assertEquals(expectedError, context.getLastError());
    }

    @Then("the order confirmation should contain:")
    public void theOrderConfirmationShouldContain(List<OrderItem> expectedOrderItems) {
        List<OrderItem> actualOrderItems = context.getOrderConfirmation();

        assertEquals(
                expectedOrderItems, actualOrderItems,
                "Order confirmation should contain cart products"
        );
    }
}