package mamoonbraiga.poodle_v1.extras;
import mamoonbraiga.poodle_v3.R;
import android.widget.ImageView;

/**
 * Created by MamoonBraiga on 2015-10-28.
 */
public class Recipe {

    protected static String title;
    protected static ImageView image;
    protected static String description;

    public void Recipe(String title, ImageView image, String description){

        this.title = title;
        this.image.setImageResource(R.drawable.pasta);
        this.description = description;


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
}
