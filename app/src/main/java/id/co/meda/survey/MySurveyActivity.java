package id.co.meda.survey;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.database.VoucherDatabase;

public class MySurveyActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_survey);

        ListView listView = getListView();
        VoucherDatabase database = new VoucherDatabase(this);
        Cursor cursor = database.queryVoucher();
        CursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[]{SurveyDatabase.SurveyDatabaseHelper.NAME_COLUMN},
                new int[]{android.R.id.text1},0);
        listView.setAdapter(adapter);

    }
}
