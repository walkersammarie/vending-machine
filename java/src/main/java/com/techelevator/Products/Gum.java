package com.techelevator.Products;

public class Gum extends Product {

    public Gum(String productName, String productPrice) {
        super(productName, productPrice);
    }

    @Override
    public String getSound() {
        return "Chew Chew, Yum!";
    }
}
