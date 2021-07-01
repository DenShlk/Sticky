package com.hypersphere.sticky;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class Sticker {
    Bitmap image;
    String emoji;

    public Sticker(Bitmap image, String emoji) {
        this.image = image;

        this.emoji = emoji;
    }

    @NonNull
    @Override
    public String toString() {
        return emoji;
    }
}
