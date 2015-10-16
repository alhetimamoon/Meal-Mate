package mamoonbraiga.poodle_v1;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by MamoonBraiga on 2015-10-14.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position);

    }

    private void selectItem(int position) {

    }
}
