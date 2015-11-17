package mamoonbraiga.MealMate.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-11-16.
 */
public class FragmentRecipe extends Fragment {

    private ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.recipe_layout, container, false);
        mListView = (ListView) view.findViewById(R.id.listview);
        View fakeHeader = inflater.inflate(R.layout.fake_header, mListView, false);
        mListView.addHeaderView(fakeHeader);
        //mListView.setOnScrollChangeListener(new );
        return view;
    }
    public int getScrollY(){
        View v = mListView.getChildAt(0);
        if (v == null){
            return 0;
        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = v.getTop();

        int headerHeight = 0;

        if (firstVisiblePosition >= 1){
            //headerHeight = mPlaceHolderView.getHeight();
        }
        return -top + firstVisiblePosition * v.getHeight() + headerHeight;
    }
}
