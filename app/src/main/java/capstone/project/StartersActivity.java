package capstone.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartersActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starters);
        StartersFragment startersFragment = new StartersFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.s_items, startersFragment);
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
                    Intent intent = new Intent(StartersActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(StartersActivity.this, KitchenOrdersActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}