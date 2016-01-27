package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class FragmentDescription extends Fragment {

    private TextView description;
    private ArrayList<String> stepsList = new ArrayList<>();
    private ListView steps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //view setup
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        description = (TextView) view.findViewById(R.id.description);
        steps = (ListView) view.findViewById(R.id.steps);

        //get the saved data from main activity
        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle bundle = mainActivity.getSavedData();
        Recipe recipe = bundle.getParcelable("recipe");

        //set description
        description.setText(recipe.getDescription());

        String step;
        for (int i=0; i<recipe.getInstructions().length(); i++){
            try {
                step = recipe.getInstructions().getJSONObject(i).getString("step");
                stepsList.add(i+1 + ". " + step);
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
                R.layout.step,          //layout
                stepsList);             //the list of instructions

        steps.setAdapter(adapter);
        stepsList = new ArrayList<>();

    }
}
