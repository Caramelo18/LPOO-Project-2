package com.mieicfeup.df.footpoly.model;

import android.util.Log;

/**
 * Created by fabio on 01/06/2016.
 */
public class StartingPoint extends Place
{
    public StartingPoint(){}
    public boolean trigger(Player player)
    {
        Log.w("StartingPoint", player.getName());
        player.incBalance(500);
        return true;
    }
}
