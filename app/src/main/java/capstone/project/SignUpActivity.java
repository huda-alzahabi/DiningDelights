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

public class SignUpActivity extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUM = "number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    private static final String KEY_LOCATION = "location";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = sharedPreferences.getString(KEY_EMAIL, null);
        if (email != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    public void onClickSubmit(View view) {
        EditText name = findViewById(R.id.Name);
        EditText mobile = findViewById(R.id.Mobile);
        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);
        EditText password2 = findViewById(R.id.Password2);
        EditText location = findViewById(R.id.Location);

        String nameText = name.getText().toString();
        String mobileText = mobile.getText().toString();
        String emailText = email.getText().toString().toLowerCase();
        String pass1Text = password.getText().toString();
        String pass2Text = password2.getText().toString();
        String locationText = location.getText().toString();

        if (!(nameText.equals("") || mobileText.equals("") || pass1Text.equals("") || pass2Text.equals("") || locationText.equals(""))) {
            SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);

            SQLiteDatabase db1 = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db1.query("CLIENTS", new String[]{"EMAIL"}, "EMAIL=?", new String[]{emailText.toLowerCase()}, null, null, null);
            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Email Already Used!", Toast.LENGTH_SHORT).show();
            } else {
                if (pass1Text.equals(pass2Text)) {
                    try {
                        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
                        AppSQLiteOpenHelper.insertClient(db, nameText, mobileText, emailText, pass1Text, locationText);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_NAME, nameText);
                        editor.putString(KEY_NUM, mobileText);
                        editor.putString(KEY_EMAIL, emailText);
                        editor.putString(KEY_PASS, pass1Text);
                        editor.putString(KEY_LOCATION, locationText);
                        editor.apply();

                        Intent intent = new Intent(this, ChooseRestaurantActivity.class);
                        startActivity(intent);

                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Passwords does not match!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }

    }
}