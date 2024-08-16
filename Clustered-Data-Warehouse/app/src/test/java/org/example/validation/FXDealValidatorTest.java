package org.example.validation;

import org.example.model.FXDeal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class FXDealValidatorTest {

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Date parsing failed", e);
        }
    }

    @Test
void testValidFXDeal() {
    FXDeal validDeal = new FXDeal("12345", "USD", "EUR", parseDate("2024-08-15T12:00:00Z"), BigDecimal.valueOf(1000.00));

    try {
        FXDealValidator.validate(validDeal);
    } catch (ValidationException e) {
        Assertions.fail("Validation failed for a valid FXDeal: " + e.getMessage());
    }
}


    @Test
    void testMissingDealId() {
        FXDeal invalidDeal = new FXDeal(null, "USD", "EUR", parseDate("2024-08-15T12:00:00Z"), BigDecimal.valueOf(1000.00));

        Assertions.assertThrows(ValidationException.class, () -> FXDealValidator.validate(invalidDeal));
    }

    @Test
    void testInvalidCurrencyCode() {
        FXDeal invalidDeal = new FXDeal("12345", "US", "EUR", parseDate("2024-08-15T12:00:00Z"), BigDecimal.valueOf(1000.00));

        Assertions.assertThrows(ValidationException.class, () -> FXDealValidator.validate(invalidDeal));
    }

    @Test
    void testNegativeDealAmount() {
        FXDeal invalidDeal = new FXDeal("12345", "USD", "EUR", parseDate("2024-08-15T12:00:00Z"), BigDecimal.valueOf(-1000.00));
    
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            FXDealValidator.validate(invalidDeal);
        });
    
        Assertions.assertEquals("Deal Amount is missing or negative", exception.getMessage());
    }
    

    @Test
    void testMissingTimestamp() {
        FXDeal invalidDeal = new FXDeal("12345", "USD", "EUR", null, BigDecimal.valueOf(1000.00));

        Assertions.assertThrows(ValidationException.class, () -> FXDealValidator.validate(invalidDeal));
    }
}
