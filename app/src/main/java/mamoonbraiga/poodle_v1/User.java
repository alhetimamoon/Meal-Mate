package mamoonbraiga.poodle_v1;

import java.util.List;

/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class User {

    private String name;
    private String email;
    private List<Recipe> recipes;

    public User(List<Recipe> recipes, String email, String name) {
        this.recipes = recipes;
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

}
