package com.moneylover;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.moneylover.ui.app.AppActivity;

public class MainActivity extends AppCompatActivity {

    private Animation zoomOutAnimation;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        image = findViewById(R.id.imgVLogo);

        Animation zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_animation);
        zoomOutAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_out_animation);

        zoomInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                image.startAnimation(zoomOutAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        zoomOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_animation, 0);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });


        image.startAnimation(zoomInAnimation);
    }
}
