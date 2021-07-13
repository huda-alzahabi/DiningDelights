package capstone.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "APP_DB";
    private static final int DB_VERSION = 1;

    public AppSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CLIENTS(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "MOBILE TEXT," +
                "EMAIL TEXT," +
                "PASSWORD TEXT," +
                "LOCATION TEXT);");

        db.execSQL("CREATE TABLE KITCHENS(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "MOBILE TEXT," +
                "EMAIL TEXT," +
                "PASSWORD TEXT," +
                "RATE DOUBLE);");

        db.execSQL("CREATE TABLE TEMP_ORDERS(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ITEM TEXT," +
                "PRICE INTEGER," +
                "QUANTITY INTEGER); ");
        db.execSQL("CREATE TABLE ORDERS(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CLIENTNAME TEXT," +
                "MOBILE TEXT," +
                "LOCATION TEXT," +
                "OPTION TEXT," +
                "ITEMS TEXT," +
                "TOTAL DOUBLE," +
                "PROGRESS TEXT);");
        db.execSQL("CREATE TABLE FOODMENU(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "PRICE INTEGER," +
                "IMGID BLOB NOT NULL);");
        db.execSQL("CREATE TABLE DRINKSMENU(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "PRICE INTEGER," +
                "IMGID BLOB NOT NULL);");
        db.execSQL("CREATE TABLE STARTERSMENU(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "PRICE INTEGER," +
                "IMGID BLOB NOT NULL);");
        db.execSQL("CREATE TABLE DESSERTSMENU(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "PRICE INTEGER," +
                "IMGID BLOB NOT NULL);");

    }

    public static void insertTempOrder(SQLiteDatabase db, String item, int price, int qty) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ITEM", item);
        contentValues.put("PRICE", price);
        contentValues.put("QUANTITY", qty);
        db.insert("TEMP_ORDERS", null, contentValues);
    }


    public static void insertClient(SQLiteDatabase db, String name, String mobile, String email, String pass, String location) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("MOBILE", mobile);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", pass);
        contentValues.put("LOCATION", location);
        db.insert("CLIENTS", null, contentValues);
    }

    public static void insertKitchen(SQLiteDatabase db, String name, String mobile, String email, String pass, Integer rate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("MOBILE", mobile);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", pass);
        contentValues.put("RATE", rate);
        db.insert("KITCHENS", null, contentValues);
    }

    public static void deleteCart(SQLiteDatabase db, String table) {
        db.execSQL("DELETE FROM " + table);
    }


    public static boolean isEmpty(SQLiteDatabase db, String tableName) {
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        c.moveToFirst();
        int icount = c.getInt(0);
        if (icount > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void insertOrder(SQLiteDatabase db, String clientName, String mobile, String location, String opt, String items, Double total, String progress) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLIENTNAME", clientName);
        contentValues.put("MOBILE", mobile);
        contentValues.put("LOCATION", location);
        contentValues.put("OPTION", opt);
        contentValues.put("ITEMS", items);
        contentValues.put("TOTAL", total);
        contentValues.put("PROGRESS", progress);
        db.insert("ORDERS", null, contentValues);

    }

    public static void insertFoodItem(SQLiteDatabase db, String name, int price, int imgRes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ITEM", name);
        contentValues.put("PRICE", price);
        contentValues.put("QUANTITY", imgRes);
        db.insert("FOODMENU", null, contentValues);
    }

    public static void insertItem(SQLiteDatabase db, String table, String name, int price, Bitmap b) {
        byte[] a = getBitmapAsByteArray(b);
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("PRICE", price);
        cv.put("IMGID", a);
        db.insert(table, null, cv);
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public static void updateRate(SQLiteDatabase db, String RestName, double rate) {
        ContentValues cv = new ContentValues();
        cv.put("RATE", rate);
        db.update("KITCHENS", cv, "EMAIL=?", new String[]{RestName + "@rest.com"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
