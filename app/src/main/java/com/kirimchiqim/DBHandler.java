package com.kirimchiqim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "kirmchiqimdb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mybudget";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String TIME_COL = "dateandtime";

    // below variable for our course description column.
    private static final String DESCRIPTION_COL = "description";

    // below variable is for our course tracks column.
    private static final String TYPE_COL = "type";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + TYPE_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewItem(String itemName, String itemDateTime, String itemDescription, String itemType) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, itemName);
        values.put(TIME_COL, itemDateTime);
        values.put(DESCRIPTION_COL, itemDescription);
        values.put(TYPE_COL, itemType);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<ItemModal> readItems(String itemType, String[] datetime) {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelect = "";
        String timeWhereCondition = "";
        if (datetime.length == 1 && datetime[0].matches("\\d{4}.\\d{2}.\\d{2}")) {
            timeWhereCondition = TIME_COL + String.format(" > '%s' AND ", datetime[0]);
        } else if (datetime.length == 2 && datetime[0].matches("\\d{4}.\\d{2}.\\d{2}") && datetime[1].matches("\\d{4}.\\d{2}.\\d{2}")) {
            timeWhereCondition = String.format("%1$s > '%2$s' AND %1$s < %3$s AND ", TIME_COL, datetime[0], datetime[1]);
        } else {
            timeWhereCondition = "";
        }
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorItems = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + timeWhereCondition + TYPE_COL + " IN (" + itemType + ");", null);

        // on below line we are creating a new array list.
        ArrayList<ItemModal> itemModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorItems.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                itemModalArrayList.add(new ItemModal(cursorItems.getString(1),
                        cursorItems.getString(4),
                        cursorItems.getString(2),
                        cursorItems.getString(3)));
            } while (cursorItems.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorItems.close();
        return itemModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
