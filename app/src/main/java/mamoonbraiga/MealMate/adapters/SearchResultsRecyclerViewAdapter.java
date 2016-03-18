package mamoonbraiga.MealMate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2016-03-14.
 */
public class SearchResultsRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultsRecyclerViewAdapter.ViewHolderSearchResults> {

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    List<Recipe> contents;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;


    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public SearchResultsRecyclerViewAdapter(List<Recipe> contents) {
        this.contents = contents;

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    public void setOnItemClickListener( OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public ViewHolderSearchResults onCreateViewHolder(ViewGroup parent, int viewType) {

        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_card, parent, false);
        ViewHolderSearchResults viewHolderSearchResults = new ViewHolderSearchResults(view);
        return viewHolderSearchResults;
    }

    @Override
    public void onBindViewHolder(final ViewHolderSearchResults holder, int position) {
        Recipe recipe = contents.get(position);
        String imageUrl = recipe.getImageUrl();

        if (imageUrl != null){

            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.image.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        else{
            holder.image.setImageDrawable(null);
        }
        holder.setTitle(recipe.getTitle());

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class ViewHolderSearchResults extends RecyclerView.ViewHolder{
        /**
         * This class is used to hold the references to UI compnents for each recipe
         * use this when connecting to the database using JSON
         */
        private ImageView image;
        private TextView title;

        public ViewHolderSearchResults(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.search_result_card_title);
            image = (ImageView) v.findViewById(R.id.search_result_card_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });

        }
        public void setTitle(String titleString){
            this.title.setText(titleString);
        }

    }
}
