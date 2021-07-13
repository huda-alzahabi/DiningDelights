package capstone.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

    }

    public void onClickLoginRest(View view) {
        Intent intent = new Intent(this, LoginSignUpRestActivity.class);
        startActivity(intent);
    }

    public void onClickLoginClient(View view) {
        Intent intent = new Intent(this, LoginSignUpClientActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}