package com.fmt.ming.paotui.utils;


import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ImageLoaderUtil {
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public static DisplayImageOptions getOptions() {
        return options;
    }

}
