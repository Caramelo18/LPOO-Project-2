package com.mieicfeup.df.footpoly.model;

import android.util.Log;

/**
 * Created by fabio on 01/06/2016.
 */
public class StartingPoint extends Place
{
    /**
     * Default constructor
     */
    public StartingPoint(){}

    /**
     * Increases player balance in 500
     * @param player player to increase
     * @return no dialog enum
     */
    public dialogType trigger(Player player)
    {
        Log.w("StartingPoint", player.getName());
        player.incBalance(500);
        return dialogType.NODIALOG;
    }
}
