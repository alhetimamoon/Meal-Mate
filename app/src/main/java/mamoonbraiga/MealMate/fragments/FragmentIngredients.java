package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-12-27.
 */
public class FragmentIngredients extends Fragment {

    private TextView ingredients_title;
    ListView ingredients;
    private ArrayList<String> ingredientList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ingredients_title = (TextView) view.findViewById(R.id.ingredients_title);

        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");

        Recipe recipe = bundle.getParcelable(String.valueOf(id));


        String ingredient;
        for (int i=0; i<recipe.getIngredients().length(); i++){
            try {
                ingredient = recipe.getIngredients().getJSONObject(i).getString("name");
                ingredientList.add("  " + ingredient);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        populateListView(view);

        return view;
    }

    private void populateListView(View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),           //context
                R.layout.ingredient,          //layout
                ingredientList);             //the list of instructions

        ingredients = (ListView) view.findViewById(R.id.ingredients_list_view);
        Log.d(adapter.toString(), "test");
        ingredients.setAdapter(adapter);
        ingredientList = new ArrayList<>();
    }
}
