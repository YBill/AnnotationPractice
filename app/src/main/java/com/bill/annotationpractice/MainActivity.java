package com.bill.annotationpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDownloadConst(-1);

        startDownload(DownLoadStateEnum.WAIT);

        startDownload(DownLoadState.WAIT);

    }

    /**
     * 注解约束输入值，代替枚举
     *
     * @param status
     */
    private void startDownload(@DownLoadState int status) {

        switch (status) {
            case DownLoadState.NONE:
                MLog.log("none");
                break;
            case DownLoadState.WAIT:
                MLog.log("start download");
                break;
            case DownLoadState.DOWNLOADING:
                MLog.log("download...");
                break;
            case DownLoadState.COMPLETE:
                MLog.log("complete");
                break;
            case DownLoadState.ERROR:
                MLog.log("error");
                break;
        }

    }

    /**
     * 枚举约束输入值
     *
     * @param status
     */
    private void startDownload(DownLoadStateEnum status) {

        switch (status) {
            case NONE:
                MLog.log("none");
                break;
            case WAIT:
                MLog.log("start download");
                break;
            case DOWNLOADING:
                MLog.log("download...");
                break;
            case COMPLETE:
                MLog.log("complete");
                break;
            case ERROR:
                MLog.log("error");
                break;
        }

    }

    /**
     * 常量
     *
     * @param status
     */
    private void startDownloadConst(int status) {

        switch (status) {
            case DownloadStatusConst.NONE:
                MLog.log("none");
                break;
            case DownloadStatusConst.WAIT:
                MLog.log("start download");
                break;
            case DownloadStatusConst.DOWNLOADING:
                MLog.log("download...");
                break;
            case DownloadStatusConst.COMPLETE:
                MLog.log("complete");
                break;
            case DownloadStatusConst.ERROR:
                MLog.log("error");
                break;
        }

    }


}
