package capstone.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class ChooseCategoryRestActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    ImageView imageView;
    SQLiteDatabase db;
    SQLiteOpenHelper sqLiteOpenHelper;
    String option = "";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category_rest);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

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

    public void selectImage(View view) {
        imageView = findViewById(android.R.id.icon);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (data != null) {
                final Uri uri = data.getData();
                sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
                db = sqLiteOpenHelper.getWritableDatabase();
                useImage(uri);
            }
            Toast.makeText(this, "DONEEE!", Toast.LENGTH_SHORT).show();
        }
    }

    void useImage(Uri uri) {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.addPlateRest:
                if (checked)
                    option = "FOODMENU";
                break;
            case R.id.addStarterRest:
                if (checked)
                    option = "STARTERSMENU";
                break;
            case R.id.addDessertRest:
                if (checked)
                    option = "DESSERTSMENU";
                break;
            case R.id.addDrinkRest:
                if (checked)
                    option = "DRINKSMENU";
                break;
        }
    }

    public void onClickAdd(View view) {
        EditText name = findViewById(R.id.dishNameEdit);
        EditText price = findViewById(R.id.dishPriceEdit);

        AppSQLiteOpenHelper.insertItem(db, option, name.getText().toString(), Integer.parseInt(price.getText().toString()), bitmap);
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}