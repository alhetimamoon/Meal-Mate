package mamoonbraiga.MealMate.extras;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MamoonBraiga on 2016-03-06.
 */
public class Meal implements Parcelable {

    private int recipe_id;
    private String meal_time;
    private String day_of_week;
    private Recipe recipe;
    private double serving_size;


    public Meal(int recipe_id, String meal_time, String day_of_week, Recipe recipe, double serving_size){
        this.recipe_id = recipe_id;
        this.meal_time = meal_time;
        this.day_of_week = day_of_week;
        this.recipe = recipe;
        this.serving_size = serving_size;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getMeal_time() {
        return meal_time;
    }

    public void setMeal_time(String meal_time) {
        this.meal_time = meal_time;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipe_id);
        dest.writeString(meal_time);
        dest.writeString(day_of_week);
        dest.writeDouble(serving_size);
    }
}
