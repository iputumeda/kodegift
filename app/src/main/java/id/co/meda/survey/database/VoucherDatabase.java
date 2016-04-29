package id.co.meda.survey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.co.meda.survey.model.Voucher;

/**
 * Created by Faris on 28/04/2016.
 */
public class VoucherDatabase {

    private SQLiteDatabase database;

    public VoucherDatabase(Context context){
        database = (new VoucherDatabaseHelper(context)).getWritableDatabase();
    }

    public long insertVoucher(Voucher voucher){

        ContentValues values = new ContentValues();
        values.put(VoucherDatabaseHelper.NAME_COLUMN, voucher.getName());
        values.put(VoucherDatabaseHelper.VOUCHER_COLUMN, voucher.getVoucher());
        return database.insert(VoucherDatabaseHelper.TABLE_NAME, null, values);

    }

    public int updateVoucher(String oldVoucherName, Voucher voucher){

        ContentValues values = new ContentValues();
        values.put(VoucherDatabaseHelper.NAME_COLUMN, voucher.getName());
        values.put(VoucherDatabaseHelper.VOUCHER_COLUMN, voucher.getVoucher());
        return database.update(VoucherDatabaseHelper.TABLE_NAME, values, "NAME = ?", new String[]{oldVoucherName});

    }

    public int deleteVoucher(String voucherName){
        return database.delete(VoucherDatabaseHelper.TABLE_NAME, "NAME = ?", new String[]{voucherName});
    }

    public Cursor queryVoucher(){
        return database.query(VoucherDatabaseHelper.TABLE_NAME, new String[]{"_id",VoucherDatabaseHelper.NAME_COLUMN, VoucherDatabaseHelper.VOUCHER_COLUMN}, null, null, null, null, VoucherDatabaseHelper.NAME_COLUMN);
    }

    public class VoucherDatabaseHelper extends SQLiteOpenHelper {

        private static final String TABLE_NAME = "VOUCHER TABLE";
        public static final String NAME_COLUMN = "NAME";
        public static final String VOUCHER_COLUMN = "VOUCHER";
        private static final String DATABASE_NAME = "Voucher Database";
        private static final int DATABASE_VERSION = 1;
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME_COLUMN+" TEXT, "+VOUCHER_COLUMN+" INTEGER);";

        public VoucherDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
