package mamoonbraiga.MealMate.fragments;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import mamoonbraiga.MealMate.activities.MainActivity;
import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;


public class FragmentRecipe extends Fragment{

    private ImageView header;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private Bundle bundle;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        final View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        header = (ImageView) view.findViewById(R.id.htab_header);  //setting up the header
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
        bundle = mainActivity.getSavedData();
        Recipe recipe = bundle.getParcelable("recipe");
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        //load the header
        imageLoader.get(recipe.getImageUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Drawable d = new BitmapDrawable(getResources(), response.getBitmap());
                header.setBackground(d);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.htab_toolbar);
        mainActivity.setActionBar(toolbar);
        mainActivity.getActionBar().setDisplayHomeAsUpEnabled(true);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.htab_viewpager);
        viewPager.setVerticalScrollBarEnabled(true);
        setUpViewPager(viewPager);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.htab_tabs);
        //tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF5722"));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        /**
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        showToast("One");
                        break;
                    case 1:
                        showToast("Two");
                        break;
                    case 2:
                        showToast("Three");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });**/
        return view;
    }

    private void showToast(String msg) {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new FragmentDescription(), "Description");
        adapter.addFrag(new FragmentIngredients(), "Ingredients");
        adapter.addFrag(new FragmentNutrition(), "Nutrition");
        viewPager.setAdapter(adapter);

    }

    static class ViewPagerAdapter extends FragmentPagerAdapter{


        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }

    }

}