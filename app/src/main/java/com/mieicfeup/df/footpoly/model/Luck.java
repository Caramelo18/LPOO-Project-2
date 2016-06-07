package com.mieicfeup.df.footpoly.model;

import android.util.Log;

import java.util.Random;

/**
 * Created by fabio on 01/06/2016.
 */
public class Luck extends Place
{
    private FreeParking freeParking;
    private Random rand;
    private int amount;

    /**
     * Normal constructor
     * @param freeParking freeparking to deposit money
     */
    public Luck(FreeParking freeParking)
    {
        this.freeParking = freeParking;
        rand = new Random();
    }

    /**
     * Returns the amount of the last luck trigger
     * @return the amount of the last luck trigger
     */
    public int getAmount()
    {
        return this.amount;
    }

    /**
     * Increases or decreases a user balance (60% and 40% respectively)
     * @param player player to chance balance
     * @return luck dialog enum
     */
    public dialogType trigger(Player player)
    {
        int card = rand.nextInt(10);
        int times = rand.nextInt(3) + 1;
        this.amount = 200 * times;
        if (card < 6) // player must play
        {
            Log.w("luck", "pay");
            freeParking.incAmount(amount);
            amount = -amount;
            player.incBalance(amount);
            return dialogType.LUCK;
        }
        else // player will receive money
        {
            player.incBalance(amount);
            Log.w("luck", "receive");
            return dialogType.LUCK;
        }
    }
}
