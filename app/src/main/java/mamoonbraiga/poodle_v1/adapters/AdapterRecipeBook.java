package mamoonbraiga.poodle_v1.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mamoonbraiga.poodle_v1.extras.Recipe;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-28.
 * This class is used by FragmentRecipeBook class, to show and update cards
 */
public class AdapterRecipeBook extends RecyclerView.Adapter<AdapterRecipeBook.ViewHolderRecipeBook> {

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public AdapterRecipeBook(Context context){
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolderRecipeBook onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recipe_layout, parent, false);
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


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyItemChanged(0, recipes);
    }

    static class ViewHolderRecipeBook extends RecyclerView.ViewHolder{
        /**
         * This class is responsible for filling the content of the cards
         * use this when connecting to the database using JSON
         */
        private ImageView recipeThumnail;
        private TextView recipeTitle;
        private TextView recipeDecription;

        public ViewHolderRecipeBook(View itemView) {
            super(itemView);
            recipeThumnail = (ImageView) itemView.findViewById(R.id.recipeThumbnail);
            recipeTitle = (TextView) itemView.findViewById(R.id.recipeTitle);
            recipeDecription = (TextView) itemView.findViewById(R.id.recipeDescription);
        }
    }
}
