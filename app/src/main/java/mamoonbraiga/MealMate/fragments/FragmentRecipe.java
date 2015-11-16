package mamoonbraiga.MealMate.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-11-16.
 */
public class FragmentRecipe extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_layout, container, false);
        Toast.makeText(view.getContext(), "Click Listener card=" + view.findViewById(R.id.recipeTitle),
                Toast.LENGTH_SHORT).show();
        return view;
    }
}
