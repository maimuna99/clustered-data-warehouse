/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import com.mongodb.client.MongoDatabase;
import org.example.model.FXDeal;
import org.example.validation.FXDealValidator;
import org.example.validation.ValidationException;

import java.math.BigDecimal;
import java.util.Date;


public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        MongoDatabase database = MongoDBConnection.connect();
        System.out.println("Connected to Database: " + database.getName());

        FXDeal deal = new FXDeal("123", "USD", "EUR", new Date(), new BigDecimal("1000.00"));

        try {
            FXDealValidator.validate(deal);
            // Proceed to save the deal to the database
            System.out.println("Deal is valid and ready to be saved to DB: " + deal);
        } catch (ValidationException e) {
            // Handle validation errors
            System.err.println("Validation failed: " + e.getMessage());
        }
    }
}
