package mamoonbraiga.MealMate.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.MealMate.extras.API;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;
/**
 * Created by MamoonBraiga on 2015-12-27.
 */
public class FragmentDescription extends Fragment {

    private static final int GET = 0;
    private static final int POST = 1;

    private TextView description;
    private TextView recipe_title;

    private ArrayList<String> stepsList = new ArrayList<>();
    private ListView steps;
    private FloatingActionButton likeButton;
    private boolean recipe_liked  = false;
    private RequestQueue requestQueue;
    private String getURL;
    private String token;
    private int id;
    private int recipe_id;
    private StringBuilder urlBuilder = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //view setup
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        //get the saved data from main activity
        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle bundle = mainActivity.getSavedData();
        Recipe recipe = bundle.getParcelable("recipe");

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getContext());
        token = (mSharedPreference.getString("token", null));
        id = (mSharedPreference.getInt("id", 0));
        recipe_id = recipe.getId();


        description = (TextView) view.findViewById(R.id.description);
        recipe_title = (TextView) view.findViewById(R.id.title_recipe);
        steps = (ListView) view.findViewById(R.id.steps);
        likeButton = (FloatingActionButton) view.findViewById(R.id.like_recipe);
        likeButton.setBackgroundTintList(getResources().getColorStateList(R.color.iron));

        //prepare GET url
        createURL();

        //send JSON request
        sendJsonRequest(GET);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJsonRequest(POST);
            }
        });
        recipe_title.setText(recipe.getTitle());

        //set description
        description.setText(recipe.getDescription());

        String step;
        for (int i=0; i<recipe.getInstructions().length(); i++){
            try {
                step = recipe.getInstructions().getJSONObject(i).getString("step");
                stepsList.add(i+1 + ". " + step);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        populateListView(view);
        return view;
    }

    private void sendJsonRequest(int method) {
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();

        switch (method){
            case GET:
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getURL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("liked")) {
                                likeButton.setBackgroundTintList(getResources().getColorStateList(R.color.ColorPrimary));
                                recipe_liked = true;
                            }
                            else {
                                likeButton.setBackgroundTintList(getResources().getColorStateList(R.color.iron));
                                recipe_liked = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(request);
                break;

            case POST:
                final JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, getURL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response is: ", response.getString("message"));
                            if (recipe_liked) {
                                likeButton.setBackgroundTintList(getResources().getColorStateList(R.color.iron));
                                recipe_liked = false;
                            }
                            else {
                                recipe_liked = true;
                                likeButton.setBackgroundTintList(getResources().getColorStateList(R.color.ColorPrimary));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(request2);
                break;
        }

    }

    private void createURL() {
        urlBuilder.append(API.like_recipe_base);
        urlBuilder.append("auth_token=");
        urlBuilder.append(token);
        urlBuilder.append("&user_id=");
        urlBuilder.append(id);
        urlBuilder.append("&recipe_id=");
        urlBuilder.append(recipe_id);
        getURL = urlBuilder.toString();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void populateListView(View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),           //context
                R.layout.step,          //layout
                stepsList);             //the list of instructions

        steps.setAdapter(adapter);
        stepsList = new ArrayList<>();

    }
}
