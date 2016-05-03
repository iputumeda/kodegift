package id.co.meda.survey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import id.co.meda.survey.helper.LocalDBHandler;
import id.co.meda.survey.helper.SessionManager;


public class MainMenuActivity extends AppCompatActivity {

    TextView userFullName;
    TextView userEmail;
    private LocalDBHandler db;
    private SessionManager session;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // SqLite database handler
        db = new LocalDBHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());

        //handle Logout
        btnLogout = (Button) findViewById(R.id.btnLogout);


        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // ambildata SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        userFullName = (TextView) findViewById(R.id.name_tv_amm);
        userEmail = (TextView) findViewById(R.id.email_tv_amm);

        //menyetel userFullName dari database server yang brisikan nama lengkap user
        userFullName.setText(name);
        //mnyetel userEmail dari database server yang berisikan email user
        userEmail.setText(email);

    }

    private void logoutUser(){
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
