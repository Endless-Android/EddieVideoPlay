package com.example.administrator.eddievideoplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.eddievideoplay.MainActivity;
import com.example.administrator.eddievideoplay.R;
import com.example.administrator.eddievideoplay.bean.LocalVideoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eddie on 2017/6/23.
 */
public class MyRecycleItemView extends RelativeLayout {


    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.duration)
    TextView mDuration;

    public MyRecycleItemView(Context context) {
        this(context, null);
    }


    public MyRecycleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.video_local_video_item, this);
        ButterKnife.bind(this, this);
    }


    public void bindView(LocalVideoBean localVideoBean) {
        mTitle.setText(localVideoBean.getTitle());
        mDuration.setText(localVideoBean.getDuration());
        String path = localVideoBean.getPath();
        Log.e("Aaaaaaaa", "bindView: ----"+path );
        mImage.setImageBitmap(MainActivity.getVideoThumb2(path));
    }


}
