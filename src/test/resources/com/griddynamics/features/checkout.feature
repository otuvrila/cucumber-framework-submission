@ecommerce @checkout
Feature: Checkout

  Background:
    Given the e-commerce application is available
    And I am logged in as "alice"

  @positive
  Scenario: Successfully checkout multiple products
    Given my cart contains:
      | product  | quantity |
      | Laptop   | 1        |
      | Mouse    | 2        |
      | Keyboard | 1        |
    When I checkout
    Then the order should be successfully created
    And I should see an order confirmation

  @negative
  Scenario: Checkout fails when stock is insufficient
    Given my cart contains:
      | product | quantity |
      | Laptop  | 100      |
    When I checkout
    Then the checkout should fail with "Insufficient stock"

  @positive
  Scenario: Order confirmation contains the purchased products
    Given my cart contains:
      | product | quantity |
      | Laptop  | 1        |
      | Mouse   | 1        |
    When I checkout
    Then the order confirmation should contain:
      | product | quantity |
      | Laptop  | 1        |
      | Mouse   | 1        |