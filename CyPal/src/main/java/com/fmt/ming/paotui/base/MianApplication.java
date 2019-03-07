package com.fmt.ming.paotui.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

public class MianApplication extends Application {
    private int score = 1;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }


    public static void initImageLoader(Context context) {
        // 缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "universalimageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 缓存最大图片大小
                .diskCacheExtraOptions(480, 800, null) // 闪存最大图片大小
                .threadPoolSize(3) // default 最大线程数
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 线程优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default 线程处理队列，先进先出
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) // LruMemory
                .memoryCacheSize(2 * 1024 * 1024) // 缓存
                .memoryCacheSizePercentage(13)    // default 缓存比例？
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default 闪存缓存
                .diskCacheSize(50 * 1024 * 1024) // 闪存缓存大小
                .diskCacheFileCount(100) // 闪存缓存图片文件数量
                //                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default 文件名
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // LOG
                .build();
        // 全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

}