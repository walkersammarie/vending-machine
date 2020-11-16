package com.techelevator.Products;

public class Chip extends Product {


    public Chip(String productName, String productPrice) {
        super(productName, productPrice);
    }

    @Override
    public String getSound() {
        return "Crunch Crunch, Yum!";
    }
}
