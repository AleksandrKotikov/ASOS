package com.fragilebytes.asos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fragilebytes.asos.R;
import com.fragilebytes.asos.models.btn.MenuModel;

public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuAdapter.ViewHolder> {
    MenuModel data;
    private LayoutInflater inflater;
    private Context context;

    public SideMenuAdapter(Context context, MenuModel data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.side_menu_recycler_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(data.getListing().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.getListing().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
