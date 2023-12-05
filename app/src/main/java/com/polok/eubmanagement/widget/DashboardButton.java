package com.polok.eubmanagement.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.polok.eubmanagement.R;

public class DashboardButton extends ConstraintLayout {
    public DashboardButton(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);

        LayoutInflater.from(context).inflate(R.layout.widget_dashboard_button, this);
        @SuppressLint({"Recycle", "CustomViewStyleable"})
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.DashboardButton,0, 0);
        try {
            ((ImageView) findViewById(R.id.button_icon)).setImageResource(typedArray.getResourceId(R.styleable.DashboardButton_image,R.drawable.circle_primary));
            ((TextView) findViewById(R.id.button_text)).setText(typedArray.getText(R.styleable.DashboardButton_text));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }
}
