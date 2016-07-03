package id.co.meda.survey;

import android.app.ListActivity;
import android.app.Service;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import id.co.meda.survey.database.DatabaseHelper;
import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.database.VoucherDatabase;

public class MyVoucherActivity extends ListActivity {

    ListView listView;
    private VoucherDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);
        init();
    }

    private void init(){
        database = new VoucherDatabase(this);
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
            cursor =  database.queryVoucher();
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            CursorAdapter adapter = new SimpleCursorAdapter(MyVoucherActivity.this,
                    R.layout.item_voucher,
                    cursor,
                    new String[]{DatabaseHelper.NAME_COLUMN},
                    new int[]{R.id.product_name},0);
            listView.setAdapter(adapter);
        }
    }

}
