package mamoonbraiga.poodle_v1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.poodle_v1.extras.Recipe;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-28.
 * This class stands between the recipes data model we want to show in the UI and the UI compenet that renders this information
 * This class is used by FragmentRecipeBook class, to show/update cards
 */
public class AdapterRecipeBook extends RecyclerView.Adapter<AdapterRecipeBook.ViewHolderRecipeBook> {

    private List<Recipe> recipes = new ArrayList<>();
    private LayoutInflater layoutInflater;
    public AdapterRecipeBook(List<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public ViewHolderRecipeBook onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.fragment_recipe_book, parent, false);
        ViewHolderRecipeBook viewHolder = new ViewHolderRecipeBook(view);

        return viewHolder;
    }

    /**
     *
     * @param holder
     * @param position the position of the recipe within our data structure
     */
    @Override
    public void onBindViewHolder(AdapterRecipeBook.ViewHolderRecipeBook holder, int position) {
        Recipe recipe = new Recipe();
        ViewHolderRecipeBook.recipeTitle.setText(recipe.getTitle());
        ViewHolderRecipeBook.recipeDescription.setText(recipe.getDescription());
        ViewHolderRecipeBook.recipeThumbnail.setImageResource(R.drawable.pasta);

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
        protected static ImageView recipeThumbnail;
        protected static TextView recipeTitle;
        protected static TextView recipeDescription;

        public ViewHolderRecipeBook(View itemView) {
            super(itemView);
            recipeThumbnail = (ImageView) itemView.findViewById(R.id.recipeThumbnail);
            recipeTitle = (TextView) itemView.findViewById(R.id.recipeTitle);
            recipeDescription = (TextView) itemView.findViewById(R.id.recipeDescription);
        }
    }
}
