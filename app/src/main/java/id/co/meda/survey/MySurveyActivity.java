package id.co.meda.survey;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.database.VoucherDatabase;

public class MySurveyActivity extends ListActivity {

    ListView listView;
    private SurveyDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_survey);

        SurveyAsyncTask task = new SurveyAsyncTask();
        task.execute();

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
        cursor.close();
    }

    private class SurveyAsyncTask extends AsyncTask<Void, String, Cursor>{

        @Override
        protected void onPreExecute() {
            listView = getListView();
        }

        @Override
        protected Cursor doInBackground(Void... params) {
            database = new SurveyDatabase(MySurveyActivity.this);
            cursor = database.queryProducts();
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            CursorAdapter adapter = new SimpleCursorAdapter(MySurveyActivity.this, android.R.layout.simple_list_item_1, cursor,
                    new String[]{SurveyDatabase.SurveyDatabaseHelper.NAME_COLUMN},
                    new int[]{android.R.id.text1},0);
            listView.setAdapter(adapter);
        }
    }
}
