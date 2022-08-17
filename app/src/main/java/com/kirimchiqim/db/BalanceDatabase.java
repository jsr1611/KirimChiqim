package com.kirimchiqim.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kirimchiqim.dao.BalanceDao;
import com.kirimchiqim.entity.BalanceModal;

@Database(entities = {BalanceModal.class}, version = 1)
public abstract class BalanceDatabase extends RoomDatabase{

    private static BalanceDatabase instance;

    public abstract BalanceDao Dao();

    public static synchronized BalanceDatabase getInstance(Context context){

        if(instance == null){
            instance =
                    Room.databaseBuilder(context.getApplicationContext(),
                                    BalanceDatabase.class, "mybalance_database")
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
            new BalanceDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(BalanceDatabase instance){
            BalanceDao dao = instance.Dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
