package com.fragilebytes.asos.adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fragilebytes.asos.R;
import com.fragilebytes.asos.models.product.ProductModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Windows on 11/04/2016.
 */

public class SwipeAdapter extends PagerAdapter {
    ProductModel productModel;
    Context context;
    LayoutInflater layoutInflater;

    public SwipeAdapter(ProductModel productModel, Context context){
        this.context = context;
        this.productModel = productModel;

    }
    @Override
    public int getCount() {
        return productModel.getProductImageUrls().size();
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_pager_row, viewGroup, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.itemImage);


        Picasso.with(context)
                .load(productModel.getProductImageUrls().get(position))
                .resize(400, 0)
                .into(imageView);

        viewGroup.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object object){
        viewGroup.removeView((RelativeLayout) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout)object);
    }


}
