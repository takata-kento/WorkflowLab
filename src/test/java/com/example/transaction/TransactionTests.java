package com.example.transaction;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.user.Gender;
import com.example.user.User;

public class TransactionTests {
    @Test
    void testTransactionCreation() {
        // Given
        String transactionId = "12345";
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime transactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));

        // When
        Transaction actual = new Transaction(transactionId, user, transactionDate);

        // Then
        @SuppressWarnings("unchecked")
        List<TransactionUnit> actualDetail = (List<TransactionUnit>) ReflectionTestUtils.getField(actual, "detail");
        Assertions.assertThat(actualDetail).isEmpty();

        String actualTransactionId = (String) ReflectionTestUtils.getField(actual, "transactionId");
        Assertions.assertThat(actualTransactionId).isEqualTo(transactionId);
    
        User actualUser = (User) ReflectionTestUtils.getField(actual, "user");
        Assertions.assertThat(actualUser).isEqualTo(user);
    
        ZonedDateTime actualTransactionDate = (ZonedDateTime) ReflectionTestUtils.getField(actual, "transactionDate");
        Assertions.assertThat(actualTransactionDate).isEqualTo(transactionDate);

        @SuppressWarnings("null")
        boolean actualCanceled = (boolean) ReflectionTestUtils.getField(actual, "canceled");
        Assertions.assertThat(actualCanceled).isFalse();
    }

    @Test
    void testGetDetail() {
        // Given
        String transactionId = "12345";
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime transactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(transactionId, user, transactionDate);

        Item firstItem = new Item("apple", 100);
        Item secondItem = new Item("banana", 200);
        TransactionUnit firstTransactionUnit = new TransactionUnit(transactionId, 1, firstItem, 1);
        TransactionUnit secondTransactionUnit = new TransactionUnit(transactionId, 2, secondItem, 1);
        List<TransactionUnit> expect = List.of(firstTransactionUnit, secondTransactionUnit);

        ReflectionTestUtils.setField(transaction, "detail", expect);

        // When
        List<TransactionUnit> actual = transaction.getDetail();

        // Then
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testAdd() {
        // Given
        String expectedTransactionId = "12345";
        Item item = new Item("apple", 100);
        User expectedUser = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime expectedDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expectedTransactionId, expectedUser, expectedDate);

        TransactionUnit transactionUnit = new TransactionUnit(expectedTransactionId, 1, item, 1);
        List<TransactionUnit> expectedDetail = List.of(transactionUnit);

        // When
        Transaction actual = transaction.add(item);

        // Then
        Assertions.assertThat(actual).extracting("transactionId").isEqualTo(expectedTransactionId);
        Assertions.assertThat(actual).extracting("detail").isEqualTo(expectedDetail);
        Assertions.assertThat(actual).extracting("user").isEqualTo(expectedUser);
        Assertions.assertThat(actual).extracting("transactionDate").isEqualTo(expectedDate);
        Assertions.assertThat(actual).extracting("canceled").isEqualTo(false);
    }

    @Test
    void testMultiAdd() {
        // Given
        String expectedTransactionId = "12345";
        Item firstItem = new Item("apple", 100);
        Item secondItem = new Item("banana", 200);
        User expectedUser = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime expectedDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expectedTransactionId, expectedUser, expectedDate);

        TransactionUnit firstTransactionUnit = new TransactionUnit(expectedTransactionId, 1, firstItem, 1);
        TransactionUnit secondTransactionUnit = new TransactionUnit(expectedTransactionId, 2, secondItem, 1);
        List<TransactionUnit> expectedDetail = List.of(firstTransactionUnit, secondTransactionUnit);

        // When
        Transaction actual = transaction.add(firstItem).add(secondItem);

        // Then
        Assertions.assertThat(actual).extracting("transactionId").isEqualTo(expectedTransactionId);
        Assertions.assertThat(actual).extracting("detail").isEqualTo(expectedDetail);
        Assertions.assertThat(actual).extracting("user").isEqualTo(expectedUser);
        Assertions.assertThat(actual).extracting("transactionDate").isEqualTo(expectedDate);
        Assertions.assertThat(actual).extracting("canceled").isEqualTo(false);
    }

    @Test
    void testAddWithQuantity() {
        // Given
        String expectedTransactionId = "12345";
        Item item = new Item("apple", 100);
        int quantity = 3;
        User expectedUser = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime expectedDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expectedTransactionId, expectedUser, expectedDate);
        TransactionUnit transactionUnit = new TransactionUnit(expectedTransactionId, 1, item, quantity);
        List<TransactionUnit> expectedDetail = List.of(transactionUnit);

        // When
        Transaction actual = transaction.add(item, quantity);

        // Then
        Assertions.assertThat(actual).extracting("transactionId").isEqualTo(expectedTransactionId);
        Assertions.assertThat(actual).extracting("detail").isEqualTo(expectedDetail);
        Assertions.assertThat(actual).extracting("user").isEqualTo(expectedUser);
        Assertions.assertThat(actual).extracting("transactionDate").isEqualTo(expectedDate);
        Assertions.assertThat(actual).extracting("canceled").isEqualTo(false);
    }

    @Test
    void testMultiAddWithQuantity() {
        // Given
        String expectedTransactionId = "12345";
        Item firstItem = new Item("apple", 100);
        Item secondItem = new Item("banana", 300);
        Item thirdItem = new Item("orange", 900);
        int firstItemQuantity = 10;
        int secondItemQuantity = 90;
        int thirdItemQuantity = 1;
        User expectedUser = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime expectedDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expectedTransactionId, expectedUser, expectedDate);
        TransactionUnit firstTransactionUnit = new TransactionUnit(expectedTransactionId, 1, firstItem, firstItemQuantity);
        TransactionUnit secondTransactionUnit = new TransactionUnit(expectedTransactionId, 2, secondItem, secondItemQuantity);
        TransactionUnit thirdTransactionUnit = new TransactionUnit(expectedTransactionId, 3, thirdItem, thirdItemQuantity);
        List<TransactionUnit> expectedDetail = List.of(firstTransactionUnit, secondTransactionUnit, thirdTransactionUnit);

        // When
        Transaction actual = transaction.add(firstItem, firstItemQuantity).add(secondItem, secondItemQuantity).add(thirdItem, thirdItemQuantity);

        // Then
        Assertions.assertThat(actual).extracting("transactionId").isEqualTo(expectedTransactionId);
        Assertions.assertThat(actual).extracting("detail").isEqualTo(expectedDetail);
        Assertions.assertThat(actual).extracting("user").isEqualTo(expectedUser);
        Assertions.assertThat(actual).extracting("transactionDate").isEqualTo(expectedDate);
        Assertions.assertThat(actual).extracting("canceled").isEqualTo(false);
    }

    @Test
    void testRowCancel() {
        // Given
        int firstItemQuantity = 3;
        Item firstItem = new Item("apple", 100);
        int secondItemQuantity = 5;
        Item secondItem = new Item("banana", 200);
        int thirdItemQuantity = 7;
        Item thirdItem = new Item("orange", 300);

        String expectedTransactionId = "12345";
        User expectedUser = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime expectedDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expectedTransactionId, expectedUser, expectedDate);

        TransactionUnit firstUnit = new TransactionUnit(expectedTransactionId, 1, firstItem, firstItemQuantity);
        TransactionUnit secondUnit = new TransactionUnit(expectedTransactionId, 2, secondItem, secondItemQuantity);
        ReflectionTestUtils.setField(secondUnit, "canceled", true);
        TransactionUnit thirdUnit = new TransactionUnit(expectedTransactionId, 3, thirdItem, thirdItemQuantity);
        List<TransactionUnit> expectedDetail = List.of(firstUnit, secondUnit, thirdUnit);

        // When
        Transaction actual = transaction.add(firstItem, firstItemQuantity).add(secondItem, secondItemQuantity).add(thirdItem, thirdItemQuantity).rowCancel(2);

        // Then
        Assertions.assertThat(actual).extracting("transactionId").isEqualTo(expectedTransactionId);
        Assertions.assertThat(actual).extracting("detail").isEqualTo(expectedDetail);
        Assertions.assertThat(actual).extracting("user").isEqualTo(expectedUser);
        Assertions.assertThat(actual).extracting("transactionDate").isEqualTo(expectedDate);
        Assertions.assertThat(actual).extracting("canceled").isEqualTo(false);
    }

    @Test
    void testSubTotal() {
        // Given
        int firstItemQuantity = 3;
        Item firstItem = new Item("apple", 100);
        int secondItemQuantity = 5;
        Item secondItem = new Item("banana", 200);
        int thirdItemQuantity = 7;
        Item thirdItem = new Item("orange", 300);

        String expectedTransactionId = "12345";
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime transactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expectedTransactionId, user, transactionDate);

        int expected = (firstItem.price() * firstItemQuantity) + (secondItem.price() * secondItemQuantity) + (thirdItem.price() * thirdItemQuantity);

        // When
        Transaction currentTransaction = transaction.add(firstItem, firstItemQuantity).add(secondItem, secondItemQuantity).add(thirdItem, thirdItemQuantity);
        int actual = currentTransaction.subTotal();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testTotal() {
        // Given
        double TAXRATE = 1.1;
        int firstItemQuantity = 3;
        Item firstItem = new Item("apple", 100);
        int secondItemQuantity = 5;
        Item secondItem = new Item("banana", 200);
        int thirdItemQuantity = 7;
        Item thirdItem = new Item("orange", 300);

        String expectedTransactionId = "12345";
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime transactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expectedTransactionId, user, transactionDate);

        int expected = (int) (((firstItem.price() * firstItemQuantity) + (secondItem.price() * secondItemQuantity) + (thirdItem.price() * thirdItemQuantity)) * TAXRATE);

        // When
        Transaction currentTransaction = transaction.add(firstItem, firstItemQuantity).add(secondItem, secondItemQuantity).add(thirdItem, thirdItemQuantity);
        int actual = currentTransaction.total();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testGetUser() {
        // Given
        String transactionId = "12345";
        User expected = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime transactionDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(transactionId, expected, transactionDate);

        // When
        User actual = transaction.getUser();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testGetTransactionDate() {
        // Given
        String transactionId = "12345";
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime expected = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(transactionId, user, expected);

        // When
        ZonedDateTime actual = transaction.getTransactionDate();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testCancel() {
        int firstItemQuantity = 3;
        Item firstItem = new Item("apple", 100);
        int secondItemQuantity = 5;
        Item secondItem = new Item("banana", 200);
        int thirdItemQuantity = 7;
        Item thirdItem = new Item("orange", 300);

        String expectedTransactionId = "12345";
        User expectedUser = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime expectedDate = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expectedTransactionId, expectedUser, expectedDate)
                .add(firstItem, firstItemQuantity)
                .add(secondItem, secondItemQuantity)
                .add(thirdItem, thirdItemQuantity);

        TransactionUnit firstUnit = new TransactionUnit(expectedTransactionId, 1, firstItem, firstItemQuantity);
        TransactionUnit secondUnit = new TransactionUnit(expectedTransactionId, 2, secondItem, secondItemQuantity);
        TransactionUnit thirdUnit = new TransactionUnit(expectedTransactionId, 3, thirdItem, thirdItemQuantity);
        List<TransactionUnit> expectedDetail = List.of(firstUnit, secondUnit, thirdUnit);

        // When
        Transaction actual = transaction.cancel();

        // Then
        Assertions.assertThat(actual).extracting("transactionId").isEqualTo(expectedTransactionId);
        Assertions.assertThat(actual).extracting("detail").isEqualTo(expectedDetail);
        Assertions.assertThat(actual).extracting("user").isEqualTo(expectedUser);
        Assertions.assertThat(actual).extracting("transactionDate").isEqualTo(expectedDate);
        Assertions.assertThat(actual).extracting("canceled").isEqualTo(true);
    }

    @Test
    void testIsCanceled() {
        // Given
        int firstItemQuantity = 3;
        Item firstItem = new Item("apple", 100);
        int secondItemQuantity = 5;
        Item secondItem = new Item("banana", 200);
        int thirdItemQuantity = 7;
        Item thirdItem = new Item("orange", 300);

        String transactionId = "12345";
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime date = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(transactionId, user, date)
                .add(firstItem, firstItemQuantity)
                .add(secondItem, secondItemQuantity)
                .add(thirdItem, thirdItemQuantity)
                .cancel();

        // When
        boolean actual = transaction.isCanceled();

        // Then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void testGetTransactionId() {
        // Given
        int firstItemQuantity = 3;
        Item firstItem = new Item("apple", 100);
        int secondItemQuantity = 5;
        Item secondItem = new Item("banana", 200);
        int thirdItemQuantity = 7;
        Item thirdItem = new Item("orange", 300);

        String expected = "12345";
        User user = new User("00001", "John Doe", 20, Gender.MAN);
        ZonedDateTime date = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC+09:00"));
        Transaction transaction = new Transaction(expected, user, date)
                .add(firstItem, firstItemQuantity)
                .add(secondItem, secondItemQuantity)
                .add(thirdItem, thirdItemQuantity)
                .cancel();

        // When
        String actual = transaction.getTransactionId();

        // Then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
