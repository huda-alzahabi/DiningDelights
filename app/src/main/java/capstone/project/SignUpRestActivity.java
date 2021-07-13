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

public class SignUpRestActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "myprefRest";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUM = "number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";

    SharedPreferences sharedPreferencesRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_rest);

        sharedPreferencesRest = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferencesRest.getString(KEY_EMAIL, null);
        if (email != null) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
    }

    public void onClickSubmit(View view) {
        EditText name = findViewById(R.id.RestName);
        EditText mobile = findViewById(R.id.RestMobile);
        EditText email = findViewById(R.id.RestEmail);
        EditText password = findViewById(R.id.RestPassword);
        EditText password2 = findViewById(R.id.RestPassword2);


        String nameText = name.getText().toString();
        String mobileText = mobile.getText().toString();
        String emailText = email.getText().toString().toLowerCase();
        String pass1Text = password.getText().toString();
        String pass2Text = password2.getText().toString();


        if (!(nameText.equals("") || mobileText.equals("") || pass1Text.equals("") || pass2Text.equals(""))) {
            SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);

            SQLiteDatabase db1 = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db1.query("KITCHENS", new String[]{"EMAIL"}, "EMAIL=?", new String[]{emailText.toLowerCase()}, null, null, null);
            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Email Already Used!", Toast.LENGTH_SHORT).show();
            } else {
                if (!emailText.matches(".*@rest.com")) {
                    Toast.makeText(this, "Email should be example@rest.com!", Toast.LENGTH_SHORT).show();
                } else {

                    if (pass1Text.equals(pass2Text)) {
                        try {
                            SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
                            AppSQLiteOpenHelper.insertKitchen(db, nameText, mobileText, emailText, pass1Text, 0);


                            SharedPreferences.Editor editor = sharedPreferencesRest.edit();
                            editor.putString(KEY_NAME, nameText);
                            editor.putString(KEY_NUM, mobileText);
                            editor.putString(KEY_EMAIL, emailText);
                            editor.putString(KEY_PASS, pass1Text);
                            editor.apply();

                            Intent intent = new Intent(this, AboutActivity.class);
                            startActivity(intent);

                            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Passwords does not match!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        } else {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }

    }

}










