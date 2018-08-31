package csku.atm;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefATM {

    ATM atm;
    Bank bank;
    boolean validLogin;

    @Before
    public void init() {
        bank = new Bank();
        atm = new ATM(bank);
    }

    @Given("a customer with id (\\d+) and pin (\\d+) exists")
    public void a_customer_with_id_and_pin_exists(int id, int pin) {
        bank.addCustomer(new Customer(id, pin));
    }

    @Given("a customer with id (\\d+) and pin (\\d+) with balance (.*) exists")
    public void a_customer_with_id_and_pin_with_balance_exists(int id, int pin, double balance) {
        bank.addCustomer(new Customer(id, pin, balance));
    }

    @When("I login to ATM with id (\\d+) and pin (\\d+)")
    public void i_login_to_ATM_with_id_and_pin(int id, int pin) {
        validLogin = atm.validateCustomer(id, pin);
    }

    @Then("I can login")
    public void i_can_login() {
        assertTrue(validLogin);
    }

    @Then("I cannot login")
    public void i_cannot_login() {
        assertFalse(validLogin);
    }

    @When("I withdraw (.*) from ATM")
    public void i_withdraw_from_atm(double amount) throws NotEnoughBalanceException {
        atm.withdraw(amount);
    }

    @When("I overdraw (.*) from ATM")
    public void i_withdraw_from_atm_more_than_balance(double amount) throws NotEnoughBalanceException {
        assertThrows(NotEnoughBalanceException.class,
                () -> atm.withdraw(amount));
    }
    @Then("my account balance is (.*)")
    public void my_account_balance_is(double balance) {
        assertEquals(balance, atm.getBalance());
    }

    @When("I transfer (.*) to customer id (\\d+)")
    public void i_transfer_to_customer_id(double amount, int toId) throws NotEnoughBalanceException {
        atm.transfer(toId, amount);
    }

    @Then("customer id (\\d+) account balance is (.*)")
    public void customer_id_account_balance_is(int id, double balance) {
        assertEquals(balance,
                     bank.findCustomer(id).getAccount().getBalance());
    }

}
