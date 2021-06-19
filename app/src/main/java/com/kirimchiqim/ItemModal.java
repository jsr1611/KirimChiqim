package com.kirimchiqim;

public class ItemModal {
    private String itemName;
    private String DateAndTime;
    private String itemType;
    private String itemDescription;
    private int id;
    private int itemAmount;
    private int totalCount;
    private int totalAmount;

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDateAndTime() {
        return DateAndTime;
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getId() {
        return id;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDateAndTime(String dateAndTime) {
        DateAndTime = dateAndTime;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemModal(String itemName, String itemType, int itemAmount, String dateAndTime, String itemDescription) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemAmount = itemAmount;
        this.DateAndTime = dateAndTime;
        this.itemDescription = itemDescription;
    }
}
