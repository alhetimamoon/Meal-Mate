package mamoonbraiga.poodle_v1;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.InjectView;
import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-16.
 */
public class RecipeBookActivity extends MainActivity{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;  //we use the bitter knife library, its easier

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        //display the navigation drawer on this activity
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.recipe_book_layout, null, false);

    }
}
