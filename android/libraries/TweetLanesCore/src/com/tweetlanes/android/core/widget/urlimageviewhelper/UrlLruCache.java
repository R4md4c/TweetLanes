package com.tweetlanes.android.core.widget.urlimageviewhelper;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class UrlLruCache extends LruCache<String, BitmapDrawable> implements ImageCache {
    private Context mContext = null;

	public UrlLruCache(int maxSize) {
        super(maxSize);
    }

    public UrlLruCache(Context ctx, int maxSize) {
    	super(maxSize);
    	mContext = ctx;
    }
    
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }
    
    @Override
    protected int sizeOf(String key, BitmapDrawable value) {
        if (value != null) {
            Bitmap b = value.getBitmap();
            if (b != null)
                return b.getRowBytes() * b.getHeight();
        }
        return 0;
    }

	@Override
	public Bitmap getBitmap(String url) {
		BitmapDrawable drawable = get(url);
		if (drawable != null) {
			return drawable.getBitmap();
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		if (mContext != null) {
			put(url, new BitmapDrawable(mContext.getResources(), bitmap));
		}
	}
}
