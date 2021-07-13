package capstone.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DishRestActivity extends AppCompatActivity {
    String fname;
    int fprice;
    Bitmap fimage;
    public final static String ITEM_NAME = "name_of_item";
    public static final String ITEM_PRICE = "price_of_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_rest);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        fname = intent.getStringExtra(ITEM_NAME);
        fprice = intent.getIntExtra(ITEM_PRICE, 0);
        fimage = intent.getParcelableExtra("BitmapImage");
        TextView dishName = findViewById(R.id.plateName);
        TextView dishPrice = findViewById(R.id.platePrice);
        ImageView dishImage = findViewById(R.id.plateImage);
        dishName.setText(fname);
        dishPrice.setText(fprice + "$");
        dishImage.setImageBitmap(fimage);

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
                Intent intent = new Intent(this, MenuActivity.class);
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
            case R.id.info:
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

}