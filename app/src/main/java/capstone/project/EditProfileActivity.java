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

public class EditProfileActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    String email;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        email = sharedPreferences.getString(KEY_EMAIL, null);

    }

    public void onClickSubmitChanges(View view) {

        EditText editMobile = findViewById(R.id.editMobile);
        EditText editLocation = findViewById(R.id.editLocation);
        EditText oldPassword = findViewById(R.id.oldPassword);
        EditText newPassword = findViewById(R.id.newPassword);
        EditText confirmPassword = findViewById(R.id.confirmPassword);

        try {
            SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
            SQLiteDatabase db2 = sqLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.query("CLIENTS", new String[]{"PASSWORD"}, "EMAIL=?", new String[]{email.toLowerCase()}, null, null, null);
            ContentValues contentValues = new ContentValues();
            if (cursor.moveToFirst()) {
                if (!editMobile.getText().toString().equals("")) {
                    contentValues.put("MOBILE", editMobile.getText().toString());
                }
                if (!editLocation.getText().toString().equals("")) {
                    contentValues.put("LOCATION", editLocation.getText().toString());
                }
                if (!oldPassword.getText().toString().equals("") && !newPassword.getText().toString().equals("") && !confirmPassword.getText().toString().equals("")) {
                    if (oldPassword.getText().toString().equals(cursor.getString(0))) {
                        if (newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                            contentValues.put("PASSWORD", newPassword.getText().toString());
                        }
                    } else {
                        Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "User error", Toast.LENGTH_SHORT).show();
            }
            db2.update("CLIENTS", contentValues, "EMAIL=?", new String[]{email.toLowerCase()});


        } catch (Exception e) {
            Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();

    }

}