package com.doCompany.alazan;

import android.graphics.Color;

import java.util.Random;

public class RandomColor {

    private static final int[] colors = {Color.GREEN, Color.BLUE};

    public static int getRandomColor() {
        Random randomColor = new Random();
        int color = randomColor.nextInt(colors.length);
        return colors[color];
    }
}
