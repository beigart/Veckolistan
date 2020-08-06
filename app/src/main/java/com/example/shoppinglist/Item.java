package com.example.shoppinglist;

public class Item {
    private String itemName;
    private String amountName;

    public Item(String expenseName, String expenseSum){
        itemName = expenseName;
        amountName = expenseSum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAmountName() {
        return amountName;
    }

    public void setAmountName(String amountName) {
        this.amountName = amountName;
    }
}
