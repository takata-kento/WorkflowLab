package com.example.transaction;

public record Item(
    String name,
    int price
) {
    public Item updateName(String name) {
        return new Item(name, this.price);
    }

    public Item updatePrice(int price) {
        return new Item(this.name, price);
    }
}
