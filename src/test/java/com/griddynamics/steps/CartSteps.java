package com.griddynamics.steps;

import com.griddynamics.support.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {

    private final TestContext context;

    public CartSteps(TestContext context) {
        this.context = context;
    }

    @When("I add {string} to the cart")
    public void iAddToTheCart(String product) {
        context.addToCart(product, 1);
    }

    @Then("the cart should contain {string}")
    public void theCartShouldContain(String product) {
        assertTrue(
                context.getCart().stream().anyMatch(item -> item.productName().equals(product)),
                "Cart should contain: " + product
        );
    }
}