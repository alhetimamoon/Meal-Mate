package mamoonbraiga.MealMate.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class FragmentAddRecipe extends Fragment{

    static final int RESULT_OK = -1;
    private CollapsingToolbarLayout collapsingToolbar;
    private RequestQueue requestQueue;
    private Toolbar toolbar;
    private ImageButton addIngredientField;
    private ImageButton addInstructionButton;
    private ImageButton backButton;
    private FloatingActionButton headerPickerButton;
    private LinearLayout ingredientsLayout;
    private LinearLayout instructionsLayout;
    private ImageView header;
    private List<EditText> instructions = new ArrayList<>();
    private List<EditText> ingredients = new ArrayList<>();
    private Button upload_recipe;
    private EditText recipeName, recipeDescription, servingSize, calories, protein, carbs, fat;
    private TextView uploadImageText;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        animateToolbarText(view);
        hideToolbar();
        initializeFields(view);
        setUpClickListeners();
        return view;
    }

    private void setUpClickListeners() {

        addIngredientField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) getActivity().getLayoutInflater().inflate(R.layout.edit_text_ingredient, null);
                ingredientsLayout.addView(editText);
                ingredients.add(editText);

            }
        });
        addInstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) getActivity().getLayoutInflater().inflate(R.layout.edit_text_instruction, null);
                instructionsLayout.addView(editText);
                instructions.add(editText);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        upload_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareRecipeFile();

            }
        });

    }

    private void initializeFields(View view) {

        uploadImageText = (TextView) view.findViewById(R.id.uploadText);
        addIngredientField = (ImageButton) view.findViewById(R.id.add_ingredient_button);
        addInstructionButton = (ImageButton) view.findViewById(R.id.add_instruction_button);
        ingredientsLayout = (LinearLayout) view.findViewById(R.id.ingredient_layout);
        instructionsLayout = (LinearLayout) view.findViewById(R.id.instructions_layout);
        header = (ImageView) view.findViewById(R.id.new_recipe_header);
        headerPickerButton = (FloatingActionButton) view.findViewById(R.id.new_recipe_image_button);
        backButton = (ImageButton) view.findViewById(R.id.toolbarButton);
        upload_recipe = (Button) view.findViewById(R.id.upload_recipe_button);
        recipeName = (EditText) view.findViewById(R.id.recipe_name);
        recipeDescription = (EditText) view.findViewById(R.id.recipe_description);
        calories = (EditText) view.findViewById(R.id.calories_amount);
        servingSize = (EditText) view.findViewById(R.id.serving_size);
        protein = (EditText) view.findViewById(R.id.protein_amount);
        carbs = (EditText) view.findViewById(R.id.carbs_amount);
        fat = (EditText) view.findViewById(R.id.fat_amount);
    }

    private void prepareRecipeFile() {
        List<String> instructionsText = new ArrayList<>();
        List<String> ingredientsText = new ArrayList<>();

        boolean valid = true;

        for (TextView textView: instructions){
            if (textView != null && !textView.getText().toString().trim().equals(""))
                instructionsText.add(textView.getText().toString());
        }

        for (TextView textView: ingredients){
            if (textView != null && !textView.getText().toString().trim().equals(""))
                ingredientsText.add(textView.getText().toString());
        }

        if (recipeName == null || recipeName.getText().toString().trim().equals("")) {
            recipeName.setError("Recipe Name Is Required");
            valid = false;
        }

        if (recipeDescription == null || recipeDescription.getText().toString().trim().equals("")){
            recipeDescription.setError("Recipe Description Is Required");
            valid = false;
        }
        if (servingSize == null || servingSize.getText().toString().trim().equals("")){
            servingSize.setError("Serving Size is Required");
            valid = false;
        }

        if (calories == null || calories.getText().toString().trim().equals("")){
            calories.setError("Calories Amount is Required");
            valid = false;
        }

        if (protein == null || protein.getText().toString().trim().equals("")){
            protein.setError("Protein Amount is Required");
            valid = false;
        }

        if (carbs == null || carbs.getText().toString().trim().equals("")){
            carbs.setError("Carbs Amount is Required");
            valid = false;
        }

        if (fat == null || fat.getText().toString().trim().equals("")){
            fat.setError("Fat Amount is Required");
            valid = false;
        }

        if (!valid)
            showErrorDialog();

        if (valid){
            createJSONObject(instructionsText, ingredientsText);
        }
    }



    private void createJSONObject(List<String> instructionsText, List<String> ingredientsText) {

        JSONObject jsonObject = new JSONObject();
        HashMap<String, Object> mainHashMap = new HashMap<>();
        JSONObject IngredientsJSON = new JSONObject();
        JSONObject instructionsJSON = new JSONObject();


        for (String text: ingredientsText){
            try {
                IngredientsJSON.put(String.valueOf(ingredientsText.indexOf(text)), text);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (String text: instructionsText){
            try {
                instructionsJSON.put(String.valueOf(instructionsText.indexOf(text)), text);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mainHashMap.put("ingredients", IngredientsJSON);
        mainHashMap.put("instructions", instructionsJSON);
        mainHashMap.put("name", recipeName.getText().toString());
        mainHashMap.put("description", recipeDescription.getText().toString());
        mainHashMap.put("serving_size", servingSize.getText().toString());
        mainHashMap.put("calories", calories.getText().toString());
        mainHashMap.put("protein", protein.getText().toString());
        mainHashMap.put("carbs", carbs.getText().toString());
        mainHashMap.put("fat", fat.getText().toString());
        jsonObject = new JSONObject(mainHashMap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap imageBitmap = ((BitmapDrawable) header.getDrawable()).getBitmap();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        Log.d("this is the image", encodedImage);
        try {
            jsonObject.put("image", encodedImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("this is our JSON Object", jsonObject.toString());

    }

    private void sendJsonRequest(){
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();

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

    public void onActivityResult(int regCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if (regCode == 1) {
                header.setImageURI(data.getData());
                uploadImageText.setVisibility(View.GONE);

            }
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
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);
    }

}
