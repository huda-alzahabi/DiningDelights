package capstone.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class AboutActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "myprefRest";
    private static final String KEY_EMAIL = "email";

    SQLiteDatabase db;
    SQLiteOpenHelper sqLiteOpenHelper;
    SharedPreferences sharedPreferencesRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        sharedPreferencesRest = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferencesRest.getString(KEY_EMAIL, null);

        TextView textViewName = findViewById(R.id.name);
        TextView textViewRate = findViewById(R.id.rate);
        TextView textViewNum = findViewById(R.id.phoneNum);
        TextView textViewEmail = findViewById(R.id.email);

        try {
            sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
            db = sqLiteOpenHelper.getWritableDatabase();
            Cursor cursor = db.query("KITCHENS", new String[]{"NAME", "MOBILE", "EMAIL", "RATE"}, "EMAIL=?", new String[]{email.toLowerCase()}, null, null, null, null);

            if (cursor.moveToFirst()) {
                textViewName.setText(cursor.getString(0));
                textViewRate.setText(cursor.getString(3));
                textViewNum.setText(cursor.getString(1));
                textViewEmail.setText(cursor.getString(2));
            }

        } catch (Exception e) {
            Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickPhone(View view) {
        TextView numView = findViewById(R.id.phoneNum);
        String num = numView.getText().toString();

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + num));
        startActivity(intent);
    }

    public void onClickEmail(View view) {
        TextView emailView = findViewById(R.id.email);
        String email = emailView.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rest, menu);
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
            case R.id.orderList:
                Intent intent2 = new Intent(this, KitchenOrdersActivity.class);
                startActivity(intent2);
                return true;
            case R.id.info:
                Intent intent4 = new Intent(this, AboutActivity.class);
                startActivity(intent4);
                return true;
            case R.id.logout:
                Intent intent6 = new Intent(this, MainActivity.class);
                SharedPreferences settings = getSharedPreferences("myprefRest", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                startActivity(intent6);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onClickEdit(View view) {
        Intent intent = new Intent(this, EditAboutActivity.class);
        startActivity(intent);
    }

    public void onClickEditMenu(View view) {
        Intent intent = new Intent(this, ChooseCategoryRestActivity.class);
        startActivity(intent);
    }

    public void onClickViewMenu(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}