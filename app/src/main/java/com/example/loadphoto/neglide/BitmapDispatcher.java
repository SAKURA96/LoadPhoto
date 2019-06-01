package com.example.loadphoto.neglide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;

import android.os.Handler;

import com.example.loadphoto.MyApplication;
import com.example.loadphoto.cache.DoubleLruCache;

public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler(Looper.getMainLooper());

    //创建一个阻塞队列
    private LinkedBlockingDeque<BitmapRequest> requestQueue;

    //获取三级缓存对象
    private DoubleLruCache doubleLruCache = new DoubleLruCache(MyApplication.getInstance());

    public BitmapDispatcher(LinkedBlockingDeque<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                BitmapRequest br = requestQueue.take();

                //设置占位图片
                showLoadingImg(br);

                //加载图片
                Bitmap bitmap = findBitMap(br);

                //把图片显示到ImageView
                showImageView(br, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageView(final BitmapRequest br, final Bitmap bitmap) {
        //equals处保证图片不错位
        if (bitmap != null && br.getImageView() != null && br.getUrlMd5().equals(br.getImageView().getTag())) {
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    if (br.getRequestListener() != null) {
                        RequestListener listener = br.getRequestListener();
                        listener.onSuccess(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap findBitMap(BitmapRequest br) {
        Bitmap bitmap = null;
        bitmap = doubleLruCache.get(br);
        if(bitmap == null){
            bitmap = downloadImage(br.getUrl());
            if(bitmap != null){
                doubleLruCache.put(br,bitmap);
            }
        }
        return bitmap;
    }

    private void showLoadingImg(BitmapRequest br) {
        if (br.getResID() > 0 && br.getImageView() != null) {
            final int resID = br.getResID();
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resID);
                }
            });
            imageView.setImageResource(resID);
        }
    }

    private Bitmap downloadImage(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;

        try {
            //创建一个UL对象
            URL url = new URL(uri);
            //然后使用HttpURLConnection通过URL去开始读取数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
