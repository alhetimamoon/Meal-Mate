package mamoonbraiga.MealMate.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-11-16.
 */
public class FragmentRecipe extends Fragment {
    private ArrayList<Recipe> recipes;
    private ImageView recipeHeader;
    private TextView description;
    private TextView title;
    private ImageLoader imageLoader;
    private VolleySingleton volleySingleton;
    private FloatingActionButton likeButton;
    private Bundle bundle;
    private ActionBar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.recipe_layout, container, false);

        //setting the floating button tint color
        likeButton = (FloatingActionButton) view.findViewById(R.id.like_button);
        likeButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ColorPrimary)));

        recipeHeader = (ImageView) view.findViewById(R.id.recipe_header);
        title = (TextView) view.findViewById(R.id.recipe_title);
        description = (TextView) view.findViewById(R.id.recipe_description);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
        MainActivity mainActivity = (MainActivity) getActivity();
        bundle = mainActivity.getSavedData();
        Recipe recipe = bundle.getParcelable("recipe");

        ((MainActivity) getActivity()).setActionBarTitle(recipe.getTitle());
        imageLoader.get(recipe.getImageUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Drawable d = new BitmapDrawable(getResources(), response.getBitmap());
                recipeHeader.setBackground(d);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        title.setText(recipe.getTitle());
        description.setText(recipe.getDescription());
        return view;
    }


}
