package mamoonbraiga.poodle_v1.extras;

import android.app.Application;
import android.content.Context;

/**
 * Created by MamoonBraiga on 2015-11-09.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;
    }
    public static MyApplication getInsatnce(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
