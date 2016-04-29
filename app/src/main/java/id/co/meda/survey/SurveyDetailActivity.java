package id.co.meda.survey;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import id.co.meda.survey.model.Product;

public class SurveyDetailActivity extends AppCompatActivity {

    private static final String PRODUCT = "CURRENT PRODUCT";

    TextView productTitle;
    ImageView productPhoto;
    TextView productCategory;
    TextView productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);

        initView();
        assessData();

    }

    public void initView(){
        productTitle = (TextView) findViewById(R.id.productTitle_tv_asd);
        productPhoto = (ImageView) findViewById(R.id.productPhoto_iv_asd);
        productCategory = (TextView) findViewById(R.id.productCategory_tv_asd);
        productDescription = (TextView) findViewById(R.id.productDescription_tv_asd);
    }

    public void assessData(){
        Product product = (Product) getIntent().getSerializableExtra(PRODUCT);
        productTitle.setText(product.getName());
        productPhoto.setImageDrawable(Drawable.createFromPath(getFilePath(product.getPhoto())));
        productCategory.setText(product.getCategory());
        productDescription.setText(product.getDescription());
    }

    public String getFilePath(String path){
        File file = getFileStreamPath(path);
        return file.toString();
    }

}
