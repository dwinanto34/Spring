package com.app.beanvalidation.groups;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

// Configure the sequence using GroupSequence annotation
// The next group won't get executed if the previous group has violations
@GroupSequence(value = {
        Default.class,
        CreditCardPaymentGroup.class,
        BankTransferPaymentGroup.class
})
public interface PaymentGroup {
}