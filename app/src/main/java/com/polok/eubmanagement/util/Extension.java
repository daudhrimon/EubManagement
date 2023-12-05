package com.polok.eubmanagement.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class Extension {
    public static void showToast(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void hideStatusBar(Window window) {
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /*public static void loadImage(String imageUrl, ImageView imageView) {
        Glide.with(imageView).load(imageUrl)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24).into(imageView);
    }*/
}
