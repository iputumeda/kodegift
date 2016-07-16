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

import id.co.meda.survey.database.DatabaseHelper;
import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.model.Barcode;
import id.co.meda.survey.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT = "CURRENT PRODUCT";
    Toolbar toolbar;
    ImageView productPhoto;
    TextView cashBack;
    TextView stock;
    TextView productName;
    TextView productCategory;
    TextView productDescription;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initView();
        populateData();

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
    private void populateData() {
        product = (Product) getIntent().getSerializableExtra(PRODUCT);
        Log.e("SEVTRIMAMEN","PRODUCT = "+product);
        productPhoto.setImageBitmap(BitmapFactory.decodeByteArray(product.getPhoto(),0,product.getPhoto().length));
        productName.setText(product.getName());
        productCategory.setText(product.getCategory());
    }
    private class PDAAsync extends AsyncTask<Integer, Void, Product>{

        private SurveyDatabase database;
        private Cursor cursor;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            database = new SurveyDatabase(ProductDetailActivity.this);
        }

        @Override
        protected Product doInBackground(Integer... params) {
            Log.e("SEVTRIMAMEN","PRODUCT POSITION IN DO BACKGROUND = "+params[0]);
            Cursor data = database.queryProducts(params[0]);
            Log.e("SEVTRIMAMEN","CURSOR IS NULL IN DO BACKGROUND = "+(cursor==null));
            Product product = getProduct(data);
            Log.e("SEVTRIMAMEN","PRODUCT IN DETAIL PRODUCT ACTIVITY = "+product);
            return null;
        }

        @Override
        protected void onPostExecute(Product product) {
            super.onPostExecute(product);
            productPhoto.setImageBitmap(BitmapFactory.decodeByteArray(product.getPhoto(),0,product.getPhoto().length));
            productName.setText(product.getName());
            productCategory.setText(product.getCategory());
        }

        private Product getProduct(Cursor cursor){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN));
            String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CATEGORY_COLUMN));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION_COLUMN));
            byte[] photo = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.PHOTO_COLUMN));
            String contentBarcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CONTENTS_BARCODE_COLUMN));
            String formatBarcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORMAT_BARCODE_COLUMN));
            return new Product(name, category, description, photo, new Barcode(contentBarcode, formatBarcode));
        }

    }
}
