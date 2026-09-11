package com.griddynamics.steps;

import com.griddynamics.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        context.login(username, password);
    }

    @Given("I am logged in as {string}")
    public void iAmLoggedInAs(String username) {
        context.login(username, "password");
        assertTrue(context.isLoginSuccessful(),
                "User should be logged in");
    }

    @Then("the login result should be {string}")
    public void theLoginResultShouldBe(String expectedResult) {
        if ("success".equals(expectedResult)) {
            assertTrue(context.isLoginSuccessful());
        } else if ("failure".equals(expectedResult)) {
            assertFalse(context.isLoginSuccessful());
        } else {
            fail("Unsupported login result: " + expectedResult);
        }
    }

    @Then("I should be logged out")
    public void iShouldBeLoggedOut() {
        assertFalse(context.isLoginSuccessful());
        assertNull(context.getCurrentUser());
    }

    @When("I am logged out")
    public void logout() {
        context.logout();
    }
}
