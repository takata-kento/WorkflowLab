package com.example.transaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class TransactionUnitTest {
    @Test
    void testConstructor() {
        // Given
        String expectedTransactionId = "12345";
        int expectedIndex = 1;
        Item expectedItem = new Item("apple", 100);
        int expectedQuantity = 4;

        // When
        TransactionUnit transactionUnit = new TransactionUnit(expectedTransactionId, expectedIndex, expectedItem, expectedQuantity);

        // Then
        Assertions.assertThat(transactionUnit).isNotNull();

        Assertions.assertThat(transactionUnit).extracting("transactionId").isEqualTo(expectedTransactionId);

        Assertions.assertThat(transactionUnit).extracting("index").isEqualTo(expectedIndex);

        Assertions.assertThat(transactionUnit).extracting("item").isEqualTo(expectedItem);

        Assertions.assertThat(transactionUnit).extracting("quantity").isEqualTo(expectedQuantity);

        Assertions.assertThat(transactionUnit).extracting("canceled").isEqualTo(false);
    }

    @Test
    void testGetTransactionId() {
        // Given
        String expected = "12345";
        Item item = new Item("apple", 100);
        TransactionUnit transactionUnit = new TransactionUnit(expected, 1, item, 4);

        // When
        String actual = transactionUnit.getTransactionId();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testGetIndex() {
        // Given
        String transactionId = "12345";
        int expected = 1;
        Item item = new Item("apple", 100);
        TransactionUnit transactionUnit = new TransactionUnit(transactionId, expected, item, 4);

        // When
        int actual = transactionUnit.getIndex();

        // Then
        Assertions.assertThat(actual).isEqualTo(1);
    }

    @Test
    void testGetItem() {
        // Given
        String transactionId = "12345";
        Item expected = new Item("apple", 100);
        TransactionUnit transactionUnit = new TransactionUnit(transactionId, 1, expected, 4);

        // When
        Item actual = transactionUnit.getItem();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testGetQuantity() {
        // Given
        String transactionId = "12345";
        Item item = new Item("apple", 100);
        int expected = 4;
        TransactionUnit transactionUnit = new TransactionUnit(transactionId, 1, item, expected);

        // When
        int actual = transactionUnit.getQuantity();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testUpdateQuantity() {
        // Given
        String transactionId = "12345";
        Item item = new Item("apple", 100);
        int expected = 5;
        TransactionUnit transactionUnit = new TransactionUnit(transactionId, 1, item, 4);

        // When
        TransactionUnit actual = transactionUnit.updateQuantity(expected);

        // Then
        Assertions.assertThat(actual).extracting("quantity").isEqualTo(expected);
    }

    @Test
    void testCanceledUnitUpdateQuantity() {
        // Given
        String transactionId = "12345";
        Item item = new Item("apple", 100);
        int quantity = 4;
        TransactionUnit transactionUnit = new TransactionUnit(transactionId, 1, item, quantity);

        // When
        // Then
        TransactionUnit canceledUnit = transactionUnit.cancel();

        Assertions.assertThatThrownBy(() -> canceledUnit.updateQuantity(quantity + 1)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testCancel() {
        // Given
        String transactionId = "12345";
        Item item = new Item("apple", 100);
        TransactionUnit transactionUnit = new TransactionUnit(transactionId, 1, item, 5);

        // When
        TransactionUnit actual = transactionUnit.cancel();

        // Then
        Assertions.assertThat(actual).extracting("canceled").isEqualTo(true);
    }

    @Test
    void testIsCanceled() {
        // Given
        String transactionId = "12345";
        Item item = new Item("apple", 100);
        TransactionUnit transactionUnit = new TransactionUnit(transactionId, 1, item, 5);
        ReflectionTestUtils.setField(transactionUnit, "canceled", true);

        // When
        boolean actual = transactionUnit.isCanceled();

        // Then
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    void testEquals() {
        // Given
        String transactionId = "12345";
        Item item = new Item("apple", 100);
        TransactionUnit transactionUnit1 = new TransactionUnit(transactionId, 1, item, 5);
        TransactionUnit transactionUnit2 = new TransactionUnit(transactionId, 1, item, 5);
        TransactionUnit transactionUnit3 = new TransactionUnit(transactionId, 1, item, 1);

        // When
        boolean actualEqual = transactionUnit1.equals(transactionUnit2);
        boolean actualNotEqual = transactionUnit1.equals(transactionUnit3);

        // Then
        Assertions.assertThat(actualEqual).isTrue();
        Assertions.assertThat(actualNotEqual).isFalse();
    }
}
