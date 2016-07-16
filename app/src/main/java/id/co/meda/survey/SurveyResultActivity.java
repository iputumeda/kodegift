package id.co.meda.survey;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import id.co.meda.survey.drawer.NavigationDrawerFragment;

public class SurveyResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);
        init();
    }

    private void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment ndf = new NavigationDrawerFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.navigation_container, ndf);
        transaction.commit();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(abdt);
        abdt.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onClickGoToMainMenu(View view) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}
