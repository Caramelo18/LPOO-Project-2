package com.mieicfeup.df.footpoly.model;

import android.util.Log;

/**
 * Created by fabio on 01/06/2016.
 */
public class FreeParking extends Place
{
    private int amount;

    public FreeParking()
    {
        this.amount = 0;
    }

    public void incAmount(int amount)
    {
        this.amount += amount;
    }
    public dialogType trigger(Player player)
    {
        Log.w("FreeParking", "trigger");
        player.incBalance(amount);
        this.amount = 0;
        return dialogType.NODIALOG;
    }
}

