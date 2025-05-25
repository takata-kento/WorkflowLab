package com.example.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.transaction.Item;
import com.example.transaction.Transaction;
import com.example.transaction.TransactionRecord;

public class ValuedCustomerCancelRatePolicyTests {
    @Test
    void testFulfillsPolicy() {
        // Given
        String id = "12345";
        String name = "John Doe";
        int age = 30;
        Gender gender = Gender.MAN;
        User user = new User(id, name, age, gender);

        Item apple = new Item("apple", 100);

        ZonedDateTime baseTransactionDate = ZonedDateTime.now(ZoneId.of("UTC+09:00"));
        TransactionRecord transactionRecord = new TransactionRecord();

        for (int i = 1; i <= 1000; i++) {
            String transactionId = String.format("%05d", i);
            ZonedDateTime transactionDate = baseTransactionDate.withDayOfMonth(1);
            Transaction transaction = new Transaction(transactionId, user, transactionDate).add(apple, 100);
            transactionRecord.add(transaction);
        }

        ValuedCustomerCancelRatePolicy policy = new ValuedCustomerCancelRatePolicy(transactionRecord, user);

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

        ZonedDateTime baseTransactionDate = ZonedDateTime.now(ZoneId.of("UTC+09:00"));
        TransactionRecord transactionRecord = new TransactionRecord();

        for (int i = 1; i <= 1000; i++) {
            String transactionId = String.format("%05d", i);
            ZonedDateTime transactionDate = baseTransactionDate.withDayOfMonth(1);
            Transaction transaction = new Transaction(transactionId, user, transactionDate).add(apple, 100);
            transactionRecord.add(transaction);
        }

        transactionRecord.cancelByTransactionId("00001").cancelByTransactionId("00002");

        ValuedCustomerCancelRatePolicy policy = new ValuedCustomerCancelRatePolicy(transactionRecord, user);

        // When
        boolean actual = policy.isOk();

        // Then
        Assertions.assertThat(actual).isFalse();
    }
}
