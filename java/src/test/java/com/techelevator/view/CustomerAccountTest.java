package com.techelevator.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CustomerAccountTest {

    private CustomerAccount customerAccountSut;
    private VendingMachine vendingMachineSut;

    @Before
    public void setup() throws VendingMachineException {
        customerAccountSut = new CustomerAccount();
        vendingMachineSut = new VendingMachine();
    }

    @Test
    public void subtractMoney_subtracts_from_currentBalance() {
        customerAccountSut.depositMoney(10);
        customerAccountSut.subtractMoney("2.00");
        BigDecimal expected = new BigDecimal("8.00");

        Assert.assertEquals(expected, customerAccountSut.getCurrentBalance());
    }

    @Test
    public void makeChange_returns_correct_change_whole_numbers(){
        customerAccountSut.depositMoney(8);
        String expected = "Your change is 32 quarter(s), 0 dime(s), and 0 nickels.";
        Assert.assertEquals(expected,customerAccountSut.makeChange());
    }

    @Test
    public void makeChange_returns_correct_change_decimal(){
        vendingMachineSut.getCustomerAccount().depositMoney(8.00);
        vendingMachineSut.purchaseSuccessful("A1");
        String expected = "Your change is 19 quarter(s), 2 dime(s), and 0 nickels.";
        Assert.assertEquals(expected, vendingMachineSut.getCustomerAccount().makeChange());
    }

    @Test
    public void addToCashBox(){
        vendingMachineSut.getCustomerAccount().depositMoney(10.00);
        vendingMachineSut.purchaseSuccessful("A1");
        vendingMachineSut.purchaseSuccessful("A1");
        BigDecimal expected = new BigDecimal("6.10");
        Assert.assertEquals(expected, vendingMachineSut.getCustomerAccount().getCashBox());
    }

}
