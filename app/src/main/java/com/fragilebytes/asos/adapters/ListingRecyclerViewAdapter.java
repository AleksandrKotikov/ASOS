package com.fragilebytes.asos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragilebytes.asos.R;
import com.fragilebytes.asos.models.category.CatalogModel;
import com.fragilebytes.asos.services.OnDataSend;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ListingRecyclerViewAdapter extends RecyclerView.Adapter<ListingRecyclerViewAdapter.ViewHolder> {

    private CatalogModel catalogModel;
    private int rowLayout;
    private Context mContext;

    public ListingRecyclerViewAdapter(CatalogModel catalogModel, int rowLayout, Context context) {
        this.catalogModel = catalogModel;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.listingItemPrice.setText(catalogModel.getListings().get(i).getCurrentPrice());

            Picasso.with(mContext)
                    .load(catalogModel.getListings().get(i).getProductImageUrl().get(0))
                    .resize(400, 0)
                    .into(viewHolder.listingItemImage);
    }

    @Override
    public int getItemCount() {
        return  catalogModel == null ? 0 : catalogModel.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
//============================================================================================================================BIND VIEW WITH IT ID
        @Bind(R.id.listItemPrice)
TextView listingItemPrice;
        @Bind(R.id.listItemImage)
ImageView listingItemImage;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setTag(itemView);
        }
    }
//==================================================================================================NOT SURE ABOUT IT!
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        ButterKnife.unbind(this);
        super.onDetachedFromRecyclerView(recyclerView);
    }
}