package com.polok.eubmanagement.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Extension {
    public static void showToast(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void hideStatusBar(Window window) {
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    public static void showErrorOnUi(EditText inputField, String errorMessage) {
        inputField.setError(errorMessage);
        inputField.requestFocus();
    }

    public static String getCurrentDate() {
        try {
            return new SimpleDateFormat("dd MMMM, yyyy", Locale.US).format(
                    Calendar.getInstance(TimeZone.getDefault()).getTime()
            );
        } catch (Exception e) {
            return "N/A";
        }
    }

    /*public static void loadImage(String imageUrl, ImageView imageView) {
        Glide.with(imageView).load(imageUrl)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24).into(imageView);
    }*/
}
