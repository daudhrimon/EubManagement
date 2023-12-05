package com.polok.eubmanagement.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.polok.eubmanagement.R;

public class PrimaryLoader extends ConstraintLayout {
    public PrimaryLoader(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);

        try {
            LayoutInflater.from(context).inflate(R.layout.widget_loader_primary, this);
            ScaleAnimation scaleAnimation = new ScaleAnimation(
                    1f,
                    2f,
                    1f,
                    2f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
            );
            scaleAnimation.setRepeatMode(Animation.REVERSE);
            scaleAnimation.setDuration(750);
            scaleAnimation.setRepeatCount(Animation.INFINITE);
            findViewById(R.id.image).startAnimation(scaleAnimation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
