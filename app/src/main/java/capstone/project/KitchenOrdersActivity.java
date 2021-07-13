package capstone.project;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

public class KitchenOrdersActivity extends ListActivity {
    ArrayList<String> Orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_orders);

        DefaultAct();
    }


    public void onDineInClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (view.getId() == R.id.checkbox_dine) {
            if (checked) {
                getOrderDetails("DineIn");
            } else {
                DefaultAct();
            }
        }

    }

    public void onDelClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (view.getId() == R.id.checkbox_deliv) {
            if (checked) {
                getOrderDetails("Delivery");

            } else {
                DefaultAct();
            }
        }
    }

    public String getOrderDetails(String option) throws SQLException {

        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        String orderDetail = "";
        ListView simpleList;
        Orders.clear();
        simpleList = findViewById(android.R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Orders);
        simpleList.setAdapter(arrayAdapter);
        Cursor mCursor =
                db.query("ORDERS", new String[]{
                                "_id",
                                "CLIENTNAME",
                                "MOBILE",
                                "LOCATION",
                                "OPTION",
                                "ITEMS",
                                "TOTAL",
                                "PROGRESS",},
                        "OPTION" + "=?",
                        new String[]{option},
                        null, null, "_id", null);

        if (mCursor.moveToFirst()) {
            do {
                orderDetail = "";

                orderDetail += mCursor.getString(mCursor.getColumnIndexOrThrow("_id")) + " " +
                        mCursor.getString(mCursor.getColumnIndexOrThrow("CLIENTNAME")) + " " +
                        mCursor.getString(mCursor.getColumnIndexOrThrow("MOBILE")) + " " +
                        mCursor.getString(mCursor.getColumnIndexOrThrow("LOCATION")) + " " +
                        mCursor.getString(mCursor.getColumnIndexOrThrow("OPTION")) + " " +
                        mCursor.getString(mCursor.getColumnIndexOrThrow("ITEMS")) + " " +
                        mCursor.getDouble(mCursor.getColumnIndexOrThrow("TOTAL")) + " " +
                        mCursor.getString(mCursor.getColumnIndexOrThrow("PROGRESS"));

                Orders.add(orderDetail);
            } while (mCursor.moveToNext());
        }
        simpleList = findViewById(android.R.id.list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Orders);
        simpleList.setAdapter(arrayAdapter);

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent donut = new Intent(KitchenOrdersActivity.this, OrderDetailActivity.class);
                Log.d("INTENT", Orders.get(i));
                donut.putExtra("BUNDLE", Orders.get(i));
                startActivity(donut);
            }
        });
        return orderDetail;
    }


    public void DefaultAct() {
        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        String orderDetail = "";
        ListView simpleList;
        Orders.clear();
        simpleList = findViewById(android.R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Orders);
        simpleList.setAdapter(arrayAdapter);
        Cursor mCursor =
                db.query("ORDERS", new String[]{
                                "_id",
                                "CLIENTNAME",
                                "MOBILE",
                                "LOCATION",
                                "OPTION",
                                "ITEMS",
                                "TOTAL",
                                "PROGRESS",},
                        null,
                        null,
                        null, null, "_id", null);

        if (mCursor.moveToFirst()) {
            do {
                orderDetail = "";

                orderDetail += mCursor.getString(0) + " " +
                        mCursor.getString(1) + " " +
                        mCursor.getString(2) + " " +
                        mCursor.getString(3) + " " +
                        mCursor.getString(4) + " " +
                        mCursor.getString(5) + " " +
                        mCursor.getDouble(6) + " " +
                        mCursor.getString(7);

                Orders.add(orderDetail);

            } while (mCursor.moveToNext());
        }

        simpleList = findViewById(android.R.id.list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Orders);
        simpleList.setAdapter(arrayAdapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent donut = new Intent(KitchenOrdersActivity.this, OrderDetailActivity.class);
                donut.putExtra("BUNDLE", Orders.get(i));
                startActivity(donut);
            }
        });
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
    }
}