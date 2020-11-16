package com.techelevator.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class VendingMachineTest {

    VendingMachine sut;

    @Before
    public void setup() throws VendingMachineException {
        sut = new VendingMachine();
        sut.getCustomerAccount().depositMoney(3);
        sut.getProductInfo();
        sut.createProduct();
    }

    @Test
    public void decline_purchase_insufficient_funds() {
        boolean result = sut.attemptPurchase("A1");
        Assert.assertEquals(false, result);
    }

    @Test
    public void accept_purchase_sufficient_funds() {
        boolean result = sut.attemptPurchase("D1");
        Assert.assertEquals(true, result);
    }

    @Test
    public void product_number_exists() {
        boolean result = sut.productNumberExists("A1");
        Assert.assertEquals(true, result);
    }

    @Test
    public void product_number_doesnt_exist() {
        boolean result = sut.productNumberExists("H1");
        Assert.assertEquals(false, result);
    }

    @Test
    public void quantity_changes_when_purchase_is_made() {
        sut.purchaseSuccessful("D1");
        sut.getCustomerAccount().depositMoney(10);
        sut.purchaseSuccessful("D1");
        sut.purchaseSuccessful("D1");
        sut.purchaseSuccessful("D1");
        sut.purchaseSuccessful("D1");
        boolean result = sut.quantityIsEnough("D1");
        Assert.assertEquals(false, result);
    }

}
