package capstone.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSignUpRestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up_rest);
    }


    public void onClickLoginRestSide(View view) {
        Intent intent = new Intent(this, LoginRestActivity.class);
        startActivity(intent);
    }

    public void onClickSignUpRestSide(View view) {
        Intent intent = new Intent(this, SignUpRestActivity.class);
        startActivity(intent);
    }
}