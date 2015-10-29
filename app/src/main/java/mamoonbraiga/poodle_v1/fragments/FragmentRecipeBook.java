package mamoonbraiga.poodle_v1.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.InjectView;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class FragmentRecipeBook extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);
        return view;
    }

}
