package id.co.meda.survey.drawer;


import android.content.Context;
import android.os.Bundle;
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
import java.util.List;

import id.co.meda.survey.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment{

    private int[] imgSrc1;
    private int[] imgSrc2;
    private NavigationDrawerListener listener;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NavigationDrawerListener){
            listener = (NavigationDrawerListener) context;
        }
    }

    private void onDrawerItemMenuClick(String criteria, int position){
        if(criteria.equals("ONE")){
            switch (position){
                case 0:
                    listener.survey();
                    break;
                case 1:
                    listener.mySurvey();
                    break;
                case 2:
                    listener.myReedemItems();
                    break;
                case 3:
                    listener.cashOutHistory();
                    break;
            }
        }else{
            switch (position){
                case 0:
                    listener.helpCenter();
                    break;
                case 1:
                    listener.editProfile();
                    break;
                case 2:
                    listener.setting();
                    break;
                case 3:
                    listener.logout();
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
