package com.griddynamics.steps;

import com.griddynamics.support.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchSteps {

    private final TestContext context;

    public SearchSteps(TestContext context) {
        this.context = context;
    }

    @When("I search for {string}")
    public void iSearchFor(String query) {
        context.search(query);
    }

    @Then("the search results should contain {string}")
    public void theSearchResultsShouldContain(String product) {
        assertTrue(context.getSearchResults().contains(product),
                "Search results should contain: " + product);
    }

    @Then("the search results should be empty")
    public void theSearchResultsShouldBeEmpty() {
        assertTrue(context.getSearchResults().isEmpty(),
                "Search results should be empty");
    }
}
