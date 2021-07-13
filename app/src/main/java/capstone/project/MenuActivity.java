package capstone.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public class MenuActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        FoodMenuFragment menuFragment = new FoodMenuFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.items, menuFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
        Button button = findViewById(R.id.vieworder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String email1 = sharedPreferences.getString(KEY_EMAIL, null);

                if (email1 != null) {
                    Intent intent = new Intent(MenuActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MenuActivity.this, KitchenOrdersActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


}