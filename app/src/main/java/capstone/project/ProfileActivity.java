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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        TextView nameView = findViewById(R.id.nameInfo);
        TextView mobileView = findViewById(R.id.mobileInfo);
        TextView emailView = findViewById(R.id.emailInfo);
        TextView locationView = findViewById(R.id.locationInfo);

        try {
            SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.query("CLIENTS", new String[]{"NAME, MOBILE, EMAIL, LOCATION"}, "EMAIL=?", new String[]{email.toLowerCase()}, null, null, null);
            if (cursor.moveToFirst()) {
                nameView.setText(cursor.getString(0));
                mobileView.setText(cursor.getString(1));
                emailView.setText(cursor.getString(2));
                locationView.setText(cursor.getString(3));
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
                AppSQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
                SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
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

    public void onClickEditProf(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void onClickChangePass(View view) {
        Intent intent = new Intent(this, ResetPassActivity.class);
        startActivity(intent);
    }

    public void onClickLogout(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}