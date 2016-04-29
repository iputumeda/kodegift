package id.co.meda.survey;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.database.VoucherDatabase;

public class MySurveyActivity extends ListActivity {

    private SurveyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_survey);

        ListView listView = getListView();
        database = new SurveyDatabase(this);
        Cursor cursor = database.queryProducts();
        CursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[]{SurveyDatabase.SurveyDatabaseHelper.NAME_COLUMN},
                new int[]{android.R.id.text1},0);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intentSrveyDetail = new Intent(this, SurveyDetailActivity.class);
        intentSrveyDetail.putExtra(SurveyDetailActivity.ID, id);
        startActivity(intentSrveyDetail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
