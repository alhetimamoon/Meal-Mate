package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.MealMate.adapters.SearchResultsRecyclerViewAdapter;
import mamoonbraiga.MealMate.extras.API;
import mamoonbraiga.MealMate.extras.Keys;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;

import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_ID;

/**
 * Created by MamoonBraiga on 2016-03-14.
 */
public class FragmentSearchResults extends Fragment {

    private List<Recipe> recipes = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private String url = API.search_api;

    String query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.search_result_list);
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getArguments();
        query = bundle.getString("query");
        url = url + query;
        sendJSONRequest();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);


        return view;
    }

    private void sendJSONRequest() {
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseJSONArray(response);
                SearchResultsRecyclerViewAdapter adapter = new SearchResultsRecyclerViewAdapter(recipes);
                adapter.setOnItemClickListener(new SearchResultsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
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
                recyclerView.setAdapter(adapter);
            }
        }
            ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    private void parseJSONArray(JSONArray response) {

        for(int i=0; i<response.length(); i++){
            Recipe recipe = new Recipe();
            try {
                JSONObject jsonObject= response.getJSONObject(i);
                recipe.setTitle(jsonObject.getString(Keys.RecipeKeys.KEY_TITLE));
                recipe.setImageUrl(jsonObject.getString(Keys.RecipeKeys.KEY_IMAGE));
                recipe.setDescription(jsonObject.getString(Keys.RecipeKeys.KEY_DESCRIPTION));
                recipe.setId(jsonObject.getInt(KEY_ID));
                recipe.setCalories(jsonObject.getInt(Keys.RecipeKeys.KEY_CALORIES));
                recipe.setProtein(jsonObject.getInt(Keys.RecipeKeys.KEY_PROTEIN));
                recipe.setCarbs(jsonObject.getInt(Keys.RecipeKeys.KEY_CARB));
                recipe.setFat(jsonObject.getInt(Keys.RecipeKeys.KEY_FAT));
                recipe.setIngredients(jsonObject.getJSONArray(Keys.RecipeKeys.KEY_INGREDIENTS));
                recipe.setInstructions(jsonObject.getJSONArray(Keys.RecipeKeys.KEY_INSTRUCTIONS));

                recipes.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
