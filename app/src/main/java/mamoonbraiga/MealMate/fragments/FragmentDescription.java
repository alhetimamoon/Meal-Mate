package mamoonbraiga.MealMate.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-12-27.
 */
public class FragmentDescription extends Fragment {

    private TextView description;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        description = (TextView) view.findViewById(R.id.description);

        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle bundle = mainActivity.getSavedData();

        Recipe recipe = bundle.getParcelable("recipe");
        description.setText(recipe.getDescription());
        return view;
    }
}
