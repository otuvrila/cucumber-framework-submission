@ecommerce @authentication
Feature: User login and logout

  Background:
    Given the e-commerce application is available

  Scenario Outline: Login with different credentials
    When I login with username "<username>" and password "<password>"
    Then the login result should be "<result>"

    @positive
    Examples:
      | username | password | result  |
      | alice    | password | success |
      | bob      | password | success |

    @negative
    Examples:
      | username | password | result  |
      | alice    | wrong    | failure |
      | unknown  | password | failure |

  @positive
  Scenario: User logs out successfully
    Given I am logged in as "alice"
    When I am logged out
    Then I should be logged out