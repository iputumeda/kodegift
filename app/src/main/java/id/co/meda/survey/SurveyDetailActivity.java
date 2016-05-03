package id.co.meda.survey;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.model.Barcode;
import id.co.meda.survey.model.Product;

public class SurveyDetailActivity extends AppCompatActivity {

    private static final String PRODUCT = "CURRENT PRODUCT";
    public static final String ID = "CURRENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);

        DetailTask task = new DetailTask();
        task.execute(getIntent().getIntExtra(ID, -1));
    }

    private class DetailTask extends AsyncTask<Integer, Void, Product>{

        TextView productTitle;
        ImageView productPhoto;
        TextView productCategory;
        TextView productDescription;

        @Override
        protected void onPreExecute() {
            productTitle = (TextView) findViewById(R.id.productTitle_tv_asd);
            productPhoto = (ImageView) findViewById(R.id.productPhoto_iv_asd);
            productCategory = (TextView) findViewById(R.id.productCategory_tv_asd);
            productDescription = (TextView) findViewById(R.id.productDescription_tv_asd);
        }

        @Override
        protected Product doInBackground(Integer... params) {
            try {
                SurveyDatabase database = new SurveyDatabase(SurveyDetailActivity.this);
                Cursor cursor = database.queryProducts(params[0]);
                cursor.moveToFirst();
                String name = cursor.getString(
                        cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.NAME_COLUMN)
                );
                String category = cursor.getString(
                        cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.CATEGORY_COLUMN)
                );
                String description = cursor.getString(
                        cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.DESCRIPTION_COLUMN)
                );
                byte[] photo = cursor.getBlob(
                        cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.PHOTO_COLUMN)
                );
                cursor.close();
                database.close();
                return new Product(name, category, description, photo, null);
            }catch (SQLiteException e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Product product) {
            if(product != null) {
                productTitle.setText(product.getName());
                productPhoto.setImageBitmap(getImage(product.getPhoto()));
                productCategory.setText(product.getCategory());
                productDescription.setText(product.getDescription());
            }else{
                Toast.makeText(SurveyDetailActivity.this, "product isn't found",Toast.LENGTH_SHORT).show();
            }
        }

        private Bitmap getImage(byte[] imageDatabase){
            return BitmapFactory.decodeByteArray(imageDatabase, 0, imageDatabase.length);
        }

    }

}
