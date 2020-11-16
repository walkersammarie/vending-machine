package com.techelevator.view;

import com.techelevator.VMBusiness;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomerAccount {

    private BigDecimal currentBalance;
    private BigDecimal cashBox = new BigDecimal("0.00");


    public CustomerAccount() {
        currentBalance = new BigDecimal("0.00");
    }

    public void depositMoney(double deposit) {
        BigDecimal myDeposit = BigDecimal.valueOf(deposit);
        myDeposit = myDeposit.setScale(2, RoundingMode.CEILING);
        currentBalance = currentBalance.add(myDeposit);
        VMBusiness.logDeposit(currentBalance, myDeposit);
    }

    public void subtractMoney(String price) {
        BigDecimal myDeposit = new BigDecimal(price);
        myDeposit = myDeposit.setScale(2, RoundingMode.CEILING);
        currentBalance = currentBalance.subtract(myDeposit);
        addToCashBox(myDeposit);
    }

    public String makeChange() {

        VMBusiness.logChange(currentBalance);

        int nickelCounter = 0;
        int dimeCounter = 0;
        int quarterCounter = 0;

        BigDecimal nickels = new BigDecimal(".05");
        BigDecimal dimes = new BigDecimal(".10");
        BigDecimal quarters = new BigDecimal(".25");

        while (currentBalance.compareTo(quarters) >= 0) {
            quarterCounter++;
            currentBalance = currentBalance.subtract(quarters);
        }
        while (currentBalance.compareTo(dimes) >= 0) {
            dimeCounter++;
            currentBalance = currentBalance.subtract(dimes);
        }
        while (currentBalance.compareTo(nickels) >= 0) {
            nickelCounter++;
            currentBalance = currentBalance.subtract(nickels);
        }
        return "Your change is " + quarterCounter + " quarter(s), " + dimeCounter + " dime(s), and " + nickelCounter + " nickels.";
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public BigDecimal getCashBox() {
        return cashBox;
    }

    public void addToCashBox(BigDecimal myDeposit){
        cashBox = cashBox.add(myDeposit);
    }
}


