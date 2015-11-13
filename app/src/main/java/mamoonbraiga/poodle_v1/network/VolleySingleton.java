package mamoonbraiga.poodle_v1.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import mamoonbraiga.poodle_v1.extras.MyApplication;

/**
 * Created by MamoonBraiga on 2015-11-09.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;
    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache = new LruCache<>(((int) Runtime.getRuntime().maxMemory()/1024)/8);
            //handling cache images, we use LruCache
            //when the queue is full, the last element in the queue will be removed and would be ready for garbage collection
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }
    public static VolleySingleton getsInstance(){
        if (sInstance ==null) sInstance = new VolleySingleton();
        return sInstance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
