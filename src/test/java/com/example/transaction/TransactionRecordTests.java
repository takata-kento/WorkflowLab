package com.example.transaction;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.user.Gender;
import com.example.user.User;

public class TransactionRecordTests {
    @Test
    void testConstructor() {
        // Given
        List<Transaction> expected = new ArrayList<>();

        // When
        TransactionRecord transactionRecord = new TransactionRecord();

        // Then
        Assertions.assertThat(transactionRecord).extracting("transactions").isEqualTo(expected);
    }

    @Test
    void testGet() {
        // Given
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime firstTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        ZonedDateTime secondTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 30, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction firstTransaction = new Transaction("00001", user, firstTransactionDate);
        Transaction secondTransaction = new Transaction("00002", user, secondTransactionDate);
        List<Transaction> expected = List.of(firstTransaction, secondTransaction);

        TransactionRecord transactionRecord = new TransactionRecord();
        ReflectionTestUtils.setField(transactionRecord, "transactions", expected);

        // When
        List<Transaction> actual = transactionRecord.get();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testAdd() {
        // Given
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime firstTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        ZonedDateTime secondTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 30, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction firstTransaction = new Transaction("00001", user, firstTransactionDate);
        Transaction secondTransaction = new Transaction("00002", user, secondTransactionDate);
        List<Transaction> expected = List.of(firstTransaction, secondTransaction);
        TransactionRecord transactionRecord = new TransactionRecord();

        // When
        TransactionRecord actual = transactionRecord.add(firstTransaction).add(secondTransaction);

        // Then
        Assertions.assertThat(actual).extracting("transactions").usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testCancelByTransactionId() {
        // Given
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime firstTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        ZonedDateTime secondTransactionDate = ZonedDateTime.of(2025, 1, 1, 12, 30, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction firstTransaction = new Transaction("00001", user, firstTransactionDate);
        Transaction secondTransaction = new Transaction("00002", user, secondTransactionDate);
        List<Transaction> expected = List.of(firstTransaction.cancel(), secondTransaction);
        TransactionRecord transactionRecord = new TransactionRecord()
                .add(firstTransaction)
                .add(secondTransaction);

        // When
        TransactionRecord actual = transactionRecord.cancelByTransactionId("00001");

        // Then
        Assertions.assertThat(actual).extracting("transactions").usingRecursiveComparison().isEqualTo(expected);
    }
}
