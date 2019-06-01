package com.example.loadphoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.loadphoto.neglide.Glide;
import com.example.loadphoto.neglide.RequestListener;

public class MainActivity extends AppCompatActivity {

    private static final int REUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };
    LinearLayout scroll_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scroll_line = findViewById(R.id.scroll_line);

        //最简单的请求图片方式
        verifyStoragePermission(this);
    }

    public static void verifyStoragePermission(Activity activity){
        try{
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,"android.permission.WRITE_EXTERNAL_STORAGE");
            if(permission != PackageManager.PERMISSION_GRANTED){
                //没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public void single(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        scroll_line.addView(imageView);

        //设置占位图片
        Glide.with(this).load("https://www.dxsabc.com/api/xiaohua/upload/min_img/20190110/20190110AdOgzcLVqR.jpg").loading(R.mipmap.ic_launcher).listener(new RequestListener() {
            @Override
            public boolean onSuccess(Bitmap bitmap) {
                Toast.makeText(MainActivity.this,"coming",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        }).into(imageView);
    }

    public void more(View view){
        for(int i = 1;i <= 10; i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            scroll_line.addView(imageView);
            String url = null;
            switch (i){
                case 1:
                    url = "http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg";
                    break;
                case 2:
                    url = "http://c.hiphotos.baidu.com/image/pic/item/30adcbef76094b36de8a2fe5a1cc7cd98d109d99.jpg";
                    break;
                case 3:
                    url = "http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg";
                    break;
                case 4:
                    url = "http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg";
                    break;
                case 5:
                    url = "http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg";
                    break;
                case 6:
                    url = "http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg";
                    break;
                case 7:
                    url = "http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg";
                    break;
                case 8:
                    url = "http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg";
                    break;
                case 9:
                    url = "http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg";
                    break;
                case 10:
                    url = "http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg";
                    break;
            }

            //设置占位图片
            Glide.with(this).load(url+"").loading(R.mipmap.ic_launcher).into(imageView);
        }
    }
}
