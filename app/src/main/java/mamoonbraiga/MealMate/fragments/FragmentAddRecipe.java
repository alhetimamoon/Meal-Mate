package mamoonbraiga.MealMate.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.design.widget.AppBarLayout;
import android.widget.TextView;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class FragmentAddRecipe extends Fragment{

    static final int RESULT_OK = -1;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ImageButton addIngredientField;
    private ImageButton addInstructionButton;
    FloatingActionButton headerPickerButton;
    private LinearLayout ingredientsLayout;
    private LinearLayout instructionsLayout;
    private ImageView header;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        animateToolbarText(view);
        hideToolbar();
        addIngredientField = (ImageButton) view.findViewById(R.id.add_ingredient_button);
        addInstructionButton = (ImageButton) view.findViewById(R.id.add_instruction_button);
        ingredientsLayout = (LinearLayout) view.findViewById(R.id.ingredient_layout);
        instructionsLayout = (LinearLayout) view.findViewById(R.id.instructions_layout);
        header = (ImageView) view.findViewById(R.id.new_recipe_header);
        headerPickerButton = (FloatingActionButton) view.findViewById(R.id.new_recipe_image_button);



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

        headerPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Recipe Image"), 1);

            }
        });
        return view;
    }

    public void onActivityResult(int regCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if (regCode == 1)
                header.setImageURI(data.getData());
        }
    }

    private void hideToolbar() {
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }

    private void animateToolbarText(View view) {
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);

        final TextView toolbarName = (TextView)view.findViewById(R.id.toolbarName);
        toolbarName.setVisibility(View.INVISIBLE);
        collapsingToolbar = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar);
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

    @Override
    public void onPause(){
        super.onPause();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);
    }
}
