package com.techelevator.Products;

public class Drink extends Product {

    public Drink(String productName, String productPrice) {
        super(productName, productPrice);
    }

    @Override
    public String getSound() {
        return "Glug Glug, Yum!";
    }
}
