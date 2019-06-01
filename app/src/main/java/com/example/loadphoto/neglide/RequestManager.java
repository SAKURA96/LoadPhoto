package com.example.loadphoto.neglide;

import java.util.concurrent.LinkedBlockingDeque;

public class RequestManager {

    private static RequestManager requestManager = new RequestManager();

    //创建阻塞队列
    private LinkedBlockingDeque<BitmapRequest> requestQueue = new LinkedBlockingDeque<>();

    //创建一个线程数组
    private BitmapDispatcher[] bitmapDispatchers;

    private RequestManager(){
        start();
    }

    //开启所有线程的总开关
    public void start(){
        stop();
        startAllDispatcher();
    }

    public static RequestManager getInstance(){
        return requestManager;
    }

    //将图片请求对象添加到队列中去
    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if(bitmapRequest == null) return;

        //判断当前请求是否在队列中
        if(!requestQueue.contains(bitmapRequest)) requestQueue.add(bitmapRequest);

    }

    //创建并开始所有的线程
    public void startAllDispatcher(){
        //获取手机支持的单个应用最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[threadCount];

        for(int i = 0;i < threadCount; i++){
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();
            //要将每个dispatcher放到数组中，方便统一管理
            bitmapDispatchers[i] = bitmapDispatcher;
        }
    }

    //停止所有的线程
    public void stop(){
        if(bitmapDispatchers != null && bitmapDispatchers.length > 0){
            for (BitmapDispatcher bitmapDispatcher:bitmapDispatchers){
                if(!bitmapDispatcher.isInterrupted()){
                    bitmapDispatcher.interrupt();
                }
            }
        }
    }

}
