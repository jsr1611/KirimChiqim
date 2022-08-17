package com.kirimchiqim.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kirimchiqim.entity.BalanceModal;


@Dao
public interface BalanceDao {

    @Insert
    void insert(BalanceModal modal);

    @Query("DELETE FROM mybalance")
    void deleteAllItems();

    @Query("SELECT * FROM mybalance ORDER BY id DESC LIMIT 1")
    BalanceModal getBalance();
}
