package id.co.meda.survey.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Farisi on 03/07/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper helper;

    public static final String SURVEY_TABLE = "SURVEY_TABLE";
    public static final String VOUCHER_TABLE = "VOUCHER_TABLE";
    public static final String VOUCHER_COLUMN = "VOUCHER";
    public static final String NAME_COLUMN = "NAME";
    public static final String CATEGORY_COLUMN = "CATEGORY";
    public static final String DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String PHOTO_COLUMN = "PHOTO";
    public static final String CONTENTS_BARCODE_COLUMN = "CONTENTS_BARCODE";
    public static final String FORMAT_BARCODE_COLUMN = "FORMAT_BARCODE";
    private static final String DATABASE_NAME = "KODE_GIFT_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_SURVEY_TABLE = "CREATE TABLE "+SURVEY_TABLE+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME_COLUMN+" TEXT, "+CATEGORY_COLUMN+" TEXT, "+DESCRIPTION_COLUMN+" TEXT, "+PHOTO_COLUMN+" BLOB, "+CONTENTS_BARCODE_COLUMN+" TEXT, "+FORMAT_BARCODE_COLUMN+" TEXT);";
    private static final String CREATE_VOUCHER_TABLE = "CREATE TABLE "+VOUCHER_TABLE+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME_COLUMN+" TEXT, "+VOUCHER_COLUMN+" INTEGER);";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context){
        if(helper == null) {
            Context appCtx = context.getApplicationContext();
            helper = new DatabaseHelper(appCtx);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SURVEY_TABLE);
        db.execSQL(CREATE_VOUCHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}