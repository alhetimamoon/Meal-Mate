package mamoonbraiga.MealMate.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import mamoonbraiga.MealMate.adapters.AdapterRecipeBook;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.*;


/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class FragmentRecipeBook extends Fragment{
    private RequestQueue requestQueue;
    private List<Recipe> recipes;
    private AdapterRecipeBook adapterRecipeBook;
    private RecyclerView reList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        //inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);
        reList = (RecyclerView) view.findViewById(R.id.listRecipes);
        reList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        reList.setLayoutManager(llm);

        /**** JSON Request *****/
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        sendJsonRequest();
        /**** JSON Request *****/

        //AdapterRecipeBook adapterRecipeBook = new AdapterRecipeBook(recipes);
        //reList.setAdapter(adapterRecipeBook);

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
                recipe.setTitle((String) JSONRecipes.getJSONObject(i).get(KEY_TITLE));
                recipe.setDescription((String) JSONRecipes.getJSONObject(i).get(KEY_DECRIPTION));
                recipe.setImageUrl(JSONRecipes.getJSONObject(i).getString(KEY_IMAGE));
                recipes.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        adapterRecipeBook = new AdapterRecipeBook(recipes);
        reList.setAdapter(adapterRecipeBook);
    }
    public String getRequestURL(int limit){
        return "https://meal-mate.herokuapp.com/recipes.json";
    }

    private void sendJsonRequest() {
        final StringRequest request = new StringRequest(Request.Method.GET, getRequestURL(1) , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), "RESPONSE "+response, Toast.LENGTH_LONG).show();
                parseJSONResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "ERROR "+error.getMessage(),Toast.LENGTH_LONG).show();
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


}
