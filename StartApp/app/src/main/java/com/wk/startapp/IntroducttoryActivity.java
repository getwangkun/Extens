package com.wk.startapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.wk.adapter.IntroductoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class IntroducttoryActivity extends AppCompatActivity {
    private ViewPager mViewPage;
    private Button mButton;
    private List<View> viewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_introducttory);
        initView();
        initAdapter();
        initStart();
    }
    /**
     * 设置第4个引导页的textView文本的点击事件
     */
    private void initStart() {
        mButton=viewList.get(3).findViewById(R.id.btn_open);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroducttoryActivity.this, MainActivity.class));
                IntroducttoryActivity.this.finish();
            }
        });
    }
    /**
     * 适配器
     */
    private void initAdapter() {
        IntroductoryAdapter adapter=new IntroductoryAdapter(viewList);
        mViewPage.setAdapter(adapter);
    }

    /**
     * viewPager和4个引导
     */
    private void initView() {
        mViewPage=findViewById(R.id.introductory_viewPager);
        viewList=new ArrayList<>();
        viewList.add(getView(R.layout.introductory_a));
        viewList.add(getView(R.layout.introductory_b));
        viewList.add(getView(R.layout.introductory_c));
    }

    private View getView(int resId) {
        return LayoutInflater.from(this).inflate(resId,null);
    }
}