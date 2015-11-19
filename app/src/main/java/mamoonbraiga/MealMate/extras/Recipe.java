package mamoonbraiga.MealMate.extras;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-28.
 */
public class Recipe implements Parcelable{

    protected  int id;
    protected  String title;
    protected  ImageView image;
    protected  String ImageUrl;
    protected  String description;
    protected  int calories;
    protected  int protein;
    protected  int carbs;
    protected  int fat;

    protected Recipe(Parcel in) {
        id = in.readInt();
        title = in.readString();
        ImageUrl = in.readString();
        description = in.readString();
        calories = in.readInt();
        protein = in.readInt();
        carbs = in.readInt();
        fat = in.readInt();
    }
    public Recipe(){

    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(ImageUrl);
        dest.writeString(description);
        dest.writeInt(calories);
        dest.writeInt(protein);
        dest.writeInt(carbs);
        dest.writeInt(fat);
    }
}
