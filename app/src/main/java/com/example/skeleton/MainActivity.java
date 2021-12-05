package com.example.skeleton;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mP = (RelativeLayout) findViewById(R.id.activity_main);

/*        View view = new View(this);
        view.setBackgroundColor(Color.parseColor("#CCCCCC"));
        view.setX(100);
        view.setY(100);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(600 , 600);
        mP.addView(view, lp);*/

        LightView lightView = new LightView(this);
        lightView.setX(100);
        lightView.setY(100);
        lightView.setBackgroundColor(Color.parseColor("#CCCCCC"));
        mP.addView(lightView, new ViewGroup.LayoutParams(600, 600));

/*        MyView lightView = new MyView(this);
        lightView.setX(100);
        lightView.setY(100);
        lightView.setBackgroundColor(Color.parseColor("#CCCCCC"));
        mP.addView(lightView, new ViewGroup.LayoutParams(600, 600));*/

    }
}
