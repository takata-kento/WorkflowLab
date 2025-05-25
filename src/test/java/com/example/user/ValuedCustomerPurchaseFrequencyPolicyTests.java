package com.example.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.transaction.Item;
import com.example.transaction.Transaction;
import com.example.transaction.TransactionRecord;

public class ValuedCustomerPurchaseFrequencyPolicyTests {
    @Test
    void testFulfillsPolicy() {
        // Given
        String id = "12345";
        String name = "John Doe";
        int age = 30;
        Gender gender = Gender.MAN;
        User user = new User(id, name, age, gender);

        Item apple = new Item("apple", 100);

        String transactionId1 = "00001";
        String transactionId2 = "00002";
        String transactionId3 = "00003";
        String transactionId4 = "00004";
        String transactionId5 = "00005";
        String transactionId6 = "00006";
        String transactionId7 = "00007";
        String transactionId8 = "00008";
        String transactionId9 = "00009";
        String transactionId10 = "00010";

        ZonedDateTime baseTransactionDate = ZonedDateTime.now(ZoneId.of("UTC+09:00"));
        ZonedDateTime transactionDate1 = baseTransactionDate.withDayOfMonth(1);
        ZonedDateTime transactionDate2 = baseTransactionDate.withDayOfMonth(5);
        ZonedDateTime transactionDate3 = baseTransactionDate.withDayOfMonth(9);
        ZonedDateTime transactionDate4 = baseTransactionDate.withDayOfMonth(13);
        ZonedDateTime transactionDate5 = baseTransactionDate.withDayOfMonth(18);
        ZonedDateTime transactionDate6 = baseTransactionDate.withDayOfMonth(19);
        ZonedDateTime transactionDate7 = baseTransactionDate.withDayOfMonth(20);
        ZonedDateTime transactionDate8 = baseTransactionDate.withDayOfMonth(21);
        ZonedDateTime transactionDate9 = baseTransactionDate.withDayOfMonth(24);
        ZonedDateTime transactionDate10 = baseTransactionDate.withDayOfMonth(25);

        Transaction transaction1 = new Transaction(transactionId1, user, transactionDate1).add(apple, 100);
        Transaction transaction2 = new Transaction(transactionId2, user, transactionDate2).add(apple, 100);
        Transaction transaction3 = new Transaction(transactionId3, user, transactionDate3).add(apple, 100);
        Transaction transaction4 = new Transaction(transactionId4, user, transactionDate4).add(apple, 100);
        Transaction transaction5 = new Transaction(transactionId5, user, transactionDate5).add(apple, 100);
        Transaction transaction6 = new Transaction(transactionId6, user, transactionDate6).add(apple, 100);
        Transaction transaction7 = new Transaction(transactionId7, user, transactionDate7).add(apple, 100);
        Transaction transaction8 = new Transaction(transactionId8, user, transactionDate8).add(apple, 100);
        Transaction transaction9 = new Transaction(transactionId9, user, transactionDate9).add(apple, 100);
        Transaction transaction10 = new Transaction(transactionId10, user, transactionDate10).add(apple, 100);

        TransactionRecord transactionRecord = new TransactionRecord()
                .add(transaction1)
                .add(transaction2)
                .add(transaction3)
                .add(transaction4)
                .add(transaction5)
                .add(transaction6)
                .add(transaction7)
                .add(transaction8)
                .add(transaction9)
                .add(transaction10);

        ValuedCustomerPurchaseFrequencyPolicy policy = new ValuedCustomerPurchaseFrequencyPolicy(transactionRecord, user);

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

        String transactionId1 = "00001";
        String transactionId2 = "00002";
        String transactionId3 = "00003";
        String transactionId4 = "00004";
        String transactionId5 = "00005";
        String transactionId6 = "00006";
        String transactionId7 = "00007";
        String transactionId8 = "00008";
        String transactionId9 = "00009";
        String transactionId10 = "00010";

        ZonedDateTime baseTransactionDate = ZonedDateTime.now(ZoneId.of("UTC+09:00"));
        ZonedDateTime transactionDate1 = baseTransactionDate.withDayOfMonth(1);
        ZonedDateTime transactionDate2 = baseTransactionDate.withMonth(1).withDayOfMonth(5);
        ZonedDateTime transactionDate3 = baseTransactionDate.withMonth(2).withDayOfMonth(9);
        ZonedDateTime transactionDate4 = baseTransactionDate.withMonth(3).withDayOfMonth(13);
        ZonedDateTime transactionDate5 = baseTransactionDate.withMonth(4).withDayOfMonth(18);
        ZonedDateTime transactionDate6 = baseTransactionDate.withMonth(5).withDayOfMonth(19);
        ZonedDateTime transactionDate7 = baseTransactionDate.withMonth(6).withDayOfMonth(20);
        ZonedDateTime transactionDate8 = baseTransactionDate.withMonth(7).withDayOfMonth(21);
        ZonedDateTime transactionDate9 = baseTransactionDate.withMonth(8).withDayOfMonth(24);
        ZonedDateTime transactionDate10 = baseTransactionDate.withMonth(9).withDayOfMonth(25);

        Transaction transaction1 = new Transaction(transactionId1, user, transactionDate1).add(apple, 100);
        Transaction transaction2 = new Transaction(transactionId2, user, transactionDate2).add(apple, 100);
        Transaction transaction3 = new Transaction(transactionId3, user, transactionDate3).add(apple, 100);
        Transaction transaction4 = new Transaction(transactionId4, user, transactionDate4).add(apple, 100);
        Transaction transaction5 = new Transaction(transactionId5, user, transactionDate5).add(apple, 100);
        Transaction transaction6 = new Transaction(transactionId6, user, transactionDate6).add(apple, 100);
        Transaction transaction7 = new Transaction(transactionId7, user, transactionDate7).add(apple, 100);
        Transaction transaction8 = new Transaction(transactionId8, user, transactionDate8).add(apple, 100);
        Transaction transaction9 = new Transaction(transactionId9, user, transactionDate9).add(apple, 100);
        Transaction transaction10 = new Transaction(transactionId10, user, transactionDate10).add(apple, 100);

        TransactionRecord transactionRecord = new TransactionRecord()
                .add(transaction1)
                .add(transaction2)
                .add(transaction3)
                .add(transaction4)
                .add(transaction5)
                .add(transaction6)
                .add(transaction7)
                .add(transaction8)
                .add(transaction9)
                .add(transaction10);

        ValuedCustomerPurchaseFrequencyPolicy policy = new ValuedCustomerPurchaseFrequencyPolicy(transactionRecord, user);

        // When
        boolean actual = policy.isOk();

        // Then
        Assertions.assertThat(actual).isFalse();
    }
}
