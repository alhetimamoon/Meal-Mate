package mamoonbraiga.MealMate.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-25.
 */
public class FragmentCalculator extends Fragment{

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private Spinner gender_spinner;
    private Spinner activity_level;
    private Spinner goal;
    private Button calculate_button;
    private EditText height_feet, height_inches, age, weight;
    private double BMR;
    private double weight_value;
    private double TDEE=0.0;
    private String goalString;
    private String activityString;
    private LinearLayout mainContent;
    private LinearLayout stats_content;
    private TextView calories, carbs, protein, fat;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        mainContent = (LinearLayout) view.findViewById(R.id.main_content_calc);
        stats_content = (LinearLayout) view.findViewById(R.id.stats_content);
        stats_content.setVisibility(LinearLayout.GONE);

        //gender spinner
        gender_spinner = (Spinner) view.findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(gender_adapter);

        //activity level spinner
        activity_level = (Spinner) view.findViewById(R.id.activity_level);
        ArrayAdapter<CharSequence> activity_adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.activity_level_array, android.R.layout.simple_spinner_item);
        activity_level.setAdapter(activity_adapter);

        //goal spinner
        goal = (Spinner) view.findViewById(R.id.goal);
        ArrayAdapter<CharSequence> goals_adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.goals_array, android.R.layout.simple_spinner_item);
        goal.setAdapter(goals_adapter);

        height_feet = (EditText) view.findViewById(R.id.height_in_feet);
        height_inches = (EditText) view.findViewById(R.id.height_in_inches);
        age = (EditText) view.findViewById(R.id.age);
        weight = (EditText) view.findViewById(R.id.weight);

        calories = (TextView) view.findViewById(R.id.calories_daily);
        protein = (TextView) view.findViewById(R.id.protein_daily);
        fat = (TextView) view.findViewById(R.id.fat_daily);
        carbs = (TextView) view.findViewById(R.id.carbs_daily);


        calculate_button = (Button) view.findViewById(R.id.calc_button);
        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (calculateBMR()){
                    setTextFields();
                    mainContent.removeViewAt(0);
                    stats_content.setVisibility(LinearLayout.VISIBLE);
                }
                else
                    showErrorDialog();

            }
        });
        animateToolbarText(view);
        hideToolbar();

        return view;
    }

    private void showErrorDialog() {

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Some required fields are empty")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void setTextFields() {
        calories.setText("Calories: " + new Integer((int) TDEE));
        fat.setText("Fat: " + new Integer((int) (TDEE * 0.20 / 8)) + "g");
        carbs.setText("Carbs: " + new Integer((int) (TDEE * 0.55 / 4)) + "g");
        protein.setText("Protein: " + new Integer((int) (TDEE * 0.25 / 4)) + "g");
    }

    private boolean calculateBMR() {

        boolean valid = true;
        if (height_feet == null || height_feet.getText().toString().trim().equals("")){
            height_feet.setError("Required");
            valid = false;
        }

        if (height_inches == null || height_inches.getText().toString().trim().equals("")){
            height_inches.setError("Required");
            valid = false;
        }

        if (age == null || age.getText().toString().trim().equals("")){
            age.setError("Required");
            valid = false;
        }

        if (weight == null || weight.getText().toString().trim().equals("")){
            weight.setError("Required");
            valid = false;
        }

        if (valid) {

            weight_value = Double.parseDouble(weight.getText().toString());
            double height_feet_value = Double.parseDouble(height_feet.getText().toString());
            double height_inches_value = Double.parseDouble(height_inches.getText().toString());
            double age_value = Double.parseDouble(age.getText().toString());
            double final_height = convertToEnglish(height_feet_value, height_inches_value);
            String gender = gender_spinner.getSelectedItem().toString();
            goalString = goal.getSelectedItem().toString();
            activityString = activity_level.getSelectedItem().toString();

            switch (gender) {
                case "Female":
                    BMR = 655 + (9.6 * weight_value) + (1.8 * final_height) - (4.7 * age_value);
                    break;
                case "Male":
                    BMR = 66 + (13.7 * weight_value) + (5 * final_height) - (6.8 * age_value);
                    break;

            }

            switch (activityString) {
                case "1 sedetary --- little to no excercise":
                    TDEE = 1.2 * BMR;
                    break;
                case "2 lightly active --- workout 1-3 times per week":
                    TDEE = 1.375 * BMR;
                    break;
                case "3 moderately active --- excercise 3-5 times per week":
                    TDEE = 1.55 * BMR;
                    break;
                case "4 very active --- workout 6-7 days a week":
                    TDEE = 1.725 * BMR;
                    break;
                case "5 extremely active --- intense workouts 6-7 days a week, physically demanding job":
                    TDEE = 1.9 * BMR;
                    break;

            }

            switch (goalString) {
                case "Lose Weight":
                    TDEE = TDEE * 0.8;
                    break;
                case "Gain Weight":
                    TDEE = TDEE * 1.15;
                    break;

            }

            return valid;
        }
        else
            return valid;
    }

    private double convertToEnglish(double height_feet_value, double height_inches_value) {
        double final_height;
        weight_value = weight_value/2.2046;

        final_height = ( (height_feet_value * 12) + (height_inches_value) ) * 2.54;
        return final_height;
    }

    private void animateToolbarText(View view) {

        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar_cal);

        final TextView toolbarName = (TextView)view.findViewById(R.id.toolbarName);
        toolbarName.setVisibility(View.INVISIBLE);
        collapsingToolbar = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar_calc);
        AppBarLayout.OnOffsetChangedListener mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (collapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                    toolbarName.setVisibility(View.VISIBLE);
                    toolbarName.animate().alpha(1).setDuration(600);
                } else {
                    toolbarName.animate().alpha(0).setDuration(100);
                }
            }
        };
        appBarLayout.addOnOffsetChangedListener(mListener);
    }

    private void hideToolbar() {

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }


}
