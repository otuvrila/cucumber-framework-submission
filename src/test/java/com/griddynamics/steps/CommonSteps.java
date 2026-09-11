package com.griddynamics.steps;

import com.griddynamics.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {

    private final TestContext context;

    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @Given("the e-commerce application is available")
    public void theEcommerceApplicationIsAvailable() {
        assertTrue(context.isApplicationAvailable(),
                "E-commerce application should be available");
    }

    @Then("I should see the error {string}")
    public void iShouldSeeTheError(String expectedError) {
        assertEquals(expectedError, context.getLastError());
    }
}
