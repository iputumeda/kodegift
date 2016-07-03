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

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase rDatabase;
    private SQLiteDatabase wDatabase;

    public VoucherDatabase(Context context){
        databaseHelper = DatabaseHelper.getInstance(context);
        rDatabase = databaseHelper.getReadableDatabase();
        wDatabase = databaseHelper.getWritableDatabase();
    }

    public long insertVoucher(Voucher voucher){

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME_COLUMN, voucher.getName());
        values.put(DatabaseHelper.VOUCHER_COLUMN, voucher.getVoucher());
        return wDatabase.insert(DatabaseHelper.VOUCHER_TABLE, null, values);

    }

    public int updateVoucher(String oldVoucherName, Voucher voucher){

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME_COLUMN, voucher.getName());
        values.put(DatabaseHelper.VOUCHER_COLUMN, voucher.getVoucher());
        return wDatabase.update(DatabaseHelper.VOUCHER_TABLE, values, "NAME = ?", new String[]{oldVoucherName});

    }

    public int deleteVoucher(String voucherName){
        return wDatabase.delete(DatabaseHelper.VOUCHER_TABLE, "NAME = ?", new String[]{voucherName});
    }

    public Cursor queryVoucher(){
        return rDatabase.query(DatabaseHelper.VOUCHER_TABLE, new String[]{"_id",DatabaseHelper.NAME_COLUMN, DatabaseHelper.VOUCHER_COLUMN}, null, null, null, null, DatabaseHelper.NAME_COLUMN);
    }

    public void close(){
        databaseHelper.close();
        rDatabase.close();
        wDatabase.close();
    }

}
