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

    public Luck(FreeParking freeParking)
    {
        this.freeParking = freeParking;
        rand = new Random();
    }

    public int getAmount()
    {
        return this.amount;
    }

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
