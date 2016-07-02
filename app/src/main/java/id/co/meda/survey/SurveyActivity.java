package id.co.meda.survey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import id.co.meda.survey.database.VoucherDatabase;
import id.co.meda.survey.model.Voucher;

public class SurveyActivity extends AppCompatActivity {

    EditText productName;
    EditText productCategory;
    EditText productDescription;
    VoucherDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        init();

    }

    public void init(){
        productName = (EditText) findViewById(R.id.product_name);
        productCategory = (EditText) findViewById(R.id.product_category);
        productDescription = (EditText) findViewById(R.id.product_description);
        database = new VoucherDatabase(this);

    }

    public void onClickBarcodeProduct(View view) {

        //TODO beralih ke pengambilan barcode

    }

    public void onClickPhotoProduct(View view) {

        //TODO beralih ke pengambilan foto

    }

    public void onClickSurveyProduct(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
