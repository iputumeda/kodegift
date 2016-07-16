package id.co.meda.survey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import id.co.meda.survey.model.Product;

/**
 * Created by Faris on 27/04/2016.
 */
public class SurveyDatabase {

    private SQLiteDatabase rDatabase;
    private SQLiteDatabase wDatabase;
    private DatabaseHelper databaseHelper;

    public SurveyDatabase(Context context){
        databaseHelper = DatabaseHelper.getInstance(context);
        rDatabase = databaseHelper.getReadableDatabase();
        wDatabase = databaseHelper.getWritableDatabase();
    }

    public long insertProduct(Product product){

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME_COLUMN, product.getName());
        values.put(DatabaseHelper.CATEGORY_COLUMN, product.getCategory());
        values.put(DatabaseHelper.DESCRIPTION_COLUMN, product.getDescription());
        values.put(DatabaseHelper.PHOTO_COLUMN, product.getPhoto());
        values.put(DatabaseHelper.CONTENTS_BARCODE_COLUMN, product.getBarcode().getContents());
        values.put(DatabaseHelper.FORMAT_BARCODE_COLUMN, product.getBarcode().getFormat());
        return wDatabase.insert(DatabaseHelper.SURVEY_TABLE, null, values);

    }

    public int updateProduct(String oldProductName, Product product){

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME_COLUMN, product.getName());
        values.put(DatabaseHelper.CATEGORY_COLUMN, product.getName());
        values.put(DatabaseHelper.DESCRIPTION_COLUMN, product.getName());
        values.put(DatabaseHelper.CATEGORY_COLUMN, product.getName());
        values.put(DatabaseHelper.CONTENTS_BARCODE_COLUMN, product.getBarcode().getContents());
        values.put(DatabaseHelper.FORMAT_BARCODE_COLUMN, product.getBarcode().getFormat());
        return wDatabase.update(DatabaseHelper.SURVEY_TABLE, values, "NAME = ?", new String[]{oldProductName});

    }

    public int deleteProduct(String productName){
        return wDatabase.delete(DatabaseHelper.SURVEY_TABLE, "NAME = ?", new String[]{productName});
    }

    public Cursor queryProducts(){
        return rDatabase.query(DatabaseHelper.SURVEY_TABLE,
                new String[]{"_id",DatabaseHelper.NAME_COLUMN,
                        DatabaseHelper.CATEGORY_COLUMN,
                        DatabaseHelper.DESCRIPTION_COLUMN,
                        DatabaseHelper.PHOTO_COLUMN,
                        DatabaseHelper.CONTENTS_BARCODE_COLUMN,
                        DatabaseHelper.FORMAT_BARCODE_COLUMN}, null, null, null, null, null);
    }

    public Cursor queryProducts(long id){
        Log.e("SEVTRIMAMEN","QUERY PRODUCT WITH ID = "+id);
        return rDatabase.query(DatabaseHelper.SURVEY_TABLE,
                new String[]{"_id",DatabaseHelper.NAME_COLUMN,
                        DatabaseHelper.CATEGORY_COLUMN,
                        DatabaseHelper.DESCRIPTION_COLUMN,
                        DatabaseHelper.PHOTO_COLUMN,
                        DatabaseHelper.CONTENTS_BARCODE_COLUMN,
                        DatabaseHelper.FORMAT_BARCODE_COLUMN},
                "_id = ?", new String[]{id+""}, null, null, null);
    }

    public void close(){
        databaseHelper.close();
        rDatabase.close();
        wDatabase.close();
    }

}
