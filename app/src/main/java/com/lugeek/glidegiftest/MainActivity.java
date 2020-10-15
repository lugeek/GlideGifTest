package com.lugeek.glidegiftest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addImgTab("test", (ViewGroup) findViewById(R.id.root));
    }

    public void addImgTab(String title, final ViewGroup container) {
        int height = dip2px(32f);
        int width = height;
        height = 200;
        width = 400;

        final FrameLayout frameLayout = new FrameLayout(this);
        final TextView tabText = new TextView(this);
        final ImageView tabImageView = new ImageView(this);
        tabImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        tabImageView.setBackgroundColor(0x000000);
        tabText.setText(title);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        tabText.setLayoutParams(layoutParams);
        frameLayout.addView(tabText);
        frameLayout.addView(tabImageView);

        frameLayout.setFocusable(true);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParams.gravity = Gravity.CENTER_VERTICAL;
        container.addView(frameLayout, lParams);

        // 优先展示tab 文字,等到图片加载成功后,隐藏文字,展示大促标签图片
        tabText.setVisibility(View.VISIBLE);
        tabImageView.setVisibility(View.GONE);

        FrameLayout.LayoutParams params;
        if (height != 0 && width != 0) {
            params = new FrameLayout.LayoutParams(width, height);
        } else {
            params = new FrameLayout.LayoutParams(dip2px(70f),
                    dip2px(this, 22f));
        }
        params.gravity = Gravity.CENTER_VERTICAL;
        tabImageView.setLayoutParams(params);

        String gif = "https://www.easygifanimator.net/images/samples/eglite.gif";

        RequestOptions options = new RequestOptions().dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this).load(gif).apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                tabText.setVisibility(View.GONE);
                tabImageView.setVisibility(View.VISIBLE);
                container.requestLayout();
                container.invalidate();
                return false;
            }
        }).into(tabImageView);

//        ImageLoader.with(this).load(gif).skipMemoryCache().cacheNone().listener(new ImageLoaderListener() {
//            @Override
//            public void onLoadStarted(View view) {
//                Log.i(TAG, "start");
//            }
//
//            @Override
//            public void onLoadFailed(View view, String s, String s1) {
//                Log.i(TAG, "fail");
//            }
//
//            @Override
//            public void onLoadSuccessed(View view, String s, Object result) {
//                tabText.setVisibility(View.GONE);
//                tabImageView.setVisibility(View.VISIBLE);
//                container.requestLayout();
//                container.invalidate();
//            }
//        }).into(tabImageView);
    }

    //dp转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}