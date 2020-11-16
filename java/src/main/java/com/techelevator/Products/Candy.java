package com.techelevator.Products;

public class Candy extends Product {

    public Candy(String productName, String productPrice) {
        super(productName, productPrice);
    }

    @Override
    public String getSound() {
        return "Munch Munch, Yum!";
    }
}
