package id.co.meda.survey;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import id.co.meda.survey.database.VoucherDatabase;

public class MyVoucherActivity extends ListActivity {

    ListView listView;
    private VoucherDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);

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
            database = new VoucherDatabase(MyVoucherActivity.this);
            cursor =  database.queryVoucher();
            return cursor;
        }


        @Override
        protected void onPostExecute(Cursor cursor) {
            CursorAdapter adapter = new SimpleCursorAdapter(MyVoucherActivity.this, R.layout.item_voucher, cursor,
                    new String[]{VoucherDatabase.VoucherDatabaseHelper.NAME_COLUMN,VoucherDatabase.VoucherDatabaseHelper.VOUCHER_COLUMN},
                    new int[]{R.id.productName_tv_iv, R.id.voucher_tv_iv},0);
            listView.setAdapter(adapter);
        }
    }

}
