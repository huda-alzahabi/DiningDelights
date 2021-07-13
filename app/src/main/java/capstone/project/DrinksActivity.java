package capstone.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class DrinksActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);
        DrinksFragment drinksFragment = new DrinksFragment();
        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.dr_items, drinksFragment);
        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft2.addToBackStack(null);
        ft2.commit();
        Button button = findViewById(R.id.vieworder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String email1 = sharedPreferences.getString(KEY_EMAIL, null);

                if (email1 != null) {
                    Intent intent = new Intent(DrinksActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DrinksActivity.this, KitchenOrdersActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}