package com.example.tongmin.mypicviewer;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

public class CircleActivity extends AppCompatActivity {

    MyCircleLayout circle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_circle);
        circle = (MyCircleLayout)findViewById(R.id.circle);
        findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircleActivity.this, "asdf", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
//        ObjectAnimator sin = ObjectAnimator.ofFloat(circle, "baseAngle", 0, 1);
//        sin.setDuration(3000);
//        sin.setInterpolator(new DecelerateInterpolator());
//        sin.start();
    }
}
