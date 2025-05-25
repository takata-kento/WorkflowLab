package com.example.transaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemTests {
    @Test
    void testUpdateName() {
        // Given
        Item item = new Item("apple", 100);
        String expectedName = "banana";
        Item updatedItem = item.updateName(expectedName);

        // When
        String actualName = updatedItem.name();

        // Then
        Assertions.assertThat(actualName)
                .isNotNull()
                .isEqualTo(expectedName);
    }

    @Test
    void testUpdatePrice() {
        // Given
        Item item = new Item("apple", 100);
        int expectedPrice = 200;
        Item updatedItem = item.updatePrice(expectedPrice);

        // When
        int actualPrice = updatedItem.price();

        // Then
        Assertions.assertThat(actualPrice)
                .isNotNull()
                .isEqualTo(expectedPrice);
    }
}
