package capstone.project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
    ArrayList<String> words = new ArrayList<String>();
    String s = "";
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Intent intent = getIntent();
        String str = intent.getStringExtra("BUNDLE");
        if (str != null) {
            String w[] = str.split("\\s");
            for (int i = 0; i < w.length; i++)
                words.add(w[i]);
            id = words.get(0);
            int n = words.size();
            TextView tv1 = findViewById(R.id.cname);
            TextView tv2 = findViewById(R.id.cmob);
            TextView tv3 = findViewById(R.id.cloc);
            TextView tv4 = findViewById(R.id.copt);
            TextView tv5 = findViewById(R.id.cit);
            TextView tv6 = findViewById(R.id.ctot);
            TextView tv7 = findViewById(R.id.cprog);

            tv1.setText("Client: " + words.get(1) + " " + words.get(2));
            tv2.setText("Phone Number: " + words.get(3));
            tv3.setText("Location: " + words.get(4));
            tv4.setText("Option: " + words.get(5));

            for (int i = 6; i < n - 2; i++)
                s = s + " " + words.get(i);
            tv5.setText("Items: " + s);
            tv6.setText("Total Bill: " + words.get(n - 2));
            tv7.setText("Progress: " + words.get(n - 1));

        } else {
            Toast.makeText(this, "No orders yet!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDoneClicked(View view) {
        ContentValues values = new ContentValues();
        values.put("PROGRESS", "Done");
        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        db.update("ORDERS", values, "_id=?", new String[]{id});
        Intent donut = new Intent(OrderDetailActivity.this, KitchenOrdersActivity.class);
        startActivity(donut);

    }

}