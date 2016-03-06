package mamoonbraiga.MealMate.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2016-03-03.
 */
public class FragmentMealPlan extends Fragment {

    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_mealplan, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.meal_plan_toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.drawer_view);
        toolbar.showOverflowMenu();



        TextView toolbarName = (TextView) view.findViewById(R.id.toolbarName);
        toolbarName.setText("Awesome Mealplan");

        mainActivity = (MainActivity) getActivity();
        mainActivity.setSupportActionBar(toolbar);


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.meal_plan_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.meal_plan_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        hideToolbar();

        setupViewPager(viewPager);

        return view;

    }

    private void hideToolbar() {
        Toolbar toolbar;
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(mainActivity.getSupportFragmentManager());
        adapter.addFrag(new FragmentMealPlanDaily(), "Monday");
        adapter.addFrag(new FragmentMealPlanDaily(), "Tuesday");
        adapter.addFrag(new FragmentMealPlanDaily(), "Wednesday");
        adapter.addFrag(new FragmentMealPlanDaily(), "Thursday");
        adapter.addFrag(new FragmentMealPlanDaily(), "Friday");
        adapter.addFrag(new FragmentMealPlanDaily(), "Saturday");
        adapter.addFrag(new FragmentMealPlanDaily(), "Sunday");


        viewPager.setAdapter(adapter);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
