package org.example.validation;


import org.example.model.FXDeal;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.math.BigDecimal;


public class FXDealValidator {

    // Set to store unique deal IDs to avoid duplicates
    private static Set<String> dealIdSet = new HashSet<>();

    // Regular expression to validate currency ISO code (e.g., "USD", "EUR")
    private static final Pattern CURRENCY_CODE_PATTERN = Pattern.compile("^[A-Z]{3}$");

    public static void validate(FXDeal deal) throws ValidationException {
        validateFields(deal);
        validateCurrencyCodes(deal);
        validateUniqueDealId(deal);
    }

    private static void validateFields(FXDeal deal) throws ValidationException {
        if (deal.getDealUniqueId() == null || deal.getDealUniqueId().isEmpty()) {
            throw new ValidationException("Deal Unique ID is missing or empty");
        }
        if (deal.getFromCurrencyIsoCode() == null || deal.getFromCurrencyIsoCode().isEmpty()) {
            throw new ValidationException("From Currency ISO Code is missing or empty");
        }
        if (deal.getToCurrencyIsoCode() == null || deal.getToCurrencyIsoCode().isEmpty()) {
            throw new ValidationException("To Currency ISO Code is missing or empty");
        }
        if (deal.getDealTimestamp() == null) {
            throw new ValidationException("Deal Timestamp is missing");
        }
        if (deal.getDealAmount() == null || deal.getDealAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Deal Amount is missing or negative");
        }
    }
    

    private static void validateCurrencyCodes(FXDeal deal) throws ValidationException {
        if (!CURRENCY_CODE_PATTERN.matcher(deal.getFromCurrencyIsoCode()).matches()) {
            throw new ValidationException("Invalid From Currency ISO Code");
        }
        if (!CURRENCY_CODE_PATTERN.matcher(deal.getToCurrencyIsoCode()).matches()) {
            throw new ValidationException("Invalid To Currency ISO Code");
        }
    }

    private static void validateUniqueDealId(FXDeal deal) throws ValidationException {
        if (dealIdSet.contains(deal.getDealUniqueId())) {
            throw new ValidationException("Duplicate Deal Unique ID: " + deal.getDealUniqueId());
        } else {
            dealIdSet.add(deal.getDealUniqueId());
        }
    }
}
