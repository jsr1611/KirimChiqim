package com.kirimchiqim.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kirimchiqim.dao.ItemDao;
import com.kirimchiqim.entity.ItemModal;

@Database(entities = {ItemModal.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase instance;

    public abstract ItemDao Dao();

    public static synchronized ItemDatabase getInstance(Context context){

        if(instance == null){
            instance =
                    Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, "mybudget_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        PopulateDbAsyncTask(ItemDatabase instance){
            ItemDao dao = instance.Dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
