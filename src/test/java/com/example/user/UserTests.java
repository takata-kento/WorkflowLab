package com.example.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.transaction.Item;
import com.example.transaction.Transaction;
import com.example.transaction.TransactionRecord;

public class UserTests {
    @Test
    void testUserCreation() {
        // Given
        String id = "12345";
        String name = "John Doe";
        int age = 30;
        Gender gender = Gender.MAN;

        // When
        User user = new User(id, name, age, gender);

        // Then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user).extracting("id").isEqualTo(id);
        Assertions.assertThat(user).extracting("name").isEqualTo(name);
        Assertions.assertThat(user).extracting("age").isEqualTo(age);
        Assertions.assertThat(user).extracting("gender").isEqualTo(gender);
    }

    @Test
    void testUserCreationWithNullId() {
        // Given
        String id = null;
        String name = "John Doe";
        int age = 30;
        Gender gender = Gender.WOMAN;

        // When
        // Then
        Assertions.assertThatThrownBy(() -> new User(id, name, age, gender))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id cannot be null");
    }

    @Test
    void testUserCreationWithEmptyId() {
        // Given
        String id = "";
        String name = "John Doe";
        int age = 30;
        Gender gender = Gender.OTHER;

        // When
        // Then
        Assertions.assertThatThrownBy(() -> new User(id, name, age, gender))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id cannot be empty");
    }

    @Test
    void testUserCreationWithNullName() {
        // Given
        String id = "12345";
        String name = null;
        int age = 30;
        Gender gender = Gender.MAN;

        // When
        // Then
        Assertions.assertThatThrownBy(() -> new User(id, name, age, gender))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name cannot be null");
    }

    @Test
    void testUserCreationWithEmptyName() {
        // Given
        String id = "12345";
        String name = "";
        int age = 30;
        Gender gender = Gender.MAN;

        // When
        // Then
        Assertions.assertThatThrownBy(() -> new User(id, name, age, gender))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name cannot be empty");
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, -2, -3 })
    void testUserCreationWithInvalidAge(int age) {
        // Given
        String id = "12345";
        String name = "John Doe";
        Gender gender = Gender.MAN;

        // When
        // Then
        Assertions.assertThatThrownBy(() -> new User(id, name, age, gender))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("age cannot be negative");
    }

    @Test
    void testUserCreationWithInvalidGender() {
        // Given
        String id = "12345";
        String name = "John Doe";
        int age = 30;

        // When
        // Then
        Assertions.assertThatThrownBy(() -> new User(id, name, age, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("gender cannot be null");
    }

    @Test
    void testIsGoldUser() {
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

        // When
        boolean actual = user.isGoldUser(transactionRecord);

        // Then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void testIsNOTGoldUser() {
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

        // When
        boolean actual = user.isGoldUser(transactionRecord);

        // Then
        Assertions.assertThat(actual).isFalse();
    }
}
