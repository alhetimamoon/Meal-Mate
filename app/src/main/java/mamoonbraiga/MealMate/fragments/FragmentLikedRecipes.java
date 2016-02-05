package mamoonbraiga.MealMate.fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.MealMate.adapters.RecyclerViewAdapter;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.poodle_v3.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MamoonBraiga on 2016-02-04.
 */
public class FragmentLikedRecipes extends Fragment {

    private static final String URL= "http://mealmate.co/api/profile?";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static final int ITEM_COUNT = 6;
    private List<Recipe> likedRecipes = new ArrayList<>();
    private String token;
    private int id;
    private StringBuilder urlBuilder = new StringBuilder();
    private String getURL;



    public static FragmentLikedRecipes newInstance() {
        return new FragmentLikedRecipes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_profile_liked_recipes, container, false);
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getContext());
        token = (mSharedPreference.getString("token", null));
        id = (mSharedPreference.getInt("id", 0));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewMaterialAdapter(new RecyclerViewAdapter(likedRecipes), 2);

        createURL();
        addLikedRecipes();
        new UserRecipesTask().execute(getURL);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void createURL() {
        urlBuilder.append(URL);
        urlBuilder.append("authentication_token=");
        urlBuilder.append(token);
        urlBuilder.append("&user_id=");
        urlBuilder.append(id);
        getURL = urlBuilder.toString();

    }

    private void addLikedRecipes() {


        for (int i = 0; i < ITEM_COUNT; ++i){
            Recipe recipe = new Recipe();
            recipe.setTitle("Three Cheese Pasta");
            recipe.setDescription("skdjf ksdjf sldkjf ksldfj ");
            likedRecipes.add(recipe);
        }

    }

    public class UserRecipesTask extends AsyncTask<String, Void, JSONObject> {
        private static final String TAG = "BackgroundTask";
        private String response;
        JSONObject message;

        @Override
        protected JSONObject doInBackground(String... url) {
            Response response = null;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url[0])
                    .get()
                    .build();

            try {

                response = client.newCall(request).execute();
                message = new JSONObject(response.body().string());
                return message;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
        public JSONObject getResponse(){
            return this.message;
        }

    }
}
