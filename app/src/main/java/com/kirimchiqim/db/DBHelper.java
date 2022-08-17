package com.kirimchiqim.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.kirimchiqim.ItemModal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    public static final String DB_NAME = "kirmchiqimdb.db";
    // below int is our database version
    private static final int DB_VERSION = 5;
    //old 6
    // below variable is for our table name.
    private static final String TABLE_NAME = "mybudget";
    private static final String TABLE_NAME_BALANCE = "mybalance";
    // below variable is for our id column.
    private static final String COL_ID = "id";
    // below variable is for our item name column
    private static final String COL_NAME = "name";
    // below variable id for our item time column.
    private static final String COL_TIME = "dateandtime";
    private static final String COL_CREATEDAT = "createdAt";
    // below variable id for our item amount column.
    private static final String COL_AMOUNT = "amount";
    // below variable for our item description column.
    private static final String COL_DESCRIPTION = "description";
    // below variable is for our item type column.
    private static final String COL_TYPE = "type";
    private static final String COL_BALANCE = "balance";
    private static String TIME_CON = "";
    private Context mContext;

    // creating a constructor for our database handler.
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    public static int getDatabaseVersion() {
        return DB_VERSION;
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(DB_CREATE_TABLE);
        db.execSQL(DB_CREATE_TABLE_BALANCE);
    }

    public static String DB_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME + " TEXT,"
                    + COL_TYPE + " TEXT,"
                    + COL_AMOUNT + " TEXT,"
                    + COL_TIME + " TEXT,"
                    + COL_DESCRIPTION + " TEXT"
                    + COL_CREATEDAT + "TEXT)" ;

    public void dbAlterTable(SQLiteDatabase db){
        String sqlQuery = "PRAGMA table_info("+ TABLE_NAME +");";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        Log.i("DB Alter SQL", sqlQuery);
        boolean createdAtExists = false;
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    // on below line we are adding the data from cursor to our array list.
                    String colName = cursor.getString(cursor.getColumnIndex("name"));
                    if(colName.equalsIgnoreCase(COL_CREATEDAT))
                        createdAtExists = true;
                } while (cursor.moveToNext());
            }
        }
        if(!createdAtExists)
            db.execSQL(DB_ALTER_TABLE);
        if(cursor != null)
            cursor.close();
    }
    public static final String DB_ALTER_TABLE =
            "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_CREATEDAT + " TEXT;";

    public static String DB_CREATE_TABLE_BALANCE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BALANCE
                    + " ("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_BALANCE + " TEXT, "
                    + COL_CREATEDAT + " TEXT);";
    // + DESCRIPTION_COL + " TEXT)"

    private static final String DELETE_ITEMS = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String DELETE_BALACE_RECORDS = "DROP TABLE IF EXISTS " + TABLE_NAME_BALANCE;

    public double dbUpdateBalance(SQLiteDatabase db){
        double totalBalance = 0.0;
        String sqlSelect = "SELECT * FROM " + TABLE_NAME;
        Cursor items = db.rawQuery(sqlSelect, null);
        Log.i("Table Query", sqlSelect);
        // moving our cursor to first position.
        if (items.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                if(items.getString(items.getColumnIndex(COL_TYPE)).equalsIgnoreCase("income"))
                    totalBalance += Integer.parseInt(items.getString(items.getColumnIndex(COL_AMOUNT)));
                else
                    totalBalance -= Integer.parseInt(items.getString(items.getColumnIndex(COL_AMOUNT)));
//                Log.i("BALANCE CALC", "total: " + totalBalance
//                        + ", type: " + items.getString(items.getColumnIndex(COL_TYPE))
//                        + ", amount: " + items.getString(items.getColumnIndex(COL_AMOUNT))
//                        );
            } while (items.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        updateBalance(db, totalBalance);
        return totalBalance;
    }

    // this method is use to add new course to our sqlite database.
    public void addNewItem(ItemModal item) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(COL_NAME, item.getItemName());
        values.put(COL_TYPE, item.getItemType());
        values.put(COL_AMOUNT, item.getItemAmount());
        values.put(COL_TIME, item.getDateAndTime());
        values.put(COL_DESCRIPTION, item.getItemDescription());
        values.put(COL_CREATEDAT, item.getItemCreatedAt());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
        Log.i("Item Insert SQL", String.valueOf(values));

        double balance = readBalance();
        double numberSign = item.getItemType().equalsIgnoreCase("income") ? 1.0 : -1.0;
        balance += (item.getItemAmount() * numberSign);
        updateBalance(db, balance);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    private void updateBalance(SQLiteDatabase db, double balance) {
        if(!db.isOpen()){
            db = this.getWritableDatabase();
        }
        ContentValues values = new ContentValues();
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(COL_BALANCE, balance);
        values.put(COL_CREATEDAT, sdf.format(date));
        db.insert(TABLE_NAME_BALANCE, null, values);
        Log.i("Balance Update SQL", String.valueOf(values));
        db.close();
    }

    public int readTotalCount(String itemType) {
        SQLiteDatabase db = this.getReadableDatabase();
        int res = 0;
        String sqlSelect = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + TIME_CON + " " + COL_TYPE + " IN (" + itemType + ");";
        Log.i("Table Query", sqlSelect);
        Cursor cursor = db.rawQuery(sqlSelect, null);
        if (cursor.moveToFirst()) {
            do {
                res = cursor.getInt(0);
                Log.i("Total Item Count", String.valueOf(res));
                break;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return res;
    }

    // we have created a new method for reading all the courses.
    public ArrayList<ItemModal> readItems(String itemType, String[] datetime) {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelect = "";

        if (datetime.length == 2 && datetime[0].matches("\\d{4}-\\d{2}-\\d{2}") && datetime[1].isEmpty()) {
            // if start time was entered and the end time was not
            TIME_CON = COL_TIME + String.format(" >= '%s' AND ", datetime[0]);
        } else if (datetime.length == 2 && datetime[1].matches("\\d{4}-\\d{2}-\\d{2}") && datetime[0].isEmpty()) {
            // if end time was entered but start time was not
            TIME_CON = COL_TIME + String.format(" <= '%s' AND ", datetime[1]);
        } else if (datetime.length == 2 && datetime[0].matches("\\d{4}-\\d{2}-\\d{2}") && datetime[1].matches("\\d{4}-\\d{2}-\\d{2}")) {
            // if both start time and end time was entered
            TIME_CON = String.format("%1$s >= '%2$s' AND %1$s <= '%3$s' AND ", COL_TIME, datetime[0], datetime[1]);
//            System.out.println("length == 2: " + (datetime.length == 2) + " " + datetime[0].matches("\\d{4}-\\d{2}-\\d{2}") + " " + datetime[1].matches("\\d{4}-\\d{2}-\\d{2}"));
        } else {
            // if non of the start and end times was entered.
            TIME_CON = "";
        }

        // on below line we are creating a cursor with query to read data from database.
        sqlSelect = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + TIME_CON + COL_TYPE + " IN (" + itemType + ") " +
                " ORDER BY " + COL_TIME + " DESC;";
        Cursor cursorItems = db.rawQuery(sqlSelect, null);
        Log.i("Table Query", sqlSelect);
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
        db.close();
        return itemModalArrayList;
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        Log.i("DB Upgrade", "oldVersion: " + oldVersion + ", new version: " + newVersion);
        if(oldVersion < DB_VERSION){
            dbAlterTable(db);
        }
        if(oldVersion < DB_VERSION){
            db.execSQL(DB_CREATE_TABLE_BALANCE);
        }
        if(oldVersion < DB_VERSION){
            dbUpdateBalance(db);
        }
    }

    public int readSum(String itemType) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer res = 0;
        String sqlSelect = "SELECT SUM(" + COL_AMOUNT + ") AS amount FROM " + TABLE_NAME + " WHERE " + TIME_CON + " " + COL_TYPE + " IN (" + itemType + ");";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        if (cursor.moveToFirst()) {
            do {
                res = cursor.getInt(0);
                break;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.i("Read sum", res +", "+ sqlSelect);
        return res;
    }

    public double readBalance(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelect =
                "SELECT " + COL_BALANCE + ", " + COL_CREATEDAT +
                " FROM " + TABLE_NAME_BALANCE +
                " ORDER BY " + COL_ID +
                " DESC LIMIT 1;";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        String result = "";
        if(cursor.moveToFirst()){
            do {
                result = cursor.getString(cursor.getColumnIndex(COL_BALANCE));
                Log.i("Balance Sheet", cursor.getString(cursor.getColumnIndex(COL_BALANCE)) + " at " +cursor.getString(cursor.getColumnIndex(COL_CREATEDAT)));
                break;
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.i("Current Balance", result);
        return !result.isEmpty() ? Double.parseDouble(result) : 0;
    }

    public void backup(String outFileName){
        //database path
        final String inFileName = mContext.getDatabasePath(DB_NAME).toString();
        try {
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toast.makeText(mContext, "Backup Completed.", Toast.LENGTH_SHORT).show();
            Log.i("DB Backup", "Backup Completed.");
        } catch (Exception e) {
            Log.w("Error", "Unable to backup database. Retry");
            Toast.makeText(mContext, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void importDB(String inFileName) {
        final String outFileName = mContext.getDatabasePath(DB_NAME).toString(); //"/data/data/" + mContext.getPackageName() + "/databases/" + DB_NAME; //
        File curDb = new File(outFileName);
        File backupDb = new File(inFileName);
        try {
            Log.i("DB path src", inFileName);
            Log.i("DB path dst", outFileName);
//            File dbFile = new File(inFileName);
            FileChannel src = new FileInputStream(backupDb).getChannel();
            FileChannel dst = new FileOutputStream(curDb).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            Log.i("DB Import", "Import Completed.");
            Toast.makeText(mContext, "Import Completed.", Toast.LENGTH_LONG).show();
            if(curDb.exists()){

            }
            else {
                Log.w("Error", "Database does not exist.");
                Toast.makeText(mContext, "Error: No Current DB." , Toast.LENGTH_LONG).show();
            }


//            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
//            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = fis.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//                Log.i("Backup: ", (new String(buffer, StandardCharsets.UTF_8)));
//            }
//
//            // Close the streams
//            output.flush();
//            output.close();
//            fis.close();


        } catch (Exception e) {
            Log.w("Error", "Unable to import database. Retry");
            Toast.makeText(mContext, "Unable to import database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void clearAllData(SQLiteDatabase db) {
        db.execSQL(DELETE_ITEMS);
        db.execSQL(DELETE_BALACE_RECORDS);
        onCreate(db);
    }
}
