package capstone.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class OrderActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor cursor;
    private static final String SHARED_PREF_NAME = "totalSum";
    private static final String KEY_TOTAL = "total";
    int totalprice = 0;

    SharedPreferences orderSumPreferences;
    SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        cursor = db.query("TEMP_ORDERS", new String[]{"_id", "ITEM", "PRICE", "QUANTITY"}, null, null, null, null, null);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        TableLayout tl = findViewById(R.id.tableLayout);
        while (cursor.moveToNext()) {

            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            int qty = cursor.getInt(3);

            totalprice += price * qty;
            TableRow row = new TableRow(this);

            TableRow.LayoutParams rp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(rp);

            TextView nameTextView = new TextView(this);
            TextView qtyTextView = new TextView(this);
            TextView priceTextView = new TextView(this);

            nameTextView.setText(name + "");
            qtyTextView.setText(qty + "");
            priceTextView.setText("$" + price * qty);


            nameTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            nameTextView.setTextSize(25);
            nameTextView.setPadding(10, 10, 10, 10);


            qtyTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            qtyTextView.setTextSize(25);
            qtyTextView.setPadding(100, 10, 200, 10);

            priceTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            priceTextView.setTextSize(25);
            priceTextView.setPadding(100, 10, 170, 10);

            row.addView(nameTextView);
            row.addView(qtyTextView);
            row.addView(priceTextView);

            tl.addView(row);
        }


        orderSumPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        float total = orderSumPreferences.getFloat(KEY_TOTAL, 0f);
        TextView totalPrice = findViewById(R.id.actualPrice);
        totalPrice.setText("    " + totalprice + "$ ");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //app bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                return true;
            case R.id.cart:
                Intent intent2 = new Intent(this, OrderActivity.class);
                startActivity(intent2);
                return true;
            case R.id.help:
                Intent intent3 = new Intent(this, CallingWaiterActivity.class);
                startActivity(intent3);
                return true;
            case R.id.profile:
                Intent intent4 = new Intent(this, ProfileActivity.class);
                startActivity(intent4);
                return true;
            case R.id.rate:
                Intent intent5 = new Intent(this, RatingActivity.class);
                startActivity(intent5);
                return true;
            case R.id.logout:
                Intent intent6 = new Intent(this, MainActivity.class);
                //empty shared username
                sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
                db = sqLiteOpenHelper.getWritableDatabase();
                AppSQLiteOpenHelper.deleteCart(db, "TEMP_ORDERS");
                SharedPreferences settings = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                startActivity(intent6);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onClickOrder(View View) {
        Intent intent = new Intent(this, ConfirmLocationActivity.class);
        sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
        db = sqLiteOpenHelper.getWritableDatabase();
        if (AppSQLiteOpenHelper.isEmpty(db, "TEMP_ORDERS")) {
            Toast.makeText(this, "Please choose your order!", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}