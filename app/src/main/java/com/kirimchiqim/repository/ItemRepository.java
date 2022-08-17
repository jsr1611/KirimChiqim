package com.kirimchiqim.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kirimchiqim.dao.ItemDao;
import com.kirimchiqim.db.ItemDatabase;
import com.kirimchiqim.entity.ItemModal;

import java.util.List;

public class ItemRepository {
    private ItemDao dao;
    private LiveData<List<ItemModal>> allItems;

    public ItemRepository(Application application){
        ItemDatabase database = ItemDatabase.getInstance(application);
        dao = database.Dao();
        allItems = dao.getAllItems();
    }

    public void insert(ItemModal modal){
        new InsertItemAsyncTask(dao).execute(modal);
    }


    private static class InsertItemAsyncTask extends AsyncTask<ItemModal, Void, Void> {
        private ItemDao dao;

        private InsertItemAsyncTask(ItemDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ItemModal... itemModals) {
            dao.insert(itemModals[0]);
            return null;
        }
    }


    private static class DeleteAllItemsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ItemDao dao;

        private DeleteAllItemsAsyncTask(ItemDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllItems();
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<ItemModal, Void, Void> {
        private ItemDao dao;

        private DeleteItemAsyncTask(ItemDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ItemModal... itemModals) {
            dao.delete(itemModals[0]);
            return null;
        }
    }

    private static class InsertItemAsyncTask extends AsyncTask<ItemModal, Void, Void> {
        private ItemDao dao;

        private InsertItemAsyncTask(ItemDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ItemModal... itemModals) {
            dao.insert(itemModals[0]);
            return null;
        }
    }

}
