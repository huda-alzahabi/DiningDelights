package capstone.project;

import android.content.ContentValues;
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

public class EditAboutActivity extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "myprefRest";
    private static final String KEY_EMAIL = "email";
    String email;
    SharedPreferences sharedPreferencesRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about);

        sharedPreferencesRest = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        email = sharedPreferencesRest.getString(KEY_EMAIL, null);
    }

    public void onClickSubmitChangesRest(View view) {
        EditText editMobileRest = findViewById(R.id.editMobileRest);
        EditText oldPasswordRest = findViewById(R.id.oldPasswordRest);
        EditText newPasswordRest = findViewById(R.id.newPasswordRest);
        EditText confirmPasswordRest = findViewById(R.id.confirmPasswordRest);

        try {
            SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
            SQLiteDatabase db2 = sqLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.query("KITCHENS", new String[]{"PASSWORD"}, "EMAIL=?", new String[]{email.toLowerCase()}, null, null, null);
            ContentValues contentValues = new ContentValues();
            if (cursor.moveToFirst()) {
                if (!editMobileRest.getText().toString().equals("")) {
                    contentValues.put("MOBILE", editMobileRest.getText().toString());
                }

                if (!oldPasswordRest.getText().toString().equals("") && !newPasswordRest.getText().toString().equals("") && !confirmPasswordRest.getText().toString().equals("")) {
                    if (oldPasswordRest.getText().toString().equals(cursor.getString(0))) {
                        if (newPasswordRest.getText().toString().equals(confirmPasswordRest.getText().toString())) {
                            contentValues.put("PASSWORD", newPasswordRest.getText().toString());
                        }
                    } else {
                        Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "User error", Toast.LENGTH_SHORT).show();
            }
            db2.update("KITCHENS", contentValues, "EMAIL=?", new String[]{email.toLowerCase()});


        } catch (Exception e) {
            Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();

    }
}