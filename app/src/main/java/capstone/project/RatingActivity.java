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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class RatingActivity extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        TextView emailView = findViewById(R.id.emailRate);
        TextView numView = findViewById(R.id.phoneNumRate);

        numView.setText("7118432");
        emailView.setText("lavilla@rest.com");

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

    public void onClickPhone(View view) {
        TextView numView = findViewById(R.id.phoneNumRate);
        String num = numView.getText().toString();

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + num));
        startActivity(intent);
    }

    public void onClickEmail(View view) {
        TextView emailView = findViewById(R.id.emailRate);
        String email = emailView.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        startActivity(intent);
    }

    public void onClickSubmitRate(View view) {
        sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
        db = sqLiteOpenHelper.getWritableDatabase();
        RatingBar rb = findViewById(R.id.ratingBar1);
        float ratingVal = rb.getRating();
        AppSQLiteOpenHelper.updateRate(db, "lavilla", ratingVal);
        Intent intent = new Intent(this, ThankYouActivity.class);
        startActivity(intent);
    }

}