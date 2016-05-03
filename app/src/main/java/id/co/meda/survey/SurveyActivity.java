package id.co.meda.survey;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.model.Barcode;
import id.co.meda.survey.model.Product;
import id.co.meda.survey.model.Voucher;

public class SurveyActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private byte[] imageToDatabase;
    private String contents;
    private String formatName;
    private String productNameString;
    private String productCategoryString;
    private String productDescriptionString;
    EditText productName;
    EditText productCategory;
    EditText productDescription;

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
        imageToDatabase = null;
    }

    public void onClickBarcodeProduct(View view) {
        new IntentIntegrator(this).initiateScan();
    }

    public void onClickPhotoProduct(View view) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePhotoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onClickSurveyProduct(View view) {
        productNameString = productName.getText().toString();
        productCategoryString = productCategory.getText().toString();
        productDescriptionString = productDescription.getText().toString();

        SurveyTask task = new SurveyTask();
        task.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){ //menerima hasil photo

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if(imageBitmap != null) {
                //konvert Bitmap ke array of bytes
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                imageToDatabase = stream.toByteArray();
                Log.e("SEVTRIMAMEN", "PHOTO IS GOTTEN");
            }else{
                Log.e("SEVTRIMAMEN", "PHOTO IS NOT GOTTEN");
            }

        }else if (requestCode == IntentIntegrator.REQUEST_CODE) { // menerima hasil barcode

            Log.e("SEVTRIMAMEN", "REQUEST CODE TRUE FOR GETTING BARCODE");
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult != null) {
                contents = scanResult.getContents();
                formatName = scanResult.getFormatName();
                Log.e("SEVTRIMAMEN", "SCANRESULT IS NOT NULL, CONTENT AND FORMATNAME IS GOTTEN");
            } else {
                Log.e("SEVTRIMAMEN", "SCANRESULT IS NULL, CONTENT AND FORMATNAME IS NOT GOTTEN");
            }

        }
    }

    private class SurveyTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SurveyDatabase databaseSurvey = new SurveyDatabase(SurveyActivity.this);
                Product product = new Product(productNameString, productCategoryString, productDescriptionString, imageToDatabase, new Barcode(contents, formatName));
                databaseSurvey.insertProduct(product);
                databaseSurvey.close();
                Log.e("SEVTRIMAMEN", "SURVEY IS SAVED");
                return true;
            }catch (SQLiteException e){
                Log.e("SEVTRIMAMEN", "SURVEY IS NOTSAVED, PESAN: "+e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSaved) {
            if(isSaved) {
                Toast.makeText(SurveyActivity.this, "survey is saved", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }



}
