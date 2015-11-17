package mamoonbraiga.MealMate.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.fragments.FragmentRecipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
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
    public ViewHolderRecipeBook onCreateViewHolder(final ViewGroup parent, int viewType) {
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card_layout, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                Class fragmentClass = FragmentRecipe.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                FragmentTransaction ft = ((Activity) parent.getContext()).getFragmentManager().beginTransaction();
                ft.replace(R.id.flContent, fragment).addToBackStack("recipe card").commit();

                Toast.makeText(v.getContext(), "Click Listener card=" + v.getId(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
        holder.setTitle(recipe.getTitle());
        holder.setDescription(recipe.getDescription());

        //ViewHolderRecipeBook.title.setText(recipe.getTitle());
        //ViewHolderRecipeBook.description.setText(recipe.getDescription());

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
        private ImageView image;
        private TextView title;
        private TextView description;

        public ViewHolderRecipeBook(View v) {
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
