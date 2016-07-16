package id.co.meda.survey;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;

import id.co.meda.survey.model.Product;


/**
 * A simple {@link Fragment} subclass.
 */
public class CashBacksFragment extends Fragment implements View.OnClickListener{

    RecyclerView popularProducts;
    TextView seeMore;
    SliderLayout sliderShow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View theView = inflater.inflate(R.layout.fragment_cash_backs, container, false);
        popularProducts = (RecyclerView) theView.findViewById(R.id.popular_products);
        seeMore = (TextView) theView.findViewById(R.id.see_more);
        sliderShow = (SliderLayout) theView.findViewById(R.id.slider);
        populateData();
        return theView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void populateData(){
        seeMore.setOnClickListener(this);
        final List<Product> products = getPopularProducts();
        popularProducts.setAdapter(new MyRecycleAdapter(products));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popularProducts.setLayoutManager(linearLayoutManager);

        DefaultSliderView defaultSliderView1 = new DefaultSliderView(getContext());
        defaultSliderView1.image(R.drawable.baby_care);
        DefaultSliderView defaultSliderView2 = new DefaultSliderView(getContext());
        defaultSliderView2.image(R.drawable.cash_out_history);
        DefaultSliderView defaultSliderView3 = new DefaultSliderView(getContext());
        defaultSliderView3.image(R.drawable.beauty);
        sliderShow.addSlider(defaultSliderView1);
        sliderShow.addSlider(defaultSliderView2);
        sliderShow.addSlider(defaultSliderView3);
    }

    private List<Product> getPopularProducts(){
        List<Product> popularProducts = new ArrayList<>();
        //TODO: belum ada data apapun di popular product
        //DUMMY
        popularProducts.add(new Product("Product A",null,"10ml", null, null));
        popularProducts.add(new Product("Product B",null,"7pcs", null, null));
        popularProducts.add(new Product("Product C",null,"20ml", null, null));
        popularProducts.add(new Product("Product D",null,"30ml", null, null));
        popularProducts.add(new Product("Product E",null,"40ml", null, null));
        return popularProducts;
    }

    private class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>{

        private List<Product> products;

        public MyRecycleAdapter(List<Product> products){
            this.products = products;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View theView = getActivity().getLayoutInflater().inflate(R.layout.item_popular_product, parent, false);
            return new MyViewHolder(theView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Product cProduct= products.get(position);
            holder.productName.setText(cProduct.getName());
            holder.productDetail.setText(cProduct.getDescription());
            holder.points.setText(cProduct.getPoint()+"");
            //TODO: belum melakukan set photo product
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView productPhoto;
            TextView productName;
            TextView productDetail;
            TextView points;

            public MyViewHolder(View itemView) {
                super(itemView);
                productPhoto = (ImageView) itemView.findViewById(R.id.product_photo);
                productName = (TextView) itemView.findViewById(R.id.product_name);
                productDetail = (TextView) itemView.findViewById(R.id.product_detail);
                points = (TextView) itemView.findViewById(R.id.points);
            }

        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.see_more){
            //TODO: menambahkan aksi ketika see more di klik user
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        sliderShow.stopAutoCycle();
    }
}
