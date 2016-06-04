package com.mieicfeup.df.footpoly.controller;

/**
 * Created by fabio on 04/06/2016.
 */
public class ScreenInfo
{
    private static float density;

    public ScreenInfo(){}

    public ScreenInfo(float density)
    {
        this.density = density;
    }

    public float getDensity()
    {
        return this.density;
    }

    public float convertDpToPixels(float dp)
    {
        return dp * this.density;
    }
}
