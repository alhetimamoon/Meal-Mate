package mamoonbraiga.MealMate.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class FragmentAddRecipe extends Fragment{

    ImageButton addIngredientField;
    ImageButton addInstructionButton;
    LinearLayout ingredientsLayout;
    LinearLayout instructionsLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        addIngredientField = (ImageButton) view.findViewById(R.id.add_ingredient_button);
        addInstructionButton = (ImageButton) view.findViewById(R.id.add_instruction_button);
        ingredientsLayout = (LinearLayout) view.findViewById(R.id.ingredient_layout);
        instructionsLayout = (LinearLayout) view.findViewById(R.id.instructions_layout);


        addIngredientField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) getActivity().getLayoutInflater().inflate(R.layout.edit_text_ingredient, null);
                ingredientsLayout.addView(editText);


            }
        });
        addInstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) getActivity().getLayoutInflater().inflate(R.layout.edit_text_instruction, null);
                instructionsLayout.addView(editText);
            }
        });
        return view;
    }
    @Override
    public void onPause(){
        super.onPause();

    }
}
