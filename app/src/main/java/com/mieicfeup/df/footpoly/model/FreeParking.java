package com.mieicfeup.df.footpoly.model;

import android.util.Log;

/**
 * Created by fabio on 01/06/2016.
 */
public class FreeParking extends Place
{
    private int amount;

    /**
     * Default constructor
     */
    public FreeParking()
    {
        this.amount = 0;
    }

    /**
     * Increases the amount contained in FreeParking
     * @param amount amount to increase
     */
    public void incAmount(int amount)
    {
        this.amount += amount;
    }

    /**
     * Increases the player balance by the amount contained in free parking and resets amount
     * @param player player to increase balance
     * @return no dialog enum
     */
    public dialogType trigger(Player player)
    {
        Log.w("FreeParking", "trigger");
        player.incBalance(amount);
        this.amount = 0;
        return dialogType.NODIALOG;
    }
}

