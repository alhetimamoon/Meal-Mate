package mamoonbraiga.MealMate.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-12-27.
 */
public class FragmentNutritions extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_nutritions, container, false);
        return view;
    }
}
