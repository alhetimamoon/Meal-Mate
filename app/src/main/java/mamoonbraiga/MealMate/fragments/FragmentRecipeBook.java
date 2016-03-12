package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baoyz.widget.PullRefreshLayout;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.MealMate.adapters.AdapterRecipeBook;
import mamoonbraiga.MealMate.extras.API;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;

import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_CALORIES;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_CARB;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_DESCRIPTION;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_FAT;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_ID;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_IMAGE;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_INGREDIENTS;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_INSTRUCTIONS;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_PROTEIN;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_TITLE;
/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class FragmentRecipeBook extends Fragment{
    private RequestQueue requestQueue;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private AdapterRecipeBook adapterRecipeBook;
    FloatingActionButton add_recipe_button;
    private RecyclerView reList;
    public static int ID=1;
    private PullRefreshLayout refreshLayout;
    Bundle bundle;
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bundle = new Bundle();
        //inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);
        add_recipe_button = (FloatingActionButton) view.findViewById(R.id.add_recipe_button);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_recipes);
        refreshLayout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        reList = (RecyclerView) view.findViewById(R.id.listRecipes);
        reList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        reList.setLayoutManager(llm);
        add_recipe_button.attachToRecyclerView(reList);
        ((MainActivity) getActivity()).getSupportActionBar().show();
        /**** JSON Request *****/
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        sendJsonRequest();
        /**** JSON Request *****/

        add_recipe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentNewRecipe = new FragmentAddRecipe();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(ft.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.flContent, fragmentNewRecipe).addToBackStack("recipe card").commit();
            }
        });

        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendJsonRequest();
            }
        });
        return view;
    }

    @Override
    public void onPause(){
        super.onPause();

    }
    private void createList(JSONArray JSONRecipes) {
        recipes = new ArrayList<>();
        for (int i=0; i<JSONRecipes.length(); i++){
            Recipe recipe = new Recipe();
            try {
                recipe.setId((Integer) JSONRecipes.getJSONObject(i).get(KEY_ID));
                recipe.setTitle((String) JSONRecipes.getJSONObject(i).get(KEY_TITLE));
                recipe.setDescription((String) JSONRecipes.getJSONObject(i).get(KEY_DESCRIPTION));
                recipe.setImageUrl(JSONRecipes.getJSONObject(i).getString(KEY_IMAGE));
                recipe.setInstructions(JSONRecipes.getJSONObject(i).getJSONArray(KEY_INSTRUCTIONS));
                recipe.setCalories(JSONRecipes.getJSONObject(i).getInt(KEY_CALORIES));
                recipe.setProtein(JSONRecipes.getJSONObject(i).getInt(KEY_PROTEIN));
                recipe.setCarbs(JSONRecipes.getJSONObject(i).getInt(KEY_CARB));
                recipe.setFat(JSONRecipes.getJSONObject(i).getInt(KEY_FAT));
                recipe.setIngredients(JSONRecipes.getJSONObject(i).getJSONArray(KEY_INGREDIENTS));
                recipes.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapterRecipeBook = new AdapterRecipeBook(recipes);
        reList.setAdapter(adapterRecipeBook);
        adapterRecipeBook.setOnItemClickListener(new AdapterRecipeBook.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(String.valueOf(recipes.get(position).getId()), recipes.get(position));
                bundle.putInt("id", recipes.get(position).getId());
                Fragment fragmentRecipe = new FragmentRecipe();
                fragmentRecipe.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(ft.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.flContent, fragmentRecipe).addToBackStack("recipe card").commit();
            }
        });
        progressBar.setVisibility(ProgressBar.GONE);
        refreshLayout.setRefreshing(false);

    }
    private void sendJsonRequest() {
        final StringRequest request = new StringRequest(Request.Method.GET, API.recipes_api , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }
    public void parseJSONResponse(String response){
        try {
            JSONArray array = new JSONArray(response);
            createList(array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Trending");
    }

}
