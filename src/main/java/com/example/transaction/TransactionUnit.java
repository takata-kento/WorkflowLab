package com.example.transaction;

public class TransactionUnit {
    private final String transactionId;
    private final int index;
    private final Item item;
    private final boolean canceled;
    private final int quantity;

    TransactionUnit(String transactionId, int index, Item item, int quantity) {
        this(transactionId, index, item, quantity, false);
    }

    private TransactionUnit(String transactionId, int index, Item item, int quantity, boolean canceled) {
        if (transactionId == null || transactionId.isEmpty()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or empty");
        }
        if (index == 0 || index < 0) {
            throw new IllegalArgumentException("Index must be one or more");
        }
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be one or more");
        }

        this.transactionId = transactionId;
        this.index = index;
        this.item = item;
        this.quantity = quantity;
        this.canceled = canceled;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public int getIndex() {
        return this.index;
    }

    public Item getItem() {
        return new Item(this.item.name(), this.item.price());
    }

    public int getQuantity() {
        return this.quantity;
    }

    TransactionUnit updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be one or more");
        }
        if (this.canceled) {
            throw new IllegalStateException("Cannot update quantity of a canceled transaction unit");
        }

        return new TransactionUnit(this.transactionId, this.index, this.item, newQuantity);
    }

    TransactionUnit cancel() {
        return new TransactionUnit(this.transactionId, this.index, this.item, this.quantity, true);
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        if (!(another instanceof TransactionUnit)) return false;

        TransactionUnit that = (TransactionUnit) another;

        if (!this.transactionId.equals(that.transactionId)) return false;
        if (this.index != that.index) return false;
        if (!this.item.equals(that.item)) return false;
        if (this.canceled != that.canceled) return false;
        if (this.quantity != that.quantity) return false;

        return true;
    }
}
