package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.MealMate.adapters.RecyclerViewAdapter;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2016-03-05.
 */
public class FragmentMealPlanDaily extends Fragment {

    private List<Recipe> breakfastRecipes = new ArrayList<>();
    private List<Recipe> lunchRecipes = new ArrayList<>();
    private List<Recipe> dinnerRecipes = new ArrayList<>();
    private List<Recipe> snackRecipes = new ArrayList<>();
    private RecyclerView breakfastRecyclerVew;
    private RecyclerView lunchRecyclerVew;
    private RecyclerView dinnerRecyclerVew;
    private RecyclerView snacksRecyclerVew;

    private RecyclerViewAdapter breakfastAdapter;
    private RecyclerViewAdapter lunchAdapter;
    private RecyclerViewAdapter dinnerAdapter;
    private RecyclerViewAdapter snackAdapter;


    public FragmentMealPlanDaily(){

    }
    public FragmentMealPlanDaily(List<Recipe> breakfastRecipes, List<Recipe> lunchRecipes,
                                 List<Recipe> dinnerRecipes, List<Recipe>snackRecipes){
        this.breakfastRecipes = breakfastRecipes;
        this.lunchRecipes = lunchRecipes;
        this.dinnerRecipes = dinnerRecipes;
        this.snackRecipes = snackRecipes;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_daily_mealplan, container, false);

        breakfastRecyclerVew = (RecyclerView) view.findViewById(R.id.breakfast_recyclerView);
        breakfastRecyclerVew.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        lunchRecyclerVew = (RecyclerView) view.findViewById(R.id.lunch_recyclerView);
        lunchRecyclerVew.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        dinnerRecyclerVew = (RecyclerView) view.findViewById(R.id.dinner_recyclerView);
        dinnerRecyclerVew.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        snacksRecyclerVew = (RecyclerView) view.findViewById(R.id.snacks_recyclerView);
        snacksRecyclerVew.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        for (int i=0; i<10; i++){
            Recipe recipe = new Recipe();
            recipe.setTitle("Three Cheese Pasta");
            recipe.setDescription("This is an amazing recipe");
            breakfastRecipes.add(recipe);
            lunchRecipes.add(recipe);
            dinnerRecipes.add(recipe);
            snackRecipes.add(recipe);
        }

        breakfastAdapter = new RecyclerViewAdapter(breakfastRecipes);
        breakfastRecyclerVew.setAdapter(breakfastAdapter);

        lunchAdapter = new RecyclerViewAdapter(lunchRecipes);
        lunchRecyclerVew.setAdapter(lunchAdapter);

        dinnerAdapter = new RecyclerViewAdapter(dinnerRecipes);
        dinnerRecyclerVew.setAdapter(dinnerAdapter);

        snackAdapter = new RecyclerViewAdapter(snackRecipes);
        snacksRecyclerVew.setAdapter(snackAdapter);







        return view;
    }
}
