package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.MealMate.adapters.RecyclerViewAdapter;
import mamoonbraiga.MealMate.extras.Meal;
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
    Bundle bundle;
    List<Meal> meals = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_daily_mealplan, container, false);

        breakfastRecipes = new ArrayList<>();
        lunchRecipes = new ArrayList<>();
        dinnerRecipes = new ArrayList<>();
        snackRecipes = new ArrayList<>();
        meals = new ArrayList<>();
        bundle = new Bundle();

        String day = getArguments().getString("day");

        this.meals = getArguments().getParcelableArrayList(day);


        for (Meal meal:meals){
            switch (meal.getMeal_time()){
                case "breakfast":
                    breakfastRecipes.add(meal.getRecipe());
                    break;

                case "lunch":
                    lunchRecipes.add(meal.getRecipe());
                    break;

                case "diner":
                    dinnerRecipes.add(meal.getRecipe());
                    break;

                case "snacks":
                    snackRecipes.add(meal.getRecipe());
                    break;
            }
        }

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

        //breakfast view
        breakfastAdapter = new RecyclerViewAdapter(breakfastRecipes);
        breakfastRecyclerVew.setAdapter(breakfastAdapter);
        breakfastAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                bundle = new Bundle();
                int id = breakfastRecipes.get(position).getId();
                bundle.putInt("id", id);
                bundle.putParcelable(String.valueOf(breakfastRecipes.get(position).getId()), breakfastRecipes.get(position));
                Fragment fragmentRecipe = new FragmentRecipe();
                fragmentRecipe.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(ft.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.flContent, fragmentRecipe).addToBackStack("recipe card").commit();

            }
        });

        //lunch view
        lunchAdapter = new RecyclerViewAdapter(lunchRecipes);
        lunchRecyclerVew.setAdapter(lunchAdapter);
        lunchAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                bundle = new Bundle();
                int id = lunchRecipes.get(position).getId();
                bundle.putInt("id", id);
                bundle.putParcelable(String.valueOf(id), lunchRecipes.get(position));
                Fragment fragmentRecipe = new FragmentRecipe();
                fragmentRecipe.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(ft.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.flContent, fragmentRecipe).addToBackStack("recipe card").commit();

            }
        });


        //dinner view
        dinnerAdapter = new RecyclerViewAdapter(dinnerRecipes);
        dinnerRecyclerVew.setAdapter(dinnerAdapter);


        //snacks view
        snackAdapter = new RecyclerViewAdapter(snackRecipes);
        snacksRecyclerVew.setAdapter(snackAdapter);


        return view;
    }


}
