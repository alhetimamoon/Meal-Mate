package mamoonbraiga.MealMate.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import mamoonbraiga.MealMate.fragments.FragmentIngredients;
import mamoonbraiga.MealMate.fragments.FragmentDescription;

/**
 * Created by MamoonBraiga on 2015-11-26.
 */
public class AdapterTabsPager extends FragmentPagerAdapter{

    public AdapterTabsPager(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new FragmentDescription();
            case 1:
                return new FragmentIngredients();
            case 2:
                return new FragmentIngredients();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
