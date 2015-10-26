package mamoonbraiga.poodle_v1;

import java.util.List;

/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class User {

    private String name;
    private String email;
    private List<RecipeFragment> recipeFragments;

    public User(List<RecipeFragment> recipeFragments, String email, String name) {
        this.recipeFragments = recipeFragments;
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<RecipeFragment> getRecipeFragments() {
        return recipeFragments;
    }

}
