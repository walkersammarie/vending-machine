package com.techelevator.view;

import com.techelevator.Products.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class VendingMachine {

    private Map<String, Product> productsList = new TreeMap<>();
    private List<String> productInfo = new ArrayList<>();
    private CustomerAccount customerAccount = new CustomerAccount();

    public VendingMachine() throws VendingMachineException {
        getProductInfo();
        createProduct();
    }

    // Method to read in file and arrange products into list
    public void getProductInfo() throws VendingMachineException {

        Path inventory = Paths.get("inventory.txt");

        try (Scanner inventoryScanner = new Scanner(inventory)) {
            while (inventoryScanner.hasNextLine()) {
                productInfo.add(inventoryScanner.nextLine());
            }

        } catch (IOException e) {
            throw new VendingMachineException(e.getMessage());
        }
    }

    // Parses product list and makes individual products based on type
    public void createProduct() {

        for (int i = 0; i < productInfo.size(); i++) {
            String[] individualProductInfo = new String[]{};
            individualProductInfo = productInfo.get(i).split("\\|");

            String productNumber = individualProductInfo[0];
            String productName = individualProductInfo[1];
            String productPrice = individualProductInfo[2];
            String productType = individualProductInfo[3];

            Product product = null;

            if (productType.contains("Candy")) {
                product = new Candy(productName, productPrice);
            } else if (productType.contains("Chip")) {
                product = new Chip(productName, productPrice);
            } else if (productType.contains("Drink")) {
                product = new Drink(productName, productPrice);
            } else if (productType.contains("Gum")) {
                product = new Gum(productName, productPrice);
            }
            productsList.put(productNumber, product);
        }
    }

    // Prints all products as a formatted list
    public String toStringProducts() {
        String displayAllProducts = "";

        for (Map.Entry<String, Product> product : productsList.entrySet()) {
            if (product.getValue().getQuantity() != 0) {
                displayAllProducts += product.getKey() + " | " + product.getValue().getProductName() + " | " + product.getValue().getProductPrice() + "\n";
            } else {
                displayAllProducts += product.getKey() + " | " + "SOLD OUT\n";
            }
        }
        return displayAllProducts;
    }

    // Checks if balance is greater than product price
    public boolean attemptPurchase(String userInput) {
        boolean result = false;
        BigDecimal productPrice = new BigDecimal(getProductsList().get(userInput).getProductPrice());
        if (customerAccount.getCurrentBalance().compareTo(productPrice) >= 0) {
            result = true;
        }
        return result;
    }

    // Checks if product number user inputs exists
    public boolean productNumberExists(String userInput) {
        boolean result = false;
        if (getProductsList().containsKey(userInput)) {
            result = true;
        }
        return result;
    }


    // Checks if item is available
    public boolean quantityIsEnough(String userInput) {
        boolean result = false;
        if (getProductsList().get(userInput).getQuantity() > 0) {
            result = true;
        }
        return result;
    }

    // Completes the purchase
    public void purchaseSuccessful(String userInput) {
        customerAccount.subtractMoney(getProductsList().get(userInput).getProductPrice()); // subtract funds
        getProductsList().get(userInput).subtractOne(); // subtract quantity
    }

    // Makes a string of all products and quantity purchased, and amount of money made since application was started
    public String toStringSalesReport() {
        String displayAllProducts = "";

        for (Map.Entry<String, Product> product : productsList.entrySet()) {
                displayAllProducts += product.getValue().getProductName() + "|" + (5 - product.getValue().getQuantity())+ "\n";
        }
        displayAllProducts += "\n** TOTAL SALES ** $" + customerAccount.getCashBox();
        return displayAllProducts;
    }

    public Map<String, Product> getProductsList() {
        return productsList;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }
}
