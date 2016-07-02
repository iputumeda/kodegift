package id.co.meda.survey;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.meda.survey.constants.Constants;
import id.co.meda.survey.drawer.NavigationDrawerFragment;
import id.co.meda.survey.drawer.NavigationDrawerListener;
import id.co.meda.survey.helper.LocalDBHandler;
import id.co.meda.survey.helper.SessionManager;


public class MainMenuActivity extends AppCompatActivity implements NavigationDrawerListener{

    private LocalDBHandler db;
    private SessionManager session;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
        populateData();
    }

    private void init(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationDrawerFragment ndf = new NavigationDrawerFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.navigation_container, ndf);
        transaction.commit();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(abdt);
        abdt.syncState();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void populateData(){
        // SqLite database handler
        db = new LocalDBHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logout();
        }

        // ambildata SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void survey() {
        Intent intentSurvey = new Intent(this, SurveyActivity.class);
        startActivity(intentSurvey);
    }

    @Override
    public void mySurvey() {
        Intent intentMySurvey = new Intent(this, MySurveyActivity.class);
        startActivity(intentMySurvey);
    }

    @Override
    public void myReedemItems() {
        Intent intentMyVoucher = new Intent(this, MyVoucherActivity.class);
        startActivity(intentMyVoucher);
    }

    @Override
    public void cashOutHistory() {

    }

    @Override
    public void helpCenter() {

    }

    @Override
    public void editProfile() {

    }

    @Override
    public void setting() {

    }

    @Override
    public void logout() {
        session.setLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void cashOut() {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CashBacksFragment(), "CASHBACKS");
        adapter.addFragment(new CategoryFragment(), "CATEGORY");
        viewPager.setAdapter(adapter);
    }

    public void onClickCategoryProduct(View view) {
        Toast.makeText(this,"Category Product : "+view.getTag(),Toast.LENGTH_LONG).show();
        Intent goToCategoryProducts = new Intent(this, CategoryProductsActivity.class);
        goToCategoryProducts.putExtra(Constants.TAG, view.getTag().toString());
        startActivity(goToCategoryProducts);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
