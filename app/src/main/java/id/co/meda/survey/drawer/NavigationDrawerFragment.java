package id.co.meda.survey.drawer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.co.meda.survey.EditProfileActivity;
import id.co.meda.survey.LoginActivity;
import id.co.meda.survey.MySurveyActivity;
import id.co.meda.survey.MyVoucherActivity;
import id.co.meda.survey.R;
import id.co.meda.survey.SurveyActivity;
import id.co.meda.survey.helper.LocalDBHandler;
import id.co.meda.survey.helper.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment{

    private int[] imgSrc1;
    private int[] imgSrc2;
    private LocalDBHandler db;
    private SessionManager session;

    public NavigationDrawerFragment() {
        imgSrc1 = new int[4];
        imgSrc2 = new int[4];
        imgSrc1[0] = R.drawable.survey;
        imgSrc1[1] = R.drawable.my_survey;
        imgSrc1[2] = R.drawable.my_redeem_items;
        imgSrc1[3] = R.drawable.cash_out_history;
        imgSrc2[0] = R.drawable.help_center;
        imgSrc2[1] = R.drawable.edit_profile;
        imgSrc2[2] = R.drawable.setting;
        imgSrc2[3] = R.drawable.logout;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View theView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ListView listItem1 = (ListView) theView.findViewById(R.id.list_item1);
        ListView listItem2 = (ListView) theView.findViewById(R.id.list_item2);
        listItem1.setDividerHeight(0);
        listItem2.setDividerHeight(0);
        listItem1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDrawerItemMenuClick("ONE",position);
            }
        });
        listItem2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDrawerItemMenuClick("TWO",position);
            }
        });
        List<Item> items1 = getItems("ONE");
        List<Item> items2 = getItems("TWO");
        ListItemAdapter adapter1 = new ListItemAdapter(items1);
        ListItemAdapter adapter2 = new ListItemAdapter(items2);
        listItem1.setAdapter(adapter1);
        listItem2.setAdapter(adapter2);
        return theView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateData();
    }

    private void populateData(){
        // SqLite database handler
        db = new LocalDBHandler(getContext().getApplicationContext());
        // session manager
        session = new SessionManager(getContext().getApplicationContext());

        if (!session.isLoggedIn()) {
           //logout();
        }

        // ambildata SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
    }

    private void onDrawerItemMenuClick(String criteria, int position){
        if(criteria.equals("ONE")){
            switch (position){
                case 0:
                    //SURVEY
                    Intent intentSurvey = new Intent(getActivity(), SurveyActivity.class);
                    startActivity(intentSurvey);
                    break;
                case 1:
                    //MY SURVEY
                    Intent intentMySurvey = new Intent(getActivity(), MySurveyActivity.class);
                    startActivity(intentMySurvey);
                    break;
                case 2:
                    //MY REEDEMITEM
                    Intent intentMyVoucher = new Intent(getActivity(), MyVoucherActivity.class);
                    startActivity(intentMyVoucher);
                    break;
                case 3:
                    //MY CASHOUT HISTORY
                    break;
            }
        }else{
            switch (position){
                case 0:
                    //HELP CENTER
                    break;
                case 1:
                    //EDIT PROFIL
                    startActivity(new Intent(getActivity(), EditProfileActivity.class));
                    break;
                case 2:
                    //SETTING
                    break;
                case 3:
                    //LOGOUT
                    session.setLogin(false);
                    db.deleteUsers();
                    // Launching the login activity
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
            }
        }
    }

    private List<Item> getItems(String criteria){
        List<Item> items = new ArrayList<>();
        int[] imgSrcs;
        String[] labels;
        if(criteria.equals("ONE")){
            imgSrcs = imgSrc1;
            labels = getResources().getStringArray(R.array.labels1);
        }else{
            imgSrcs = imgSrc2;
            labels = getResources().getStringArray(R.array.labels2);
        }
        for(int i = 0 ; i < 4; i++){
            items.add(new Item(imgSrcs[i], labels[i]));
        }
        return items;
    }

    private class ListItemAdapter extends ArrayAdapter<Item>{

        public ListItemAdapter(List<Item> itemList) {
            super(NavigationDrawerFragment.this.getContext(), R.layout.item_navigation, itemList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View theView = NavigationDrawerFragment.this.getActivity().getLayoutInflater().inflate(R.layout.item_navigation, parent, false);
            ImageView imgItem = (ImageView) theView.findViewById(R.id.image_item);
            TextView label = (TextView) theView.findViewById(R.id.label_item);
            Item item = getItem(position);
            imgItem.setImageDrawable(getResources().getDrawable(item.imgSrc));
            label.setText(item.label);
            return theView;
        }
    }

    private class Item{
        public Item(int imgSrc, String label) {
            this.imgSrc = imgSrc;
            this.label = label;
        }

        int imgSrc;
        String label;
    }

}
