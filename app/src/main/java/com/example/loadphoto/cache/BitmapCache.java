package com.example.loadphoto.cache;

import android.graphics.Bitmap;

import com.example.loadphoto.neglide.BitmapRequest;

public interface BitmapCache {

    //存入内存
    void put(BitmapRequest request, Bitmap bitmap);

    //读取缓存的图片
    Bitmap get(BitmapRequest request);

    //清楚缓存的图片
    void remove(BitmapRequest request);
}
