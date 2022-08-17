package com.kirimchiqim.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "mybalance_table")
public class BalanceModal {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String balance;

    private String createdAt;

    public BalanceModal(String balance) {
        this.balance = balance;
        this.createdAt = (new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
