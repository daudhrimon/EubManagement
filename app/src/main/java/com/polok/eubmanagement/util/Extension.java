package com.polok.eubmanagement.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class Extension {
    public static void showToast(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void hideStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) window.setDecorFitsSystemWindows(false);
        else window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}
