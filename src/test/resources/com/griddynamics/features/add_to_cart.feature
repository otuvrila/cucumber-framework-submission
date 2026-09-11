@ecommerce @cart
Feature: Add products to cart

  Background:
    Given the e-commerce application is available
    And I am logged in as "alice"

  @positive
  Scenario: Logged-in user adds an available product to the cart
    When I add "Laptop" to the cart
    Then the cart should contain "Laptop"

  @negative
  Scenario: User cannot add an out-of-stock product
    When I add "Gaming Console" to the cart
    Then I should see the error "Insufficient stock"

  @negative
  Scenario: Anonymous user cannot add products to the cart
    Given I am logged out
    When I add "Laptop" to the cart
    Then I should see the error "User must be logged in"