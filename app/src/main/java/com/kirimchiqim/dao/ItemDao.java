package com.kirimchiqim.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kirimchiqim.entity.ItemModal;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(ItemModal modal);

    @Update
    void update(ItemModal modal);

    @Delete
    void delete(ItemModal modal);

    @Query("DELETE FROM mybudget")
    void deleteAllItems();

    @Query("SELECT * FROM mybudget ORDER BY id DESC")
    LiveData<List<ItemModal>> getAllItems();

}
