package com.example.loadphoto.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.loadphoto.neglide.BitmapRequest;

//内存和sd的合集
public class DoubleLruCache implements BitmapCache {

    //内存，磁盘
    private MemoryLruCache lruCache;
    private DiskBitmapCache bitmapCache;

    public DoubleLruCache(Context context) {
        bitmapCache = DiskBitmapCache.getInstance(context);
        lruCache = MemoryLruCache.getInstance();
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        lruCache.put(request, bitmap);
        bitmapCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap = lruCache.get(request);
        if (bitmap == null) {
            bitmap = bitmapCache.get(request);
            lruCache.put(request, bitmap);
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest request) {
        lruCache.remove(request);
        bitmapCache.remove(request);
    }
}
