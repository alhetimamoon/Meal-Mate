package mamoonbraiga.MealMate.adapters;

/**
 * Created by MamoonBraiga on 2016-02-04.
 */
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
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderProfileRecipes> {

    List<Recipe> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;


    public RecyclerViewAdapter(List<Recipe> contents) {
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

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ViewHolderProfileRecipes onCreateViewHolder(ViewGroup parent, int viewType) {
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card_layout, parent, false);
        ViewHolderProfileRecipes viewHolder = new ViewHolderProfileRecipes(view);
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolderProfileRecipes holder, int position) {
        Recipe recipe = contents.get(position);
        String imageUrl = recipe.getImageUrl();

        if (imageUrl != null){

            imageLoader.get("http://s3.amazonaws.com/meal-mate-1/recipes/images/000/000/011/original/muffins.jpg?1451948726", new ImageLoader.ImageListener() {

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.image.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        }

        holder.setTitle(recipe.getTitle());
        holder.setDescription(recipe.getDescription());
    }
    public static class ViewHolderProfileRecipes extends RecyclerView.ViewHolder{
        /**
         * This class is used to hold the references to UI compnents for each recipe
         * use this when connecting to the database using JSON
         */
        private ImageView image;
        private TextView title;
        private TextView description;
        private String mItem;

        public ViewHolderProfileRecipes(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.recipeTitle);
            description = (TextView) v.findViewById(R.id.recipeDescription);
            image = (ImageView) v.findViewById(R.id.recipeThumbnail);

        }
        public void setTitle(String titleString){
            this.title.setText(titleString);
        }
        public void setDescription(String descriptionString){
            this.description.setText(descriptionString);
        }

    }
}
