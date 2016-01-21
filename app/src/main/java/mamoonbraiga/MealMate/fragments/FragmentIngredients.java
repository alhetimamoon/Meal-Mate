package mamoonbraiga.MealMate.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-12-27.
 */
public class FragmentIngredients extends Fragment {

    private TextView ingredients;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ingredients = (TextView) view.findViewById(R.id.ingredientsText);
        ingredients.setText("this is a test text!");
        return view;
    }
}
