package mamoonbraiga.poodle_v1;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-09-30.
 * this class is responsible for the navigation drawer items, whether its in an item or a header
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder>{

    private static final int HEADER_TYPE = 0;
    private static final int ROW_TYPE = 1;
    private List<String> rows;
    private int rowsIcons[];

    public DrawerAdapter(ArrayList<String> rows, int icons[]){

        this.rowsIcons = icons;
        this.rows = rows;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // if its a header type inflate the header, and if its the row type inflate the row type
        if (viewType == HEADER_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header, parent, false);
            return new ViewHolder(view, viewType);

        }
        else if (viewType == ROW_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row, parent, false);
            return new ViewHolder(view, viewType);
        }
        return null;
    }
    //row & header content
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (holder.viewType == ROW_TYPE) {
            String rowText = rows.get(position -1);
            holder.textView.setText(rowText);
            holder.imageView.setImageResource(rowsIcons[position - 1]);

        }

    }

    @Override
    public int getItemCount() {
        return rows.size() + 1;
    }


    @Override
    public int getItemViewType(int position){

        if (position == 0){
            return HEADER_TYPE;
        }
        return ROW_TYPE;

    }
    //view holder class for the drawer
    //getting access to the text and icons
    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected int viewType;

        @InjectView(R.id.drawer_row_icon) ImageView imageView;
        @InjectView(R.id.drawer_row_text) TextView textView;

        public ViewHolder (View itemView, int viewType){
            super(itemView);

            this.viewType = viewType;

            if (viewType == ROW_TYPE)
                ButterKnife.inject(this, itemView);
        }

    }
}

