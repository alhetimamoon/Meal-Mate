package mamoonbraiga.MealMate.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-11-16.
 */
public class FragmentRecipe extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.recipe_layout, container, false);
        return view;
    }
}
