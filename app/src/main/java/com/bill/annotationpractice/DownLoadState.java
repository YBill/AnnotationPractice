package com.bill.annotationpractice;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Bill on 2018/7/29.
 * <p>
 * 注解
 */

@IntDef({
        DownLoadState.WAIT,
        DownLoadState.DOWNLOADING,
        DownLoadState.COMPLETE,
        DownLoadState.ERROR,
        DownLoadState.NONE
})
@Retention(RetentionPolicy.SOURCE)
public @interface DownLoadState {
    int NONE = 0;
    int WAIT = 1;
    int DOWNLOADING = 2;
    int COMPLETE = 3;
    int ERROR = 4;
}

