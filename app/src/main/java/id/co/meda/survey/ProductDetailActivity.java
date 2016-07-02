package id.co.meda.survey;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.model.Barcode;
import id.co.meda.survey.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String PRODUCT = "CURRENT PRODUCT";
    public static final String ID = "CURRENT_ID";

    private SurveyDatabase database;

    TextView productTitle;
    ImageView productPhoto;
    TextView productCategory;
    TextView productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initView();
        assessData();

    }

    public void initView(){
        productTitle = (TextView) findViewById(R.id.product_title);
        productPhoto = (ImageView) findViewById(R.id.product_photo);
        productCategory = (TextView) findViewById(R.id.product_category);
        productDescription = (TextView) findViewById(R.id.product_description);
    }

    public void assessData(){

        long id = getIntent().getLongExtra(ID,0);

        database = new SurveyDatabase(this);

        Cursor cursor = database.queryProducts(id);
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
        String photo = cursor.getString(
                cursor.getColumnIndex(SurveyDatabase.SurveyDatabaseHelper.PHOTO_COLUMN)
        );

        Product product = new Product(name,category,description,photo,new Barcode());

        productTitle.setText(product.getName());
        productPhoto.setImageDrawable(Drawable.createFromPath(getFilePath(product.getPhoto())));
        productCategory.setText(product.getCategory());
        productDescription.setText(product.getDescription());
    }

    public String getFilePath(String path){
        File file = getFileStreamPath(path);
        return file.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
