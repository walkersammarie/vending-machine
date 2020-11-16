package com.techelevator;

import com.techelevator.view.*;

import java.math.BigDecimal;

public class Application {

    // Main Menu
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_EXIT_PROGRAM = "Exit";
    private static final String MAIN_MENU_SALES_REPORT = Menu.HIDDEN_OPTION;
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
            MAIN_MENU_EXIT_PROGRAM, MAIN_MENU_SALES_REPORT};

    // Purchase Menu
    private static final String PURCHASE_MENU_OPTION_FEEDMONEY = "Add money to account.";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Buy a snack.";
    private static final String PURCHASE_MENU_EXIT_SELECT = "Get change and return to main menu.";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEEDMONEY,
            PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_EXIT_SELECT};


    private final BasicUI ui;
    private VendingMachine vendingMachine;

    public Application(BasicUI ui) {
        this.ui = ui;
    }

    public static void main(String[] args) {
        BasicUI cli = new MenuDrivenCLI();
        Application application = new Application(cli);
        application.prepareVendingMachine();
        application.run();
    }

    // Create new vending machine
    private void prepareVendingMachine() {
        try {
            vendingMachine = new VendingMachine();
        } catch (VendingMachineException e) {
            ui.output("Couldn't create vending machine.");
        }
    }

    public void run() {

        boolean running = true;

        while (running) {
            String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);

            if (selection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                // display vending machine items
                displayProducts();
            } else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {
                // do purchase
                displayPurchaseMenu();
            } else if (selection.equals(MAIN_MENU_EXIT_PROGRAM)) {
                // exit program
                VMBusiness.runSalesReport(vendingMachine);
                ui.output("Keep snacking and have a great day!");
                running = false;
            } else if (selection.equals(MAIN_MENU_SALES_REPORT)) {
                // run sales report
                VMBusiness.runSalesReport(vendingMachine);
                ui.pauseOutput();
            }
        }
    }

    // Prints all products
    private void displayProducts() {
        ui.output("Choose from the following options:");
        ui.output(vendingMachine.toStringProducts());
        ui.pauseOutput();
    }

    // Asks for integer input from user for deposit and returns new balance
    private void promptUserForMoney() {
        int fundsToDeposit = ui.promptForInt("Please enter amount for deposit (whole dollars only): ");
        vendingMachine.getCustomerAccount().depositMoney(fundsToDeposit);
        ui.output("Your current balance is: " + vendingMachine.getCustomerAccount().getCurrentBalance() + "\nThanks " +
                "for the cash!");
        ui.pauseOutput();
    }

    private void promptUserForSelection() {
        ui.output(vendingMachine.toStringProducts());
        promptForProductNumber("Please enter the product number: ");
    }

    // Prompts for a product number, checks if everything is valid, then makes purchase if customer has enough money
    public void promptForProductNumber(String prompt) {
        String userInput = ui.promptForString(prompt);
        String errorMessage = getValidationErrorMessage(userInput);
        if (errorMessage != null) {
            System.out.println(errorMessage);
            return;

        }
        vendingMachine.purchaseSuccessful(userInput);

        VMBusiness.logPurchase(userInput,
                vendingMachine.getProductsList().get(userInput).getProductName(),
                vendingMachine.getProductsList().get(userInput).getProductPrice(),
                vendingMachine.getCustomerAccount().getCurrentBalance());
        ui.output("*** ITEM DISPENSING ***");
        ui.output(vendingMachine.getProductsList().get(userInput).getSound());
        ui.output("You have " + vendingMachine.getCustomerAccount().getCurrentBalance() + " dollars " +
                "left"); // give current balance
        ui.pauseOutput();
    }

    // Validates user input and account balance before purchase is made
    private String getValidationErrorMessage(String userInput) {
        if (userInput == null) {
            return "Input required";
        }

        boolean productNumberExists = vendingMachine.productNumberExists(userInput);
        if (!productNumberExists) {
            return "Product doesn't exist, please make a different selection.";
        }

        boolean productQuantityGreaterThan0 = vendingMachine.quantityIsEnough(userInput);
        if (!productQuantityGreaterThan0) {
            return "Product is sold out, please make a different selection.";
        }

        boolean currentBalanceGreaterThanProductAmount = vendingMachine.attemptPurchase(userInput);
        if (!currentBalanceGreaterThanProductAmount) {
            return "Insufficient funds, please add more money.";
        }

        return null;
    }

    private void displayPurchaseMenu() {

        boolean running = true;
        while (running) {
            String selection = ui.promptForSelection(PURCHASE_MENU_OPTIONS);
            if (selection.equals(PURCHASE_MENU_OPTION_FEEDMONEY)) {
                promptUserForMoney();
            } else if (selection.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                if (vendingMachine.getCustomerAccount().getCurrentBalance().compareTo(BigDecimal.ZERO) <= 0) {
                    ui.output("Please add money to account before making a selection.");
                    ui.pauseOutput();
                } else {
                    promptUserForSelection();
                }
            } else if (selection.equals(PURCHASE_MENU_EXIT_SELECT)) {
                ui.output(vendingMachine.getCustomerAccount().makeChange());
                ui.pauseOutput();
                running = false;
            }
        }
    }
}

