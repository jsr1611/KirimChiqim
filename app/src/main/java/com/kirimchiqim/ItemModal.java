package com.kirimchiqim;

public class ItemModal {
    private String itemName;
    private String DateAndTime;
    private String itemType;
    private String itemDescription;
    private int id;

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

    public void setId(int id) {
        this.id = id;
    }

    public ItemModal(String itemName, String dateAndTime, String itemType, String itemDescription) {
        this.itemName = itemName;
        DateAndTime = dateAndTime;
        this.itemType = itemType;
        this.itemDescription = itemDescription;
    }
}
