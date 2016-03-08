package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-12-27.
 */
public class FragmentNutrition extends Fragment {

    private TextView calories;
    private TextView protein;
    private TextView carb;
    private TextView fat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);
        calories = (TextView) view.findViewById(R.id.calories);
        protein = (TextView) view.findViewById(R.id.protein);
        carb = (TextView) view.findViewById(R.id.carb);
        fat = (TextView) view.findViewById(R.id.fat);

        Bundle bundle = getArguments();
        int id = bundle.getInt("id");

        Recipe recipe = bundle.getParcelable(String.valueOf(id));

        calories.setText("  CALORIES:: " + Integer.toString(recipe.getCalories()));
        protein.setText("  PROTEIN: " + Integer.toString(recipe.getProtein()) + "g");
        carb.setText("  CARB: " + Integer.toString(recipe.getCarbs())+ "g");
        fat.setText("  FAT: " + Integer.toString(recipe.getFat()) + "g");


        return view;
    }
}
