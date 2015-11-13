package mamoonbraiga.poodle_v1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import mamoonbraiga.poodle_v1.extras.Recipe;
import mamoonbraiga.poodle_v1.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-28.
 * This class stands between the recipes data model we want to show in the UI and the UI compenet that renders this information
 * This class is used by FragmentRecipeBook class, to show/update cards
 */
public class AdapterRecipeBook extends RecyclerView.Adapter<AdapterRecipeBook.ViewHolderRecipeBook> {

    private List<Recipe> recipes;
    VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    public AdapterRecipeBook(List<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public ViewHolderRecipeBook onCreateViewHolder(ViewGroup parent, int viewType) {
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);
        ViewHolderRecipeBook viewHolder = new ViewHolderRecipeBook(view);

        return viewHolder;
    }

    /**
     *
     * @param holder
     * @param position the position of the recipe within our data structure
     */
    @Override
    public void onBindViewHolder(final AdapterRecipeBook.ViewHolderRecipeBook holder, int position) {
        Recipe recipe = recipes.get(position);
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
        ViewHolderRecipeBook.title.setText(recipe.getTitle());
        ViewHolderRecipeBook.description.setText(recipe.getDescription());

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolderRecipeBook extends RecyclerView.ViewHolder{
        /**
         * This class is used to hold the references to UI compnents for each recipe
         * use this when connecting to the database using JSON
         */
        protected static ImageView image;
        protected static TextView title;
        protected static TextView description;

        public ViewHolderRecipeBook(View v) {
            super(v);

            title = (TextView) v.findViewById(R.id.recipeTitle);
            description = (TextView) v.findViewById(R.id.recipeDescription);
            image = (ImageView) v.findViewById(R.id.recipeThumbnail);


        }
    }
}
