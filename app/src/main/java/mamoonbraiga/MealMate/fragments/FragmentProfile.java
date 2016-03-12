package mamoonbraiga.MealMate.fragments;

import android.graphics.drawable.Drawable;
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

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class FragmentProfile extends Fragment{
    private MaterialViewPager vPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final Drawable header1 = getResources().getDrawable(R.drawable.header_pasta);
        final Drawable header2 = getResources().getDrawable(R.drawable.pizza_header);

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");

        vPager = (MaterialViewPager) view.findViewById(R.id.materialViewPager);
        vPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(R.color.ColorPrimary, header1);
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(R.color.ColorPrimary, header2);
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.lottieanddoof.com/wp-content/uploads/2013/06/IMG_9652.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        ViewPager viewPager = vPager.getViewPager();
        setUpViewPager(viewPager);
        vPager.getPagerTitleStrip().setViewPager(vPager.getViewPager());

        getUserInfo();
        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapterProfile adapter = new ViewPagerAdapterProfile(getActivity().getSupportFragmentManager());
        adapter.addFrag(new FragmentLikedRecipes(), "Liked Recipes");
        adapter.addFrag(new FragmentYourRecipes(), "Your Recipes");
        viewPager.setAdapter(adapter);
    }

    private void getUserInfo() {

    }
    public static class ViewPagerAdapterProfile extends FragmentStatePagerAdapter {


        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapterProfile(FragmentManager fm) {
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
