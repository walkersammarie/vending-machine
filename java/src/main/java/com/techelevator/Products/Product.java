package com.techelevator.Products;

public abstract class Product implements Sellable {

    private int quantity;
    private final String productName;
    private final String productPrice;

    public Product(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;

        quantity = 5;
    }

    public final int getQuantity() {
        return quantity;
    }

    public final void subtractOne() {
        quantity--;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public String getProductPrice() {
        return productPrice;
    }

    public abstract String getSound();
}
