package id.co.meda.survey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.co.meda.survey.model.Product;

/**
 * Created by Faris on 27/04/2016.
 */
public class SurveyDatabase {

    private SQLiteDatabase database;

    public SurveyDatabase(Context context){
        database = (new SurveyDatabaseHelper(context)).getWritableDatabase();
    }

    public long insertProduct(Product product){

        ContentValues values = new ContentValues();
        values.put(SurveyDatabaseHelper.NAME_COLUMN, product.getName());
        values.put(SurveyDatabaseHelper.CATEGORY_COLUMN, product.getName());
        values.put(SurveyDatabaseHelper.DESCRIPTION_COLUMN, product.getName());
        values.put(SurveyDatabaseHelper.CATEGORY_COLUMN, product.getName());
        //TODO KURANG BARCODE
        return database.insert(SurveyDatabaseHelper.TABLE_NAME, null, values);

    }

    public int updateProduct(String oldProductName, Product product){

        ContentValues values = new ContentValues();
        values.put(SurveyDatabaseHelper.NAME_COLUMN, product.getName());
        values.put(SurveyDatabaseHelper.CATEGORY_COLUMN, product.getName());
        values.put(SurveyDatabaseHelper.DESCRIPTION_COLUMN, product.getName());
        values.put(SurveyDatabaseHelper.CATEGORY_COLUMN, product.getName());
        //TODO KURANG BARCODE
        return database.update(SurveyDatabaseHelper.TABLE_NAME, values, "NAME = ?", new String[]{oldProductName});

    }

    public int deleteProduct(String productName){
        return database.delete(SurveyDatabaseHelper.TABLE_NAME, "NAME = ?", new String[]{productName});
    }

    public Cursor queryProducts(){
        return database.query(SurveyDatabaseHelper.TABLE_NAME,
                new String[]{"_id",SurveyDatabaseHelper.NAME_COLUMN,
                        SurveyDatabaseHelper.CATEGORY_COLUMN,
                        SurveyDatabaseHelper.DESCRIPTION_COLUMN,
                        SurveyDatabaseHelper.DESCRIPTION_COLUMN,
                        SurveyDatabaseHelper.PHOTO_COLUMN}, null, null, null, null, null);
    }

    public Cursor queryProducts(long id){
        return database.query(SurveyDatabaseHelper.TABLE_NAME,
                new String[]{"_id",SurveyDatabaseHelper.NAME_COLUMN,
                        SurveyDatabaseHelper.CATEGORY_COLUMN,
                        SurveyDatabaseHelper.DESCRIPTION_COLUMN,
                        SurveyDatabaseHelper.DESCRIPTION_COLUMN,
                        SurveyDatabaseHelper.PHOTO_COLUMN}, "_id = ?", new String[]{id+""}, null, null, null);
    }

    public void close(){
        database.close();
    }

    public class SurveyDatabaseHelper extends SQLiteOpenHelper{

        private static final String TABLE_NAME = "SURVEY_TABLE";
        public static final String NAME_COLUMN = "NAME";
        public static final String CATEGORY_COLUMN = "CATEGORY";
        public static final String DESCRIPTION_COLUMN = "DESCRIPTION";
        public static final String PHOTO_COLUMN = "PHOTO";
        private static final String DATABASE_NAME = "Survey_Database";
        private static final int DATABASE_VERSION = 1;
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME_COLUMN+" TEXT, "+CATEGORY_COLUMN+" TEXT, "+DESCRIPTION_COLUMN+" TEXT, "+PHOTO_COLUMN+" TEXT);";

        public SurveyDatabaseHelper(Context context) {
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
