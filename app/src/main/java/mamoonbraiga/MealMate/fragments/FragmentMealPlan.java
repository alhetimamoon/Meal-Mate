package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.MealMate.extras.Meal;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;

import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_CALORIES;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_CARB;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_DESCRIPTION;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_FAT;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_ID;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_INGREDIENTS;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_INSTRUCTIONS;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_PROTEIN;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_TITLE;

/**
 * Created by MamoonBraiga on 2016-03-03.
 */
public class FragmentMealPlan extends Fragment {

    private MainActivity mainActivity;
    private RequestQueue requestQueue;
    private final String url = "http://mealmate.co/api/get_mealplan?id=2";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar loadingProgress;
    private ImageButton backButton;
    JSONArray mealsJSONArray;
    JSONArray recipesArray;
    List<Meal> monday_meals = new ArrayList<>();
    List<Meal> tuesday_meals = new ArrayList<>();
    List<Meal> wednesday_meals = new ArrayList<>();
    List<Meal> thursday_meals = new ArrayList<>();
    List<Meal> friday_meals = new ArrayList<>();
    List<Meal> saturday_meals = new ArrayList<>();
    List<Meal> sunday_meals = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_mealplan, container, false);
        loadingProgress = (ProgressBar) view.findViewById(R.id.loading_mealplan);
        backButton = (ImageButton) view.findViewById(R.id.toolbarButton);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.meal_plan_toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.drawer_view);
        toolbar.showOverflowMenu();



        TextView toolbarName = (TextView) view.findViewById(R.id.toolbarName);
        toolbarName.setText("Awesome Mealplan");

        mainActivity = (MainActivity) getActivity();
        mainActivity.setSupportActionBar(toolbar);
        viewPager = (ViewPager) view.findViewById(R.id.meal_plan_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.meal_plan_tablayout);

        hideToolbar();
        sendJSONRequest();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;

    }

    private void sendJSONRequest() {
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        final JsonObjectRequest  request = new JsonObjectRequest(JsonObjectRequest.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mealsJSONArray = response.getJSONArray("meals");
                    recipesArray = response.getJSONArray("recipes");
                    setupViewPager(viewPager);
                    tabLayout.setupWithViewPager(viewPager);

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error Loading Recipes", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
    });
        requestQueue.add(request);


    }

    private void hideToolbar() {
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.toolbar_container);
        frameLayout.setVisibility(View.GONE);
    }

    private void setupViewPager(ViewPager viewPager) {

        monday_meals.clear();
        tuesday_meals.clear();
        wednesday_meals.clear();
        thursday_meals.clear();
        friday_meals.clear();
        saturday_meals.clear();
        sunday_meals.clear();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        String day_of_week;
        double serving_size;
        for (int i=0; i<mealsJSONArray.length(); i++){
            try {
                JSONObject jsonObject = mealsJSONArray.getJSONObject(i);
                day_of_week = jsonObject.getString("day_of_week");
                serving_size = jsonObject.getDouble("serving_size");
                Recipe recipe;
                Meal meal;
                switch (day_of_week){

                    case "monday":

                        recipe = getRecipe(recipesArray, jsonObject.getInt("recipe_id"));
                        recipe.setServing_size(serving_size);
                        meal= new Meal(jsonObject.getInt("recipe_id"), jsonObject.getString("meal_time"), jsonObject.getString("day_of_week"), recipe, serving_size);
                        monday_meals.add(meal);
                        break;

                    case "tuesday":
                        recipe = getRecipe(recipesArray, jsonObject.getInt("recipe_id"));
                        recipe.setServing_size(serving_size);
                        meal= new Meal(jsonObject.getInt("recipe_id"), jsonObject.getString("meal_time"), jsonObject.getString("day_of_week"), recipe, serving_size);
                        tuesday_meals.add(meal);

                        break;
                    case "wednesday":
                        recipe = getRecipe(recipesArray, jsonObject.getInt("recipe_id"));
                        recipe.setServing_size(serving_size);
                        meal= new Meal(jsonObject.getInt("recipe_id"), jsonObject.getString("meal_time"), jsonObject.getString("day_of_week"), recipe, serving_size);
                        wednesday_meals.add(meal);
                        break;
                    case "thursday":
                        recipe = getRecipe(recipesArray, jsonObject.getInt("recipe_id"));
                        recipe.setServing_size(serving_size);
                        meal= new Meal(jsonObject.getInt("recipe_id"), jsonObject.getString("meal_time"), jsonObject.getString("day_of_week"), recipe, serving_size);
                        thursday_meals.add(meal);
                        break;
                    case "friday":
                        recipe = getRecipe(recipesArray, jsonObject.getInt("recipe_id"));
                        recipe.setServing_size(serving_size);
                        meal= new Meal(jsonObject.getInt("recipe_id"), jsonObject.getString("meal_time"), jsonObject.getString("day_of_week"), recipe, serving_size);
                        friday_meals.add(meal);
                        break;
                    case "saturday":
                        recipe = getRecipe(recipesArray, jsonObject.getInt("recipe_id"));
                        recipe.setServing_size(serving_size);
                        meal= new Meal(jsonObject.getInt("recipe_id"), jsonObject.getString("meal_time"), jsonObject.getString("day_of_week"), recipe, serving_size);
                        saturday_meals.add(meal);
                        break;
                    case "sunday":
                        recipe = getRecipe(recipesArray, jsonObject.getInt("recipe_id"));
                        recipe.setServing_size(serving_size);
                        meal= new Meal(jsonObject.getInt("recipe_id"), jsonObject.getString("meal_time"), jsonObject.getString("day_of_week"), recipe, serving_size);
                        sunday_meals.add(meal);
                        break;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //setting up Monday meals
        Bundle mondayBundle = new Bundle();
        mondayBundle.putParcelableArrayList("monday", (ArrayList<? extends Parcelable>) monday_meals);
        mondayBundle.putString("day", "monday");
        Fragment monday_fragment = new FragmentMealPlanDaily();
        monday_fragment.setArguments(mondayBundle);
        adapter.addFrag(monday_fragment, "Monday");


        //setting up Tuesday meals
        Bundle tuesdayBundle = new Bundle();
        tuesdayBundle.putParcelableArrayList("tuesday", (ArrayList<? extends Parcelable>) tuesday_meals);
        tuesdayBundle.putString("day", "tuesday");
        Fragment tuesday_fragment = new FragmentMealPlanDaily();
        tuesday_fragment.setArguments(tuesdayBundle);
        adapter.addFrag(tuesday_fragment, "Tuesday");

        //setting up Wednesday meals
        Bundle wednesdayBundle = new Bundle();
        wednesdayBundle.putParcelableArrayList("wednesday", (ArrayList<? extends Parcelable>) wednesday_meals);
        wednesdayBundle.putString("day", "wednesday");
        Fragment wednesday_fragment = new FragmentMealPlanDaily();
        wednesday_fragment.setArguments(wednesdayBundle);
        adapter.addFrag(wednesday_fragment, "wednesday");

        //setting up thursday meals
        Bundle thursdayBundle = new Bundle();
        thursdayBundle.putParcelableArrayList("thursday", (ArrayList<? extends Parcelable>) thursday_meals);
        thursdayBundle.putString("day", "thursday");
        Fragment thursday_fragment = new FragmentMealPlanDaily();
        thursday_fragment.setArguments(thursdayBundle);
        adapter.addFrag(thursday_fragment, "Thursday");

        //setting up Friday meals
        Bundle fridayBundle = new Bundle();
        fridayBundle.putParcelableArrayList("friday", (ArrayList<? extends Parcelable>) friday_meals);
        fridayBundle.putString("day", "friday");
        Fragment friday_fragment = new FragmentMealPlanDaily();
        friday_fragment.setArguments(fridayBundle);
        adapter.addFrag(friday_fragment, "Friday");

        //setting up Saturday meals
        Bundle saturdayBundle = new Bundle();
        saturdayBundle.putParcelableArrayList("sat", (ArrayList<? extends Parcelable>) saturday_meals);
        saturdayBundle.putString("day", "sat");
        Fragment saturday_fragment = new FragmentMealPlanDaily();
        saturday_fragment.setArguments(saturdayBundle);
        adapter.addFrag(saturday_fragment, "Saturday");

        //setting up Sunday meals
        Bundle sundayBundle = new Bundle();
        sundayBundle.putParcelableArrayList("sunday", (ArrayList<? extends Parcelable>) sunday_meals);
        sundayBundle.putString("day", "sunday");
        Fragment sunday_fragment = new FragmentMealPlanDaily();
        sunday_fragment.setArguments(sundayBundle);
        adapter.addFrag(sunday_fragment, "Sunday");


        //set the view pager adapter
        //viewPager.setOffscreenPageLimit(7);
        viewPager.setAdapter(adapter);
        loadingProgress.setVisibility(ProgressBar.GONE);

    }

    private Recipe getRecipe(JSONArray recipesArray, int recipe_id) {

        Recipe recipe = new Recipe();
        for (int i=0; i<recipesArray.length(); i++){

            try {
                if (recipe_id == (recipesArray.getJSONObject(i).getJSONObject("recipe")).getInt(KEY_ID)){
                    JSONObject jsonObject = recipesArray.getJSONObject(i).getJSONObject("recipe");
                    recipe.setId(jsonObject.getInt(KEY_ID));
                    recipe.setTitle(jsonObject.getString(KEY_TITLE));
                    recipe.setDescription((jsonObject.getString(KEY_DESCRIPTION)));
                    recipe.setImageUrl(jsonObject.getString("thumbnail_image_url"));
                    recipe.setInstructions(jsonObject.getJSONArray(KEY_INSTRUCTIONS));
                    recipe.setCalories(jsonObject.getInt(KEY_CALORIES));
                    recipe.setProtein(jsonObject.getInt(KEY_PROTEIN));
                    recipe.setCarbs(jsonObject.getInt(KEY_CARB));
                    recipe.setFat(jsonObject.getInt(KEY_FAT));
                    recipe.setIngredients(jsonObject.getJSONArray(KEY_INGREDIENTS));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recipe;

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.toolbar_container);
        frameLayout.setVisibility(View.VISIBLE);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.toolbar_container);
        frameLayout.setVisibility(View.VISIBLE);
    }
}
