package id.co.meda.survey;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import id.co.meda.survey.database.VoucherDatabase;

public class MyVoucherActivity extends ListActivity {

    private VoucherDatabase database;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);
        listView = getListView();
        database = new VoucherDatabase(MyVoucherActivity.this);
        Cursor cursor = database.queryVoucher();
        CursorAdapter adapter = new SimpleCursorAdapter(MyVoucherActivity.this, R.layout.item_voucher, cursor,
                new String[]{VoucherDatabase.VoucherDatabaseHelper.NAME_COLUMN,VoucherDatabase.VoucherDatabaseHelper.VOUCHER_COLUMN},
                new int[]{R.id.productName_tv_iv, R.id.voucher_tv_iv},0);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

}
