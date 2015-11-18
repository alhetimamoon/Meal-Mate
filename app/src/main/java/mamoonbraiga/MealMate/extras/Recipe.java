package mamoonbraiga.MealMate.extras;
import android.widget.ImageView;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-28.
 */
public class Recipe{

    protected  int id;
    protected  String title;
    protected  ImageView image;
    protected  String ImageUrl;
    protected  String description;
    protected  int calories;
    protected  int protein;
    protected  int carbs;
    protected  int fat;

    public void Recipe(){
        this.image.setImageResource(R.drawable.pasta);

    }

    public String getTitle() {
        return title;
    }

    public ImageView getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(ImageView image) {
        this.image =  image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  String getImageUrl() {
        return ImageUrl;
    }

    public  void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
