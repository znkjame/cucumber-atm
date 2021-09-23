Feature: deposit
  As a customer
  I want to deposit money to my account using ATM

Background:
  Given a customer with id 1 and pin 111 with balance 200 exists
  And I login to ATM with id 1 and pin 111

  Scenario: deposit amount
    When I deposit 1000 to ATM
    Then My account balance is 1200