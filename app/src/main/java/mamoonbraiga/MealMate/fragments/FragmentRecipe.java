package mamoonbraiga.MealMate.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.ArrayList;
import java.util.List;

import mamoonbraiga.MealMate.extras.Recipe;
import mamoonbraiga.MealMate.network.VolleySingleton;
import mamoonbraiga.poodle_v3.R;


public class FragmentRecipe extends Fragment{

    private VolleySingleton volleySingleton = VolleySingleton.getsInstance();
    private Bundle bundle;
    private int id;
    private Recipe recipe = new Recipe();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        final View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        bundle = getArguments();
        id = bundle.getInt("id");
        recipe = bundle.getParcelable(String.valueOf(id));

        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getTitle());
        toolbar.setVisibility(View.VISIBLE);
        /** view pager and tab setup **/
        final MaterialViewPager vPager = (MaterialViewPager) view.findViewById(R.id.recipeViewPager);
        vPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.ColorPrimaryDark,
                                recipe.getImageUrl());
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        /** view pager and tab setup **/
        ViewPager viewPager = vPager.getViewPager();
        setUpViewPager(viewPager);
        vPager.getPagerTitleStrip().setViewPager(vPager.getViewPager());

        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());


        //setup description bundle and tab
        Fragment description_fragment = new FragmentDescription();
        Bundle descriptionBundle = new Bundle();
        descriptionBundle.putInt("id", id);
        descriptionBundle.putParcelable(String.valueOf(id), recipe);
        description_fragment.setArguments(descriptionBundle);
        adapter.addFrag(description_fragment, "Description");

        //setup ingredients tab and bundle
        Fragment ingredients_fragment = new FragmentIngredients();
        Bundle ingredientsBundle = new Bundle();
        ingredientsBundle.putInt("id", id);
        ingredientsBundle.putParcelable(String.valueOf(id), recipe);
        ingredients_fragment.setArguments(ingredientsBundle);
        adapter.addFrag(ingredients_fragment, "Ingredients");


        //setup nutrition tab and bundle
        Fragment nutrition_fragment = new FragmentNutrition();
        Bundle nutritionBundle = new Bundle();
        nutritionBundle.putInt("id", id);
        nutritionBundle.putParcelable(String.valueOf(id), recipe);
        nutrition_fragment.setArguments(nutritionBundle);
        adapter.addFrag(nutrition_fragment, "Nutrition");

        viewPager.setAdapter(adapter);

    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter{


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
    @Override
    public void onPause(){
        super.onPause();
    }

}