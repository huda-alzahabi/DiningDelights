package capstone.project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
    }


    public void onClickReset(View view) {
        EditText emailView = findViewById(R.id.Email);
        EditText passView = findViewById(R.id.Password);
        EditText confirmView = findViewById(R.id.Password2);

        String email = emailView.getText().toString();
        String pass = passView.getText().toString();
        String confirm = confirmView.getText().toString();

        if (!(email.equals("") || pass.equals("") || confirm.equals(""))) {
            try {
                SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                SQLiteDatabase db2 = sqLiteOpenHelper.getReadableDatabase();
                Cursor cursor = db.query("CLIENTS", new String[]{"EMAIL"}, "EMAIL=?", new String[]{email.toLowerCase()}, null, null, null);
                if (cursor.moveToFirst()) {
                    if (pass.equals(confirm)) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("PASSWORD", pass);
                        db2.update("CLIENTS", contentValues, "EMAIL=?", new String[]{email.toLowerCase()});

                        Intent intent = new Intent(this, ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User Not Found!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }


    }
}