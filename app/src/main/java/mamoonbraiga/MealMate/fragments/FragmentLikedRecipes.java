package mamoonbraiga.MealMate.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.MealMate.adapters.RecyclerViewAdapter;
import mamoonbraiga.MealMate.extras.API;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;

import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_DESCRIPTION;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_IMAGE;
import static mamoonbraiga.MealMate.extras.Keys.RecipeKeys.KEY_TITLE;

/**
 * Created by MamoonBraiga on 2016-02-04.
 */
public class FragmentLikedRecipes extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerViewAdapter adapter;
    private List<Recipe> likedRecipes = new ArrayList<>();
    private String token;
    private int id;
    private StringBuilder urlBuilder = new StringBuilder();
    private String getURL;
    private JSONArray likedRecipesJSONArray;
    private RequestQueue requestQueue;
    private Bundle bundle;
    public static int BUNDLE_ID=1;
    private ProgressBar progressBar;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.fragment_profile_liked_recipes, container, false);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getContext());
        token = (mSharedPreference.getString("token", null));
        id = (mSharedPreference.getInt("id", 0));
        bundle = new Bundle();
        //recycle view setup
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_recipes);
        //prepare GET url
        createURL();

        //send JSON request
        sendJsonRequest();

        return view;
    }

    /**
     *
     * This method sends the JSON request and set up the ui elements
     */
    private void sendJsonRequest() {
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                addLikedRecipes(response);
                adapter = new RecyclerViewAdapter(likedRecipes);
                mAdapter = new RecyclerViewMaterialAdapter(adapter, 2);
                MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                mRecyclerView.setAdapter(mAdapter);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.saveData(BUNDLE_ID, bundle);
                progressBar.setVisibility(ProgressBar.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);}

    private void createURL() {
        urlBuilder.append(API.user_recipes_base);
        urlBuilder.append("auth_token=");
        urlBuilder.append(token);
        urlBuilder.append("&user_id=");
        urlBuilder.append(id);
        getURL = urlBuilder.toString();

    }


    private void addLikedRecipes(JSONObject responseBack) {
        try {
            likedRecipesJSONArray = responseBack.getJSONArray("liked_recipes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < likedRecipesJSONArray.length(); ++i){
                Recipe recipe = new Recipe();
            try {
                recipe.setTitle((String) likedRecipesJSONArray.getJSONObject(i).get(KEY_TITLE));
                recipe.setDescription((String) likedRecipesJSONArray.getJSONObject(i).get(KEY_DESCRIPTION));
                recipe.setImageUrl((String) likedRecipesJSONArray.getJSONObject(i).get(KEY_IMAGE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
                likedRecipes.add(recipe);
            }
        }

}