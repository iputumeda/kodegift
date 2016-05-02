package id.co.meda.survey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.database.VoucherDatabase;
import id.co.meda.survey.model.Product;
import id.co.meda.survey.model.Voucher;

public class SurveyActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private byte[] imageToDatabase;
    EditText productName;
    EditText productCategory;
    EditText productDescription;
    SurveyDatabase databaseSurvey;
    VoucherDatabase databaseVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        init();

    }

    public void init(){
        productName = (EditText) findViewById(R.id.productName_et_as);
        productCategory = (EditText) findViewById(R.id.productCategory_et_as);
        productDescription = (EditText) findViewById(R.id.productDescription_et_as);
        databaseSurvey = new SurveyDatabase(this);
        databaseVoucher = new VoucherDatabase(this);
        imageToDatabase = null;
    }

    public void onClickBarcodeProduct(View view) {

        //TODO beralih ke pengambilan barcode

    }

    public void onClickPhotoProduct(View view) {

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePhotoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    public void onClickSurveyProduct(View view) {

        String productNameString = productName.getText().toString();
        String productCategoryString = productCategory.getText().toString();
        String productDescriptionString = productDescription.getText().toString();

        Product product = new Product(productNameString, productCategoryString, productDescriptionString,imageToDatabase,null);
        databaseSurvey.insertProduct(product);

        Voucher voucher = new Voucher(productNameString, 100);
        databaseVoucher.insertVoucher(voucher);

        Toast.makeText(this, "survey is saved", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //konvert Bitmap ke array of bytes
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            imageToDatabase = stream.toByteArray();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseSurvey.close();
        databaseVoucher.close();
    }
}
