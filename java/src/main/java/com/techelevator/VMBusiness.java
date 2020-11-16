package com.techelevator;

import com.techelevator.view.VendingMachine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VMBusiness {

    public static void logDeposit(BigDecimal currentBalance, BigDecimal deposit) {
        String line = "FEED MONEY: $" + deposit + " $" + currentBalance;
        printToLog(line);
    }

    public static void logPurchase(String productNumber, String productName, String productPrice,
                                   BigDecimal currentBalance) {
        String line = productName + " " + productNumber + " $" + productPrice + " $" + currentBalance;
        printToLog(line);
    }

    public static void logChange(BigDecimal currentBalance) {
        String line = " GIVE CHANGE: $" + currentBalance + " $0.00";
        printToLog(line);

    }

    private static void printToLog(String line) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        String text = dateTime.format(formatter);
        try (FileOutputStream stream = new FileOutputStream("VMLog.txt", true); PrintWriter writer =
                new PrintWriter(stream)) {
            writer.println(text + " " + line);
        } catch (IOException e) {
            System.out.println("Error, could not log information. Please contact your local technician.");
        }
    }

    public static void runSalesReport(VendingMachine vendingMachine) {
        try (FileOutputStream stream = new FileOutputStream("VMSalesReport.txt"); PrintWriter writer =
                new PrintWriter(stream)) {
            writer.println(vendingMachine.toStringSalesReport());
            System.out.println("\n** GENERATING SALES REPORT **");
            stream.flush();
        } catch (IOException e) {
            System.out.println("Error, could not print sales report. Please contact your local technician.");
        }
    }
}
