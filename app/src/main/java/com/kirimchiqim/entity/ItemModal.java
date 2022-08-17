package com.kirimchiqim.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "mybudget_table")
public class ItemModal {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String amount;

    private String description;

    private String type;

    private String dateandtime;

    private String createdAt;

    public ItemModal(String name, String amount, String description, String type, String dateandtime) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.dateandtime = dateandtime;
        this.createdAt = (new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
