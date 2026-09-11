@ecommerce @search
Feature: Product search

  Background:
    Given the e-commerce application is available

  @positive
  Scenario: Search using an exact product name
    When I search for "Laptop"
    Then the search results should contain "Laptop"

  @positive
  Scenario: Search using a partial product name
    When I search for "Lap"
    Then the search results should contain "Laptop"

  @negative
  Scenario: Search for a product that does not exist
    When I search for "Refrigerator"
    Then the search results should be empty