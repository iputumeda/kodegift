package id.co.meda.survey;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.meda.survey.utility.Utility;
import id.co.meda.survey.database.DatabaseHelper;
import id.co.meda.survey.database.SurveyDatabase;
import id.co.meda.survey.model.Barcode;
import id.co.meda.survey.model.Product;

public class MySurveyActivity extends AppCompatActivity{

    private ProgressDialog progressDialog;
    Toolbar toolbar;
    RecyclerView list;
    TextView empty;
    private SurveyDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_survey);
        init();
        populateData();
        Log.e("KODEGIFTDEBUG","ONCREATE");
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

    private void init(){
        database = new SurveyDatabase(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        empty = (TextView) findViewById(R.id.empty);
        empty.setVisibility(View.GONE);
        list = (RecyclerView) findViewById(R.id.list_of_survey);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    private void populateData(){
        SurveyAsyncTask asyncTask = new SurveyAsyncTask();
        asyncTask.execute();
        Log.e("KODEGIFTDEBUG","POPULATE");
    }

    private List<Product> getProducts(){
        Log.e("KODEGIFTDEBUG", "GET PRODUCT");
        List<Product> products = new ArrayList<>();
        SurveyAsyncTask asyncTask = new SurveyAsyncTask();
        asyncTask.execute();
        if (cursor != null) {
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN));
                String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CATEGORY_COLUMN));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION_COLUMN));
                byte[] photo = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.PHOTO_COLUMN));
                String contentBarcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CONTENTS_BARCODE_COLUMN));
                String formatBarcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORMAT_BARCODE_COLUMN));
                Product product = new Product(name, category, description, Utility.getListFromArrayOfByte(photo), new Barcode(contentBarcode, formatBarcode));
                products.add(product);
                Log.e("KODEGIFTDEBUG", "LOOP : "+product.getName());
            }
        }else{
            Log.e("KODEGIFTDEBUG", "1 CURSOR IS NULL");
        }
        Log.e("KODEGIFTDEBUG", "SIZE : "+products.size());
        return products;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cursor != null)
            cursor.close();
        database.close();
    }

    public void showEmpty(boolean condition){
        if(condition){
            empty.setVisibility(View.VISIBLE);
        }else{
            empty.setVisibility(View.GONE);
        }
    }

    private static class MyRecycleAdapter extends RecyclerView.Adapter<MySurveyActivity.MyRecycleAdapter.MyViewHolder>{

        private List<Product> products;
        private Activity activity;
        private Listener listener;

        public MyRecycleAdapter(List<Product> products, Activity activity){
            this.products = products;
            this.activity = activity;
            listener = null;
        }

        @Override
        public MySurveyActivity.MyRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View theView = activity.getLayoutInflater().inflate(R.layout.item_survey,parent,false);
            return new MyViewHolder(theView);
        }

        @Override
        public void onBindViewHolder(final MySurveyActivity.MyRecycleAdapter.MyViewHolder holder, final int position) {
            final Product product = products.get(position);
            byte[] photo = product.getPhotos().get(0);
            Bitmap bmp = BitmapFactory.decodeByteArray(photo,0, photo.length);
            holder.productPhoto.setImageBitmap(bmp);
            holder.productName.setText(product.getName());
            holder.productCategory.setText(product.getCategory());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick(position, holder.cardView, product);
                    }
                }
            });
        }

        public void setListener(Listener listener){
            this.listener = listener;
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            ImageView productPhoto;
            TextView productName;
            TextView productCategory;

            public MyViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView;
                productPhoto = (ImageView) itemView.findViewById(R.id.product_photo);
                productName = (TextView) itemView.findViewById(R.id.product_name);
                productCategory = (TextView) itemView.findViewById(R.id.product_category);
            }
        }

        interface Listener{
            void onItemClick(int position, ViewGroup root, Product product);
        }

        public int size(){
            return products.size();
        }

        public boolean isEmpty(){
            return size()==0;
        }

    }

    private class SurveyAsyncTask extends AsyncTask<Void, Void, List<Product>>{

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MySurveyActivity.this);
            progressDialog.show();
        }

        @Override
        protected List<Product> doInBackground(Void... params) {
            cursor = database.queryProducts();
            List<Product> products = new ArrayList<>();
            while(cursor.moveToNext()){
                Product product = getProduct(cursor);
                products.add(product);
                Log.e("KODEGIFTDEBUG", "LOOP PRODUCT: "+product);
            }
            return products;
        }

        private Product getProduct(Cursor cursor){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN));
            String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CATEGORY_COLUMN));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION_COLUMN));
            byte[] photo = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.PHOTO_COLUMN));
            String contentBarcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CONTENTS_BARCODE_COLUMN));
            String formatBarcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORMAT_BARCODE_COLUMN));
            return new Product(name, category, description, Utility.getListFromArrayOfByte(photo), new Barcode(contentBarcode, formatBarcode));
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            progressDialog.dismiss();
            if(products.size() > 0) {
                MyRecycleAdapter adapter = new MyRecycleAdapter(products, MySurveyActivity.this);
                adapter.setListener(new MyRecycleAdapter.Listener() {
                    @Override
                    public void onItemClick(int position, ViewGroup root, Product product) {
                        Intent intentSrveyDetail = new Intent(MySurveyActivity.this, ProductDetailActivity.class);
                        intentSrveyDetail.putExtra(ProductDetailActivity.PRODUCT, product);
                        Log.e("KODEGIFTDEBUG","MY SURVEY ON PRODUCT = "+product);
                        startActivity(intentSrveyDetail);
                    }
                });
                list.setAdapter(adapter);
                showEmpty(adapter.isEmpty());
                Log.e("KODEGIFTDEBUG","PRODUCT IS NOT NOL, PRODUCT IS GOTTEN");
            }else{
                Log.e("KODEGIFTDEBUG","PRODUCT IS NOL, PRODUCT IS NOT GOTTEN");
            }
        }
    }

}
