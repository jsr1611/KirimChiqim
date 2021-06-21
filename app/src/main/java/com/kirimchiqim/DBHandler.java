package com.kirimchiqim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "kirmchiqimdb";

    // below int is our database version
    private static final int DB_VERSION = 6;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mybudget";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our item name column
    private static final String NAME_COL = "name";

    // below variable id for our item time column.
    private static final String TIME_COL = "dateandtime";

    // below variable id for our item amount column.
    private static final String AMOUNT_COL = "amount";

    // below variable for our item description column.
    private static final String DESCRIPTION_COL = "description";

    // below variable is for our item type column.
    private static final String TYPE_COL = "type";
    private static String TIME_CON = "";

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
                + TYPE_COL + " TEXT,"
                + AMOUNT_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }


    // this method is use to add new course to our sqlite database.
    public void addNewItem(String itemName, String itemType, String itemAmount, String itemDateTime, String itemDescription) {

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
        values.put(TYPE_COL, itemType);
        values.put(AMOUNT_COL, itemAmount);
        values.put(TIME_COL, itemDateTime);
        values.put(DESCRIPTION_COL, itemDescription);


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public int readTotalCount(String itemType) {
        SQLiteDatabase db = this.getReadableDatabase();
        int res = 0;
        String sqlSelect = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + TIME_CON + " " + TYPE_COL + " IN (" + itemType + ");";
        System.out.println("total count SSSSSSSSSSSSSSSSSQL total:" + sqlSelect);
        Cursor cursor = db.rawQuery(sqlSelect, null);
        if (cursor.moveToFirst()) {
            do {
                res = cursor.getInt(0);
                //System.out.println("\n\ntotalcount RESSSSSSSSSSSSSSSSSSS: " + res + "\n\n");
                break;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return res;
    }

    // we have created a new method for reading all the courses.
    public ArrayList<ItemModal> readItems(String itemType, String[] datetime) {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelect = "";
        //String timeWhereCondition = "";

        if (datetime.length == 2 && datetime[0].matches("\\d{4}-\\d{2}-\\d{2}") && datetime[1].isEmpty()) {
            // if start time was entered and the end time was not
            TIME_CON = TIME_COL + String.format(" >= '%s' AND ", datetime[0]);
        } else if (datetime.length == 2 && datetime[1].matches("\\d{4}-\\d{2}-\\d{2}") && datetime[0].isEmpty()) {
            // if end time was entered but start time was not
            TIME_CON = TIME_COL + String.format(" <= '%s' AND ", datetime[1]);
        } else if (datetime.length == 2 && datetime[0].matches("\\d{4}-\\d{2}-\\d{2}") && datetime[1].matches("\\d{4}-\\d{2}-\\d{2}")) {
            // if both start time and end time was entered
            TIME_CON = String.format("%1$s >= '%2$s' AND %1$s <= '%3$s' AND ", TIME_COL, datetime[0], datetime[1]);
            System.out.println("length == 2: " + (datetime.length == 2) + " " + datetime[0].matches("\\d{4}-\\d{2}-\\d{2}") + " " + datetime[1].matches("\\d{4}-\\d{2}-\\d{2}"));
        } else {
            // if non of the start and end times was entered.
            TIME_CON = "";
        }
        System.out.println("\n\n\nTime Condition: " + TIME_CON);

        // on below line we are creating a cursor with query to read data from database.
        sqlSelect = "SELECT * FROM " + TABLE_NAME + " WHERE " + TIME_CON + TYPE_COL + " IN (" + itemType + ");";
        Cursor cursorItems = db.rawQuery(sqlSelect, null);
        System.out.println("\n\n\n\nSQL Select " + sqlSelect);
        // on below line we are creating a new array list.
        ArrayList<ItemModal> itemModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorItems.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                itemModalArrayList.add(new ItemModal(cursorItems.getString(1),
                        cursorItems.getString(2),
                        Integer.parseInt(cursorItems.getString(3)),
                        cursorItems.getString(4),
                        cursorItems.getString(5)));
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

    public int readSum(String itemType) {
        Integer res = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelect = "SELECT SUM(" + AMOUNT_COL + ") AS amount FROM " + TABLE_NAME + " WHERE " + TIME_CON + " " + TYPE_COL + " IN (" + itemType + ");";
        System.out.println("ssssssssssssssssssql: " + sqlSelect);
        Cursor cursor = db.rawQuery(sqlSelect, null);
        if (cursor.moveToFirst()) {
            do {
                res = cursor.getInt(0);
                break;
            } while (cursor.moveToNext());
        }
        cursor.close();
        System.out.println(" sum ressssssssssssssssssssssss: " + res);
        return res;
    }
}
