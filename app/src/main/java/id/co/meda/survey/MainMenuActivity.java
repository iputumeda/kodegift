package id.co.meda.survey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainMenuActivity extends AppCompatActivity {

    TextView userFullName;
    TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        userFullName = (TextView) findViewById(R.id.name_tv_amm);
        userEmail = (TextView) findViewById(R.id.email_tv_amm);

        //TODO mnyetel userEmail dari database server yang berisikan email user
        //TODO menyetel userFullName dari database server yang brisikan nama lengkap user

    }

    public void onClickSurvey(View view) {
        Intent intentSurvey = new Intent(this, SurveyActivity.class);
        startActivity(intentSurvey);
    }

    public void onClickMySurvey(View view) {
        Intent intentMySurvey = new Intent(this, MySurveyActivity.class);
        startActivity(intentMySurvey);
    }

    public void onClickMyVoucher(View view) {
        Intent intentMyVoucher = new Intent(this, MyVoucherActivity.class);
        startActivity(intentMyVoucher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.ubahProfile_mo){
            Intent intentChangeProfile = new Intent(this, ChangeProfileActivity.class);
            startActivity(intentChangeProfile);
        }
        return super.onOptionsItemSelected(item);
    }
}
