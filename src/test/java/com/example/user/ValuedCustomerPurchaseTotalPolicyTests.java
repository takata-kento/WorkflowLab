package com.example.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.transaction.Item;
import com.example.transaction.Transaction;
import com.example.transaction.TransactionRecord;

public class ValuedCustomerPurchaseTotalPolicyTests {
    @Test
    void testFulfillsPolicy() {
        // Given
        String id = "12345";
        String name = "John Doe";
        int age = 30;
        Gender gender = Gender.MAN;
        User user = new User(id, name, age, gender);

        Item apple = new Item("apple", 100);
        Item banana = new Item("banana", 300);

        String firstTransactionId = "00001";
        ZonedDateTime firstTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction firstTransaction = new Transaction(firstTransactionId, user, firstTransactionDate);
        Transaction fixedFirstTransaction = firstTransaction.add(apple, 100).add(banana, 100);

        String secondTransactionId = "00002";
        ZonedDateTime secondTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction secondTransaction = new Transaction(secondTransactionId, user, secondTransactionDate);
        Transaction fixedSecondTransaction = secondTransaction.add(banana, 200);

        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.add(fixedFirstTransaction).add(fixedSecondTransaction);

        ValuedCustomerPurchaseTotalPolicy policy = new ValuedCustomerPurchaseTotalPolicy(transactionRecord, user);

        // When
        boolean actual = policy.isOk();

        // Then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void testFailureFulfillsPolicy() {
        // Given
        String id = "12345";
        String name = "John Doe";
        int age = 30;
        Gender gender = Gender.MAN;
        User user = new User(id, name, age, gender);

        Item apple = new Item("apple", 100);
        Item banana = new Item("banana", 300);

        String firstTransactionId = "00001";
        ZonedDateTime firstTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction firstTransaction = new Transaction(firstTransactionId, user, firstTransactionDate);
        Transaction fixedFirstTransaction = firstTransaction.add(apple, 10).add(banana, 10);

        String secondTransactionId = "00002";
        ZonedDateTime secondTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction secondTransaction = new Transaction(secondTransactionId, user, secondTransactionDate);
        Transaction fixedSecondTransaction = secondTransaction.add(banana, 10);

        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.add(fixedFirstTransaction).add(fixedSecondTransaction);

        ValuedCustomerPurchaseTotalPolicy policy = new ValuedCustomerPurchaseTotalPolicy(transactionRecord, user);

        // When
        boolean actual = policy.isOk();

        // Then
        Assertions.assertThat(actual).isFalse();
    }
}
