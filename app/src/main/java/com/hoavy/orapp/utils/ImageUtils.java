package com.hoavy.orapp.utils;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public class ImageUtils {
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            return null;
        }
    }
}
