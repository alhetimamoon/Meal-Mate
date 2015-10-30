package mamoonbraiga.poodle_v1.fragments;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;
import mamoonbraiga.poodle_v1.adapters.AdapterRecipeBook;
import mamoonbraiga.poodle_v1.extras.Recipe;
import mamoonbraiga.poodle_v3.R;


/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class FragmentRecipeBook extends Fragment{
    FloatingActionMenu actionMenu;
    private com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton;
    private SubActionButton searchButton;
    private SubActionButton addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);
        RecyclerView reList = (RecyclerView) view.findViewById(R.id.listRecipes);
        reList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        reList.setLayoutManager(llm);

        AdapterRecipeBook adapterRecipeBook = new AdapterRecipeBook(createList(7));
        reList.setAdapter(adapterRecipeBook);

        /** Floating Action Button **/
        ImageView options = new ImageView(getActivity());
        options.setImageResource(R.drawable.ic_food_variant_grey600_48dp);
        actionButton = new com.oguzdev.circularfloatingactionmenu.
                library.FloatingActionButton.Builder(getActivity()).setContentView(options).build();

        ImageView searchIcon = new ImageView(getActivity());
        searchIcon.setImageResource(R.drawable.ic_magnify_grey600_48dp);

        ImageView addIcon = new ImageView(getActivity());
        addIcon.setImageResource(R.drawable.add_recipe);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());

        searchButton = itemBuilder.setContentView(searchIcon).build();
        addButton = itemBuilder.setContentView(addIcon).build();

        actionMenu = new FloatingActionMenu.Builder(getActivity()).addSubActionView(searchButton).addSubActionView(addButton).attachTo(options).build();

        /** Floating Action Button **/


        return view;
    }
    @Override
    public void onPause(){
        super.onPause();
        actionButton.detach();
        actionMenu.close(true);

    }
    private List<Recipe> createList(int size) {
        List<Recipe> recipes = new ArrayList<>();
        for (int i=0; i<=size; i++) {

            Recipe recipe = new Recipe();
            recipe.setTitle("Farfalle with Butternut Squash");
            recipe.setDescription("Heat a large skillet over medium-high heat. Add ¼ cup water and 1½ pounds trimmed, halved Brussels sprouts to pan; " +
                    "cover and cook 5 minutes. Add 2 tablespoons unsalted butter, ¼ teaspoon kosher salt, and ¼ teaspoon freshly ground black pepper to pan; " +
                    "cook, uncovered, 2 minutes. Increase heat to high; cook 1 minute, stirring frequently. Stir in 1 teaspoon grated lemon rind and 1 tablespoon fresh lemon ");
            recipes.add(recipe);
        }
        return recipes;
    }

}
