package id.co.meda.survey;

import android.app.ListActivity;
import android.app.Service;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import id.co.meda.survey.database.SurveyDatabase;

public class MyVoucherActivity extends ListActivity {

    ListView listView;
    private SurveyDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);

        listView = getListView();
        VoucherAsyncTask task = new VoucherAsyncTask();
        task.execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }

    private class VoucherAsyncTask extends AsyncTask<Void, String, Cursor>{

        @Override
        protected void onPreExecute() {
            listView = getListView();
        }

        @Override
        protected Cursor doInBackground(Void... contexts) {
            database = new SurveyDatabase(MyVoucherActivity.this);
            cursor =  database.queryProducts();
            return cursor;
        }


        @Override
        protected void onPostExecute(Cursor cursor) {
            CursorAdapter adapter = new SimpleCursorAdapter(MyVoucherActivity.this,
                    R.layout.item_voucher,
                    cursor,
                    new String[]{SurveyDatabase.SurveyDatabaseHelper.NAME_COLUMN},
                    new int[]{R.id.product_name},0);
            listView.setAdapter(adapter);
        }
    }

}
