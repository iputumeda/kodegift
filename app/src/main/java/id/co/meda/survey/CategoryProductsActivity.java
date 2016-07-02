package id.co.meda.survey;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.meda.survey.constants.Constants;
import id.co.meda.survey.model.Product;

public class CategoryProductsActivity extends AppCompatActivity {

    RecyclerView listOfProducts;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);
        init();
        populateData();
    }

    private void init(){
        listOfProducts = (RecyclerView) findViewById(R.id.list_of_products);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listOfProducts.setLayoutManager(linearLayoutManager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(Constants.TAG));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void populateData(){
        List<Product> products = getProducts();
        MyRecycleAdapter adapter = new MyRecycleAdapter(products, this);
        adapter.setListener(new MyRecycleAdapter.MyListener() {
            @Override
            public void onItemClickListener(int position, View root) {
                //TODO tambahkan aksi ketika suatu product diklik
                //DUMMY
                TextView productName = (TextView) root.findViewById(R.id.product_name);
                Toast.makeText(CategoryProductsActivity.this, "POSITION: "+position+"\nPRODUCT NAME: "+productName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        listOfProducts.setAdapter(adapter);
    }

    private List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        //TODO tambahkan produk2 yang ingin ditampilkan ke products
        //DUMMY:
        products.add(new Product("Product A",null,null,null,null));
        products.add(new Product("Product B",null,null,null,null));
        products.add(new Product("Product C",null,null,null,null));
        products.add(new Product("Product D",null,null,null,null));
        products.add(new Product("Product E",null,null,null,null));
        products.add(new Product("Product F",null,null,null,null));
        products.add(new Product("Product G",null,null,null,null));
        products.add(new Product("Product H",null,null,null,null));
        products.add(new Product("Product I",null,null,null,null));
        products.add(new Product("Product J",null,null,null,null));
        products.add(new Product("Product K",null,null,null,null));
        products.add(new Product("Product L",null,null,null,null));
        products.add(new Product("Product M",null,null,null,null));
        return products;
    }

    private static class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>{

        List<Product> products;
        Activity activity;
        MyListener mListener;

        public MyRecycleAdapter(List<Product> products, Activity activity){
            this.products = products;
            this.activity = activity;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View theView = activity.getLayoutInflater().inflate(R.layout.item_product, parent, false);
            return new MyViewHolder(theView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Product product = products.get(position);
            holder.productName.setText(product.getName());
            holder.points.setText(""+product.getPoint());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onItemClickListener(position, holder.cardView);
                    }else{
                        Log.e("FARISI","BLOM DI SET LISTENERNYA");
                    }
                }
            });
            //TODO set product photo
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public void setListener(MyListener listener){
            mListener = listener;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            ImageView productPhoto;
            TextView productName;
            TextView points;

            public MyViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView;
                productPhoto = (ImageView) itemView.findViewById(R.id.product_photo);
                productName = (TextView) itemView.findViewById(R.id.product_name);
                points = (TextView) itemView.findViewById(R.id.points);
            }
        }

        interface MyListener{
            void onItemClickListener(int position, View root);
        }

    }

}
