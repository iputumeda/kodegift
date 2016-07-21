package id.co.meda.survey;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.database.VoucherDatabase;
import id.co.meda.survey.model.Barcode;
import id.co.meda.survey.model.Product;

public class SurveyActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Map<Integer, byte[]> photos;
    private String contents;
    private String formatName;
    private String productNameString;
    private String productCategoryString;
    private String productDescriptionString;
    private int currentImageId;
    EditText productName;
    Spinner productCategory;
    EditText productDescription;
    VoucherDatabase database;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        init();
    }

    public void init(){
        productName = (EditText) findViewById(R.id.product_name);
        productCategory = (Spinner) findViewById(R.id.product_category);
        productDescription = (EditText) findViewById(R.id.product_description);
        database = new VoucherDatabase(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        photos = new HashMap<>();
    }

    public void onClickBarcodeProduct(View view) {
        new IntentIntegrator(this).initiateScan();
    }

    public void onClickPhotoProduct(View view) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePhotoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
            currentImageId = view.getId();
        }
    }

    public void onClickSurveyProduct(View view) {
        productNameString = productName.getText().toString();
        productCategoryString = productCategory.getSelectedItem().toString();
        productDescriptionString = productDescription.getText().toString();
        Log.d("KODEGIFTDEBUG",productCategoryString);
        if(isValidToSaveSurvey()){
            SurveyTask task = new SurveyTask();
            task.execute();
        }else{
            Toast.makeText(this, "your survey is not complete",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidToSaveSurvey(){
        return !productNameString.equals("") && productNameString.length() != 0 &&
                !productCategoryString.equals("") && productCategoryString.length() != 0 &&
                !productDescriptionString.equals("") && productDescriptionString.length() != 0 &&
                photos.size() == 3;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){ //menerima hasil photo
            handlePhotoRequest(data);
        }else if (requestCode == IntentIntegrator.REQUEST_CODE) { // menerima hasil barcode
            handleBarcodeRequest(requestCode, resultCode, data);
        }
    }

    private void handlePhotoRequest(Intent data){
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        if(imageBitmap != null) {
            ImageView currentPhotoProduct = (ImageView) findViewById(currentImageId);
            currentPhotoProduct.setImageBitmap(imageBitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] currentPhoto = stream.toByteArray();

            photos.put(currentImageId, currentPhoto);

            Log.e("KODEGIFTDEBUG", "PHOTO IS GOTTEN AND NOT NULL = "+(currentPhoto!=null));
        }else{
            Log.e("KODEGIFTDEBUG", "PHOTO IS NOT GOTTEN");
        }
    }

    private void handleBarcodeRequest(int requestCode, int resultCode, Intent data){
        Log.e("KODEGIFTDEBUG", "REQUEST CODE TRUE FOR GETTING BARCODE");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            contents = scanResult.getContents();
            formatName = scanResult.getFormatName();
            Log.e("KODEGIFTDEBUG", "SCANRESULT IS NOT NULL, CONTENT AND FORMATNAME IS GOTTEN");
        } else {
            Log.e("KODEGIFTDEBUG", "SCANRESULT IS NULL, CONTENT AND FORMATNAME IS NOT GOTTEN");
        }
    }

    private class SurveyTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            SurveyDatabase databaseSurvey = null;
            try {
                databaseSurvey = new SurveyDatabase(SurveyActivity.this);
                List<byte[]> finalPhotos = new ArrayList<>();
                Set<Map.Entry<Integer, byte[]>> setOfPhotos = photos.entrySet();
                Iterator<Map.Entry<Integer, byte[]>> iteratorOfPhotos = setOfPhotos.iterator();
                while (iteratorOfPhotos.hasNext()){
                    byte[] currentPhoto = iteratorOfPhotos.next().getValue();
                    finalPhotos.add(currentPhoto);
                }
                Product product = new Product(productNameString, productCategoryString, productDescriptionString, finalPhotos, new Barcode(contents, formatName));
                long index = databaseSurvey.insertProduct(product);
                Log.e("KODEGIFTDEBUG", "SURVEY IS SAVED, INDEX: "+index+", PRODUCT: "+product);
                return true;
            }catch (SQLiteException e){
                Log.e("KODEGIFTDEBUG", "SURVEY IS NOTSAVED, PESAN: "+e.getMessage());
                return false;
            }finally {
                Log.e("KODEGIFTDEBUG", "SURVEY HAS BEEN CLOSED");
                databaseSurvey.close();
            }
        }

        @Override
        protected void onPostExecute(Boolean isSaved) {
            if(isSaved){
                startActivity(new Intent(SurveyActivity.this, SurveyResultActivity.class));
                finish();
            }else{
                Toast.makeText(SurveyActivity.this, "NOT SAVED", Toast.LENGTH_LONG).show();
            }
        }
    }



}
