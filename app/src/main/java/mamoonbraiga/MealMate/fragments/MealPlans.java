package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2016-03-12.
 */
public class MealPlans extends Fragment {

    private LayoutInflater layoutInflater;
    private SwipeStack swipeStack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_mealplans, container, false);
        swipeStack = (SwipeStack) view.findViewById(R.id.swipeStack);
        List<String> mealplans = new ArrayList<>();
        mealplans.add("test");
        mealplans.add("test");
        mealplans.add("test");
        mealplans.add("test");

        swipeStack.setAdapter(new SwipeStackAdapter(mealplans));
        this.layoutInflater = inflater;

        return view;
    }

    public class SwipeStackAdapter extends BaseAdapter {

        private List<String> mData;


        public SwipeStackAdapter(List<String> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.meal_plan_card, parent, false);
            return convertView;    }


    }

}
