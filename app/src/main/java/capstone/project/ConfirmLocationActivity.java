package capstone.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConfirmLocationActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    String option = "", itemname, allitems = "", client_name, client_location, client_mobile;
    double totalprice = 0;

    String progress = "InProgress";
    int qty;
    SQLiteDatabase db;
    SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_location);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);


        EditText locationView = findViewById(R.id.locationConfirmation);
        try {
            sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
            db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor1 = db.query("TEMP_ORDERS", new String[]{"_id", "ITEM", "PRICE", "QUANTITY"}, null, null, null, null, null);
            while (cursor1.moveToNext()) {
                itemname = cursor1.getString(1);
                int price = cursor1.getInt(2);
                qty = cursor1.getInt(3);
                totalprice += price * qty;
                allitems = allitems + " " + itemname + " " + qty;
            }


            Cursor cursor = db.query("CLIENTS", new String[]{"NAME", "MOBILE", "LOCATION"}, "EMAIL=?", new String[]{email.toLowerCase()}, null, null, null);
            if (cursor.moveToFirst()) {
                locationView.setText(cursor.getString(2));
                client_name = cursor.getString(0);
                client_mobile = cursor.getString(1);
            }

        } catch (Exception e) {
            Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
        }
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

    public void onClickConfirm(View view) {
        sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
        db = sqLiteOpenHelper.getWritableDatabase();

        Intent intent = new Intent(this, WaitingActivity.class);
        EditText ct = findViewById(R.id.locationConfirmation);
        client_location = ct.getText().toString();
        RadioGroup rg = findViewById(R.id.radioGroup);

        if (ct.getText().toString().isEmpty()) {
            Toast.makeText(this, "PLease fill in location or table no.!", Toast.LENGTH_SHORT).show();
        } else if (rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "PLease choose your option!", Toast.LENGTH_SHORT).show();
        } else {
            AppSQLiteOpenHelper.insertOrder(db, client_name, client_mobile, client_location, option, allitems, totalprice, progress);
            AppSQLiteOpenHelper.deleteCart(db, "TEMP_ORDERS");
            startActivity(intent);
        }


    }


    //onedit save the final text in DB of orders
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.dinein:
                if (checked)
                    option = "DineIn";
                break;
            case R.id.delivery:
                if (checked)
                    option = "Delivery";
                break;
        }
    }


}

