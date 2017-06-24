package com.example.administrator.eddievideoplay;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.administrator.eddievideoplay.adapter.MyAdapter;
import com.example.administrator.eddievideoplay.bean.LocalVideoBean;
import com.example.administrator.eddievideoplay.view.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.mStateView)
    StateView mMStateView;
    @BindView(R.id.btn_top)
    Button mBtnTop;
    @BindView(R.id.activity_main)
    RelativeLayout mActivityMain;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    private MyAdapter mAdapter;
    private GetLocalVideosTask mGetVideoTask;
    private StateView mStateView;// 加载状态控件，加载中、失败、成功
    private List<LocalVideoBean> mVideoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = View.inflate(getBaseContext(),
                R.layout.activity_main, null);
        setContentView(mView);
        ButterKnife.bind(this);
        initRecycleView();
        initTitle();
        initListener();
        initDatas();
    }

    private void initDatas() {
        //mStateView.setCurrentState(StateView.STATE_LOADING);
        mVideoList = new ArrayList<LocalVideoBean>();
        mAdapter = new MyAdapter(this, mVideoList);
        mRecycleView.setAdapter(mAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new PermissionUtils(this).needPermission(200);
        } else {
            getDatas();
        }


        mAdapter.setOnItemClickListen(new MyAdapter.OnItemClickListen() {
            @Override
            public void OnItemClick(View v, int position) {

            }
        });
    }


    private void initRecycleView() {
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {
        //放回顶部
        mBtnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListViewPos(0);
            }
        });

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDatas();
                        mRefresh.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        mAdapter.setOnItemClickListen(new MyAdapter.OnItemClickListen() {
            @Override
            public void OnItemClick(View v, int position) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //同意给与权限  可以再此处调用拍照
                    Log.i("用户同意权限", "user granted the permission!");
                    getDatas();
                } else {

                    // permission denied, boo! Disable the
                    // f用户不同意 可以给一些友好的提示
                    Log.i("用户不同意权限", "user denied the permission!");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void initTitle() {
        new TitleBuilder(this).setLeftImageRes(false, 0).setLeftTextOrImageListener(true, null).setMiddleTitleText("本地视频");
    }


    public void setListViewPos(int listViewPos) {
        mRecycleView.smoothScrollToPosition(0);
    }

    public void getDatas() {
        mGetVideoTask = new GetLocalVideosTask();
        mGetVideoTask.setSuccessListener(new GetLocalVideosTask.OnSuccessListener() {
            @Override
            public void onSuccess(List<LocalVideoBean> videos) {
                Log.e("aaaaa11111111", "onSuccess: "+videos.size() );
                if (videos.size() > 0) {
                    Log.e("aaaaa222222222", "onSuccess: "+videos.size() );
                    mVideoList.clear();
                    mVideoList.addAll(videos);
                    mAdapter.notifyDataSetChanged();
                } else {

                }

            }
        });
        mGetVideoTask.execute(this.getContentResolver());
    }

    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }


    /**
     * @param path 视频文件的路径
     * @param kind 缩略图的分辨率 ：MINI_KIND、MICRO_KIND、FULL_SCREEN_KIND
     * @return 返回的缩略图
     */

    public static Bitmap getVideoThumb2(String path, int kind) {
        return ThumbnailUtils.createVideoThumbnail(path, kind);
    }

    public static Bitmap getVideoThumb2(String path) {
        return getVideoThumb2(path,
                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }

}
