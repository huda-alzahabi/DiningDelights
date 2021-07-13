package capstone.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String TABLE_NAME = "TEMP_ORDERS";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //check if session active or no : no need to login again
        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = sharedPreferences.getString(KEY_EMAIL, null);
        if (email != null) {   //email is shared from prev login
            Intent intent = new Intent(this, ChooseRestaurantActivity.class);
            startActivity(intent);
        }
        SQLiteDatabase db2 = sqLiteOpenHelper.getWritableDatabase();
        db2.execSQL("DELETE FROM " + TABLE_NAME);
        db2.close();
    }

    public void onClickLogin(View view) {
        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);

        String emailText = email.getText().toString();
        String passText = password.getText().toString();

        if (!(emailText.equals("") || passText.equals(""))) {
            try {
                SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

                Cursor cursor = db.query("CLIENTS", new String[]{"EMAIL, PASSWORD"}, "EMAIL=?", new String[]{emailText.toLowerCase()}, null, null, null);
                if (cursor.moveToFirst()) {
                    String passStored = cursor.getString(1);

                    if (passStored.equals(passText)) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_EMAIL, emailText);
                        editor.apply();

                        Intent intent = new Intent(this, ChooseRestaurantActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Email or Password incorrect!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User Not Found!", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                db.close();

            } catch (Exception e) {
                Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickForgot(View view) {
        Intent intent = new Intent(this, ResetPassActivity.class);
        startActivity(intent);
    }


}