package id.co.meda.survey;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.model.Barcode;
import id.co.meda.survey.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String PRODUCT = "CURRENT PRODUCT";
    public static final String ID = "CURRENT_ID";
    private SurveyDatabase database;
    private Cursor cursor;
    Toolbar toolbar;
    ImageView productPhoto;
    TextView cashBack;
    TextView stock;
    TextView productName;
    TextView productCategory;
    TextView productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initView();
        populateData();

//        DetailTask task = new DetailTask();
//        int id = getIntent().getIntExtra(ID, 0);
//        Log.e("SEVTRIMAMEN", "ID: " + id);
//        task.execute(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cashBack = (TextView) findViewById(R.id.cash_back);
        stock = (TextView) findViewById(R.id.stock);
        productName = (TextView) findViewById(R.id.product_name);
        productPhoto = (ImageView) findViewById(R.id.product_photo);
        productCategory = (TextView) findViewById(R.id.product_category);
        productDescription = (TextView) findViewById(R.id.product_description);
    }

//    private class DetailTask extends AsyncTask<Integer, Void, Product>{
//
//        @Override
//        protected void onPreExecute() {
//            productName = (TextView) findViewById(R.id.product_title);
//            productPhoto = (ImageView) findViewById(R.id.product_photo);
//            productCategory = (TextView) findViewById(R.id.product_category);
//            productDescription = (TextView) findViewById(R.id.product_description);
//        }
//
//        @Override
//        protected Product doInBackground(Integer... params) {
//            try {
//                database = new SurveyDatabase(ProductDetailActivity.this);
//                cursor = database.queryProducts(params[0]);
//                Log.e("SEVTRIMAMEN", "ROW: "+cursor.getCount());
//                cursor.moveToFirst();
//                String name = cursor.getString(
//                        cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.NAME_COLUMN)
//                );
//                String category = cursor.getString(
//                        cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.CATEGORY_COLUMN)
//                );
//                String description = cursor.getString(
//                        cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.DESCRIPTION_COLUMN)
//                );
//                byte[] photo = cursor.getBlob(
//                        cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.PHOTO_COLUMN)
//                );
//                Product product = new Product(name, category, description, photo, null);
//                return product;
//            }catch (SQLiteException e){
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Product product) {
//            if(product != null) {
//                productName.setText(product.getName());
//                productPhoto.setImageBitmap(getImage(product.getPhoto()));
//                productCategory.setText(product.getCategory());
//                productDescription.setText(product.getDescription());
//            }else{
//                Toast.makeText(ProductDetailActivity.this, "product isn't found",Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        private Bitmap getImage(byte[] imageDatabase){
//            return BitmapFactory.decodeByteArray(imageDatabase, 0, imageDatabase.length);
//        }
//
//    }

    private void populateData(){
        //TODO isikan data Product dari Product yang diberikan
//        long id = getIntent().getLongExtra(ID,0);
//        database = new SurveyDatabase(this);
//        Cursor cursor = database.queryProducts(id);
//        cursor.moveToFirst();
//        String name = cursor.getString(
//                cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.NAME_COLUMN)
//        );
//        String category = cursor.getString(
//                cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.CATEGORY_COLUMN)
//        );
//        String description = cursor.getString(
//                cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.DESCRIPTION_COLUMN)
//        );
//        byte[] photo = cursor.getBlob(
//                cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.PHOTO_COLUMN)
//        );
//        Product product = new Product(name,category,description,photo,null);
//        productName.setText(product.getName());
//        productPhoto.setImageBitmap(BitmapFactory.decodeByteArray(product.getPhoto(),0,product.getPhoto().length));
//        productCategory.setText(product.getCategory());
//        productDescription.setText(product.getDescription());
    }

//    public String getFilePath(String path){
//        File file = getFileStreamPath(path);
//        return file.toString();
//    }
//    private Bitmap getImage(byte[] imageDatabase){
//        return BitmapFactory.decodeByteArray(imageDatabase, 0, imageDatabase.length);
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        database.close();
//        cursor.close();
//        database.close();
//    }
}
